package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class UserDbo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  private String userName;

  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
