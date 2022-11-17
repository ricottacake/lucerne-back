package com.aceliq.lucerne.components;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.aceliq.lucerne.data.UserRepository;
import com.aceliq.lucerne.model.User;

@Service
public class Auth implements AuthenticationManager {

  @Autowired
  UserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String userId = (String) authentication.getPrincipal();
    Optional<User> a = userRepository.findById(userId);
    a.orElseThrow(() -> new BadCredentialsException("error"));
    authentication.setAuthenticated(true);
    return authentication;
  }
}
