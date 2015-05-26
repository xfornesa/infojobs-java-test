package com.prunatic.domain.user;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserCredentialsTest {

    @Test
    public void shouldBeComparable()
    {
        UserCredentials credentials = UserCredentials.fromUsername("aUsername");
        UserCredentials sameCredentials = UserCredentials.fromUsername("aUsername");

        assertTrue(credentials.equals(sameCredentials));
    }
}