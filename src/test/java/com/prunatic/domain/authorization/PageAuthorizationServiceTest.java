package com.prunatic.domain.authorization;

import com.prunatic.domain.web.Page;
import com.prunatic.domain.authentication.UserSession;
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
        String aUsername = "aUsername";
        String[] userRoles = {"aRole"};
        Page page = new Page("aPage", "aRole");
        UserSession session = new UserSession(userRoles);

        boolean actual = sut.grant(page, session);

        assertTrue(actual);
    }

    @Test
    public void shouldNotGrantWhenUserHasNoRequiredRole() throws Exception {
        String aUsername = "aUsername";
        String[] userRoles = {"aDifferentRole"};
        Page page = new Page("aPage", "aRole");
        UserSession session = new UserSession(userRoles);

        boolean actual = sut.grant(page, session);

        assertFalse(actual);
    }
}