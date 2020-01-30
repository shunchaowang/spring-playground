package me.swang.springplayground.domain;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
  @Id
  @GeneratedValue
  private Long id;
  private String username;
  private Timestamp createdOn;
  private Calendar updatedOn;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Timestamp getCreatedOn() {
    return this.createdOn;
  }

  public void setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
  }

  public Calendar getUpdatedOn() {
    return this.updatedOn;
  }

  public void setUpdatedOn(Calendar updatedOn) {
    this.updatedOn = updatedOn;
  }

}