package com.prunatic.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryUserRepositoryTest {

    InMemoryUserRepository sut;

    @Before
    public void setUp() throws Exception {
        sut = new InMemoryUserRepository();
    }

    @Test
    public void shouldLetAddUsers() throws Exception {
        String[] someRoles = {"aRole"};
        String aUsername = "aUsername";
        User user = User.fromRegistration(aUsername, someRoles);

        sut.add(user);

        assertEquals(1, sut.allUsers().length);
    }
}