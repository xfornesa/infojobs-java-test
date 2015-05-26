package com.prunatic.model;

import com.prunatic.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void createFromRegistration() throws Exception {
        String[] someRoles = {"aRole"};
        String aUsername = "aUsername";
        User user = User.fromRegistration(aUsername, someRoles);

        assertEquals(aUsername, user.username());
        assertArrayEquals(someRoles, user.roles());
    }
}