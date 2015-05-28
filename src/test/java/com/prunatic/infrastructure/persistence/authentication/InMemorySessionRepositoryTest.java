package com.prunatic.infrastructure.persistence.authentication;

import com.prunatic.domain.authentication.UserSession;
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
        UserSession session = new UserSession("aUsername", new String[]{"aRole"});

        sut.add(session);

        assertEquals(1, sut.findAllSessions().length);
    }

    @Test
    public void shouldLetInvalidateSessions() throws Exception {
        UserSession session = new UserSession("aUsername", new String[]{"aRole"});
        sut.add(session);

        sut.invalidate(session);

        assertEquals(0, sut.findAllSessions().length);
    }

    @Test
    public void shouldValidateSessions() throws Exception {
        UserSession session = new UserSession("aUsername", new String[]{"aRole"});
        sut.add(session);

        boolean actual = sut.validate(session);

        assertTrue(actual);
    }

    @Test
    public void shouldNotValidateSessionsWhenDoesNotExist() throws Exception {
        UserSession session = new UserSession("aUsername", new String[]{"aRole"});

        boolean actual = sut.validate(session);

        assertFalse(actual);
    }

    @Test
    public void shouldNotValidateSessionsWhenHasExpired() throws Exception {
        UserSession session = new UserSession("aUsername", new String[]{"aRole"}, -1);
        sut.add(session);

        boolean actual = sut.validate(session);

        assertFalse(actual);
    }
}