package com.aceliq.lucerne.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSocketAuthenticatorService {

  // @Autowired
  // private UserService userService;

  // @Autowired
  // private PasswordEncoder passwordEncoder;
  //
  @Autowired
  private Auth authManager;

  public KeyAuthenticationToken getAuthenticatedOrFail(String key) {

    // Check the username and password are not empty
    // if (username == null || username.trim().isEmpty()) {
    // throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
    // }
    // if (password == null || password.trim().isEmpty()) {
    // throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
    // }
    // Check that the user with that username exists
    // User user = userService.findUserByUsername(username);
    // if(user == null){
    // throw new AuthenticationCredentialsNotFoundException("User not found");
    // }
    KeyAuthenticationToken token = new KeyAuthenticationToken(key);
    // UsernamePasswordAuthenticationToken token = new
    // UsernamePasswordAuthenticationToken("HUI", "PIZDA");

    // verify that the credentials are valid
    authManager.authenticate(token);

    // Erase the password in the token after verifying it because we will pass it to
    // the STOMP headers.

    return token;
  }
}
