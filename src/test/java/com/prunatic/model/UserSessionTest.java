package com.prunatic.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserSessionTest {

    @Test
    public void shouldKeepUserRoles()
    {
        String[] someRoles = {"aRole"};
        UserSession sut = new UserSession(someRoles);

        String[] actual = sut.userRoles();

        assertArrayEquals(someRoles, actual);
    }
}