package com.prunatic.domain.authentication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemorySessionRepositoryTest {

    private InMemorySessionRepository sut;

    @Before
    public void setUp() throws Exception {
        sut = new InMemorySessionRepository();
    }

    @Test
    public void shouldLetAddUserSessions() throws Exception {
        String aUsername = "aUsername";
        String[] someRoles = {"aRole"};
        UserSession session = new UserSession(someRoles);

        sut.add(session);

        assertEquals(1, sut.findAllSessions().length);
    }
}