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
        UserSession session = new UserSession(new String[]{"aRole"});

        sut.add(session);

        assertEquals(1, sut.findAllSessions().length);
    }

    @Test
    public void shouldLetInvalidateSessions() throws Exception {
        UserSession session = new UserSession(new String[]{"aRole"});
        sut.add(session);

        sut.invalidate(session);

        assertEquals(0, sut.findAllSessions().length);
    }

    @Test
    public void shouldValidateSessions() throws Exception {
        UserSession session = new UserSession(new String[]{"aRole"});
        sut.add(session);

        boolean actual = sut.validate(session);

        assertTrue(actual);
    }

    @Test
    public void shouldNotValidateSessionsWhenExpired() throws Exception {
        UserSession session = new UserSession(new String[]{"aRole"});
        sut.add(session);
        sut.invalidate(session);

        boolean actual = sut.validate(session);

        assertFalse(actual);
    }
}