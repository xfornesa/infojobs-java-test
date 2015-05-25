package com.prunatic.user;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest extends TestCase {

    @Test
    public void testFromRegistration() throws Exception {
        String[] someRoles = {"aRole"};
        String aUsername = "aUsername";
        User user = User.fromRegistration(aUsername, someRoles);

        assertEquals(aUsername, user.username());
        assertEquals(someRoles, user.roles());
    }
}