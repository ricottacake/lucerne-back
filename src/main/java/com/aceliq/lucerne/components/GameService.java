package com.aceliq.lucerne.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.aceliq.lucerne.data.UserRepository;
import com.aceliq.lucerne.model.Card;
import com.aceliq.lucerne.model.DealerCard;
import com.aceliq.lucerne.model.Game;
import com.aceliq.lucerne.model.GameLog;
import com.aceliq.lucerne.model.NextMove;
import com.aceliq.lucerne.model.Player;
import com.aceliq.lucerne.model.PlayerQuery;
import com.aceliq.lucerne.model.Protocol;
import com.aceliq.lucerne.model.TakeCard;
import com.aceliq.lucerne.model.User;

@Service
public class GameService {

  @Autowired
  private ApplicationContext context;

  /**
   * send data with websocket to players
   */
  @Autowired
  private SendToWebsocket sendToWebsocket;

  @Autowired
  private PlayerService playerService;

  /**
   * access to all online games now
   */
  @Autowired
  private OnlineGames onlineGames;

  /**
   * 
   * repository for work with DB
   */
  @Autowired
  UserRepository userRepository;

  private Map<String, Integer> searchGame;

  public int searchGame(String playerId) {
    return searchGame.get(playerId);
  }

  @Bean
  @Scope("prototype")
  public Game getGame() {
    Game game = new Game((int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000));
    onlineGames.addGame(game);
    return game;
  }

  @Bean
  @Scope("prototype")
  public Player getPlayer(User user) {
    return new Player(user);
  }

  public GameService() {
    this.searchGame = new HashMap<String, Integer>();
  }

  public void handlerWebsocketDisconnect(String playerId) {

    if (!searchGame.containsKey(playerId)) {
      return;
    }

    Game game = onlineGames.getGame(searchGame(playerId));
    Player player = game.getPlayerById(playerId);

    game.removePlayer(player);
    searchGame.remove(playerId);

    if (game.getPlayers().size() == 0) {
      onlineGames.deleteGame(searchGame(playerId));
    } else {
      updatePlayersInLobby(game);
    }
  }

  public void updatePlayersInLobby(Game game) {
    sendToWebsocket.sendToAllPlayers(game, new Protocol<Game>("ok", "updatePlayersInLobby", game));
  }

  /**
   * method for handler "JOIN GAME" and "NEW GAME" queries
   * 
   * @param <T> type query
   * @param playerId user player id
   * @param protocol income protocol from user
   */
  public <T> void lobbyAction(String playerId, Protocol<T> protocol) {
    Game game = protocol.getType().equals("newGame") ? (Game) context.getBean("getGame")
        : onlineGames.getGame((Integer) protocol.getData());

    User user = userRepository.findById(playerId).get();

    Player player = (Player) context.getBean("getPlayer", user);
    game.addPlayer(player);
    searchGame.put(playerId, game.getId());
    sendToWebsocket.send(playerId, new Protocol<Game>("ok", "updatePlayersInLobby", game));

    if (protocol.getType().equals("joinGame")) {
      sendToWebsocket.sendToAllPlayers(game,
          new Protocol<Game>("ok", "updatePlayersInLobby", game));
    }
  }

  public void handlerGameData(String playerId, Protocol<PlayerQuery> protocol) throws Exception {

    Game game = onlineGames.getGame(searchGame.get(playerId));
    Player onePlayer = game.getPlayers().stream()
        .filter(player -> player.getUser().getId().equals(playerId)).findFirst().get();

    String publicId = onePlayer.getUser().getPublicId();
    String name = onePlayer.getUser().getName();
    String queryType = protocol.getData().getQueryType();

    if (queryType.equals("start")) {
      Card card1 = game.getCard();
      Card card2 = game.getCard();

      game.getDelaer().addCard(card1);
      game.getDelaer().addCard(card2);

      FirstTwoDealerCards firstTwoDealerCards = new FirstTwoDealerCards(card1, card2);

      Protocol<FirstTwoDealerCards> a =
          new Protocol<FirstTwoDealerCards>("ok", "startGame", firstTwoDealerCards);

      sendToWebsocket.sendToAllPlayers(game, a);

      nextPlayer(game);
      return;
    }

    if (!onePlayer.isMyMove()) {
      throw new Exception("not avaliable");
    }

    switch (queryType) {
      case "add":

        Card card = game.getCard();
        onePlayer.addCard(card);

        Protocol<TakeCard> prepared = new Protocol<TakeCard>("ok", "takeCard", new TakeCard(card));

        sendToWebsocket.send(playerId, prepared); // send to player card which he get

        if (onePlayer.checkSum() < 21) {
          playerService.playerMove(onePlayer, game);
          return;
        }

        GameLog gameLog = new GameLog(publicId, name, "OVER");
        Protocol<GameLog> p = new Protocol<GameLog>("ok", "gameLog", gameLog);

        sendToWebsocket.sendToAllPlayers(game, p);

        playerService.finishPlayerMove(onePlayer);

        nextPlayer(game);

        break;

      case "enough":

        playerService.finishPlayerMove(onePlayer);
        nextPlayer(game);

        break;
    }
  }

  public void dealerMove(Game game) {

    while (game.getDelaer().checkSum() < 17) {

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      Card card = game.getCard();
      game.getDelaer().addCard(card);

      sendToWebsocket.sendToAllPlayers(game,
          new Protocol<DealerCard>("ok", "dealerCard", new DealerCard(card)));
    }
    sendResult(game);

  }

  public void sendResult(Game game) {

    int dealerResult = game.getDelaer().checkSum();

    for (Player player : game.getPlayers()) {
      if (player.checkSum() == 21) {
        player.setResult(true);
      } else if (player.checkSum() > 21) {
        player.setResult(false);
      } else if (player.checkSum() < 21 && dealerResult < player.checkSum()) {
        player.setResult(true);
      } else if (dealerResult > 21 && player.checkSum() <= 21) {
        player.setResult(true);
      } else {
        player.setResult(false);
      }
    }
    sendToWebsocket.sendToAllPlayers(game, new Protocol<Game>("ok", "resultGame", game));
  }

  public void nextPlayer(Game game) {

    /**
     * find player who has not yet been like
     */
    Optional<Player> optionalPlayer =
        game.getPlayers().stream().filter(player -> !player.isEndGame()).findFirst();

    optionalPlayer.ifPresentOrElse(player -> {

      /**
       * take status player in "play now"
       */
      player.setMyMove(true);

      /**
       * 
       * prepare data for send
       */
      NextMove nextMove = new NextMove(player.getUser().getPublicId(), player.getUser().getName());
      Protocol<NextMove> protocol = new Protocol<NextMove>("ok", "nextMove", nextMove);

      /**
       * 
       * send to all players info about who now play
       */
      sendToWebsocket.sendToAllPlayers(game, protocol);

      /**
       * 
       * start player timer in 5 seconds
       */
      playerService.playerMove(player, game);

    }, () -> {

      /**
       * if all players make a move -> dealer start play
       */
      dealerMove(game);
    });
  }
}
