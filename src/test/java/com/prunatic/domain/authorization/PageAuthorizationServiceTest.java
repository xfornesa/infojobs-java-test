package com.prunatic.domain.authorization;

import com.prunatic.domain.authentication.UserSessionRepository;
import com.prunatic.domain.cms.Page;
import com.prunatic.domain.authentication.UserSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PageAuthorizationServiceTest {

    private PageAuthorizationService sut;
    private Page page;
    private UserSessionRepository sessionRepository;
    private UserSession authorizedSession;
    private UserSession unAuthorizedSession;
    private UserSession expiredSession;

    @Before
    public void setUp() throws Exception {
        page = new Page("aPage", "aRole");
        authorizedSession = new UserSession(new String[]{"aRole"});
        unAuthorizedSession = new UserSession(new String[]{"aDifferentRole"});
        expiredSession = new UserSession(new String[]{"aDifferentRole"});
        sessionRepository = mock(UserSessionRepository.class);
        when(sessionRepository.validate(authorizedSession)).thenReturn(true);
        when(sessionRepository.validate(unAuthorizedSession)).thenReturn(true);
        when(sessionRepository.validate(expiredSession)).thenReturn(false);

        sut = new PageAuthorizationService(sessionRepository);
    }

    @Test
    public void shouldAuthorizeWhenUserHasRequiredRole() throws Exception, LoginRequiredException, SessionExpiredException {
        boolean actual = sut.authorize(page, authorizedSession);

        assertTrue(actual);
    }

    @Test
    public void shouldNotAuthorizeWhenUserHasNoRequiredRole() throws Exception, LoginRequiredException, SessionExpiredException {
        boolean actual = sut.authorize(page, unAuthorizedSession);

        assertFalse(actual);
    }

    @Test(expected = LoginRequiredException.class)
    public void shouldRaiseLoginRequiredExceptionWhenNoSessionAvailable() throws Exception, LoginRequiredException, SessionExpiredException {
        sut.authorize(page, null);
    }

    @Test(expected = SessionExpiredException.class)
    public void shouldRaiseSessionExpiredExceptionWhenSessionHasExpired() throws Exception, LoginRequiredException, SessionExpiredException {
        sut.authorize(page, expiredSession);
    }
}