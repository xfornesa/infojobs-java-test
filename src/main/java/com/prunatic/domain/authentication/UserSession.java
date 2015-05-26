package com.prunatic.domain.authentication;

public class UserSession {

    private String[] userRoles;

    public UserSession(String[] userRoles) {
        this.userRoles = userRoles;
    }

    public String[] userRoles() {
        return userRoles;
    }
}
