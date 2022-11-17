package com.aceliq.lucerne.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class Protocol<T> {

  @JsonProperty("status")
  private String status;

  @JsonView(Views.Simple.class)
  private String type;

  @JsonProperty("data")
  private T data;

  public Protocol(String satus, String type, T data) {
    super();
    this.status = satus;
    this.type = type;
    this.data = data;
  }

  public Protocol() {
    super();
  }

  public String getStatus() {
    return status;
  }

  public String getType() {
    return type;
  }

  public T getData() {
    return data;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setData(T data) {
    this.data = data;
  }
}
