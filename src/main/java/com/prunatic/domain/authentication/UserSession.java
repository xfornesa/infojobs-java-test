package com.prunatic.domain.authentication;

import org.joda.time.DateTime;

public class UserSession {

    public static final int EXPIRING_IN_MINUTES = 5;
    private DateTime expiresAt;
    private String[] userRoles;

    public UserSession(String[] userRoles) {
        this(userRoles, EXPIRING_IN_MINUTES);
    }

    public UserSession(String[] userRoles, int expiringInMinutes) {
        this.userRoles = userRoles;
        this.expiresAt = new DateTime().plusMinutes(expiringInMinutes);
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

    public void expire() {
        this.expiresAt = new DateTime().minusSeconds(1);
    }
}
