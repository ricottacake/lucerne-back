package com.aceliq.lucerne.components;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class KeyAuthenticationToken extends AbstractAuthenticationToken {

  private String key;

  private Object principal;
  private Object credentials;

  public KeyAuthenticationToken(String principal) {
    super(null);
    this.principal = principal;
    this.credentials = "JKFKSFDKSDKGKDF";
    setAuthenticated(false);
    // TODO Auto-generated constructor stub
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public String getKey() {
    return this.key;
  }

  @Override
  public Object getCredentials() {
    // TODO Auto-generated method stub
    return this.credentials;
  }

  @Override
  public Object getPrincipal() {
    // TODO Auto-generated method stub
    return this.principal;
  }

}
