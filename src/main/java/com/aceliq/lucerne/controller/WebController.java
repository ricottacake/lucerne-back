package com.aceliq.lucerne.controller;

import java.io.InputStream;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.aceliq.lucerne.data.UserRepository;
import com.aceliq.lucerne.model.ChangeName;
import com.aceliq.lucerne.model.User;
import com.aceliq.lucerne.model.Views;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@RestController
public class WebController {

  @Autowired
  ServletContext servletContext;

  @Autowired
  UserRepository userRepository;

  @PostMapping(path = "/api/reg", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> regUser(@RequestBody User user, BindingResult result) throws Exception {

    if (result.hasErrors()) {
      return new ResponseEntity<String>("valid error", HttpStatus.BAD_REQUEST);
    }

    final ObjectMapper objectMapper = new ObjectMapper();

    final String serializedValue = objectMapper.writerWithView(Views.Detailed.class)
        .writeValueAsString(userRepository.save(user));

    return new ResponseEntity<String>(serializedValue, HttpStatus.OK);
  }

  @PostMapping(path = "/api/change", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> changeName(@RequestBody ChangeName changeName, BindingResult result)
      throws Exception {

    if (result.hasErrors()) {
      return new ResponseEntity<String>("valid error", HttpStatus.BAD_REQUEST);
    }

    Optional<User> user = userRepository.findById(changeName.getPlayerId());


    user.ifPresent(item -> {
      item.setName(changeName.getNewName());
      userRepository.save(item);
    });

    return new ResponseEntity<String>("done", HttpStatus.OK);
  }

  @RequestMapping(value = "/cards/{id:.+}", method = RequestMethod.GET,
      consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
  public byte[] getImage(@PathVariable("id") String id) throws IOException {
    InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream("cards/" + id.toLowerCase() + ".png");
    return IOUtils.toByteArray(inputStream);
  }
}
