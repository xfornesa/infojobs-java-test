package com.prunatic.domain.authentication;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserSessionTest {

    @Test
    public void shouldKeepUserRoles()
    {
        String username = "aUsername";
        String[] someRoles = {"aRole"};
        UserSession sut = new UserSession(someRoles);

        String[] actual = sut.userRoles();

        assertArrayEquals(someRoles, actual);
    }
}