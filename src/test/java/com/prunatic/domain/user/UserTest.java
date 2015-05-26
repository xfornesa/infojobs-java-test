package com.prunatic.domain.user;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void createFromRegistration() throws Exception {
        String aUsername = "aUsername";
        String[] someRoles = {"aRole"};
        User user = User.fromRegistration(aUsername, someRoles);

        assertEquals(aUsername, user.username());
        assertArrayEquals(someRoles, user.roles());
    }

    @Test
    public void shouldCreateUserCredentials() throws Exception {
        String aUsername = "aUsername";
        String[] someRoles = {"aRole"};
        User user = User.fromRegistration(aUsername, someRoles);
        UserCredentials aCredentials = UserCredentials.fromUsername(aUsername);

        UserCredentials actual = user.credentials();

        assertEquals(aCredentials, actual);
    }
}