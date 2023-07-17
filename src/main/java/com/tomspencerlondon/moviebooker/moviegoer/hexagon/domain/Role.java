package com.tomspencerlondon.moviebooker.moviegoer.hexagon.domain;

public enum Role {
  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN");

  private String roleUser;

  Role(String roleUser) {
    this.roleUser = roleUser;
  }

  public String value() {
    return roleUser;
  }
}
