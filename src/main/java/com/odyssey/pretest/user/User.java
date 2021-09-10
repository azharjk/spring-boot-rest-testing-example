package com.odyssey.pretest.user;

public class User {
  private Long id;
  private String fullName;  

  public User() {

  }

  public User(Long id, String fullName) {
    this.id = id;
    this.fullName = fullName;
  }

  public User(String fullname) {
    this.fullName = fullname;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setfullName(String fullName) {
    this.fullName = fullName;
  }
}
