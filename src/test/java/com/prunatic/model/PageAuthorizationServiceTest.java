package com.prunatic.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PageAuthorizationServiceTest {

    private PageAuthorizationService sut;

    @Before
    public void setUp() throws Exception {
        sut = new PageAuthorizationService();
    }

    @Test
    public void shouldGrantWhenUserHasRequiredRole() throws Exception {
        String[] userRoles = {"aRole"};
        Page page = new Page("aPage", "aRole");
        UserSession session = new UserSession(userRoles);

        boolean actual = sut.grant(page, session);

        assertTrue(actual);
    }

    @Test
    public void shouldNotGrantWhenUserHasNoRequiredRole() throws Exception {
        String[] userRoles = {"aDifferentRole"};
        Page page = new Page("aPage", "aRole");
        UserSession session = new UserSession(userRoles);

        boolean actual = sut.grant(page, session);

        assertFalse(actual);
    }
}