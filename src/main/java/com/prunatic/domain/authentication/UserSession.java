package com.prunatic.domain.authentication;

import org.joda.time.DateTime;

public class UserSession {

    private DateTime expiresAt;
    private String[] userRoles;

    public UserSession(String[] userRoles) {
        this.userRoles = userRoles;
        this.expiresAt = new DateTime();
    }

    public String[] userRoles() {
        return userRoles;
    }

    public DateTime expiresAt() {
        return expiresAt;
    }

    public void increaseExpiringDate(int minutes) {
        expiresAt = expiresAt.plusMinutes(minutes);
    }
}
