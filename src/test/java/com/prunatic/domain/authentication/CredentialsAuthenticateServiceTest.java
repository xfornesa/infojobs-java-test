package com.prunatic.domain.authentication;

import com.prunatic.domain.user.User;
import com.prunatic.domain.user.UserRepository;
import com.prunatic.domain.user.UserCredentials;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CredentialsAuthenticateServiceTest {

    private CredentialsAuthenticateService sut;
    private UserRepository userRepository;
    private UserSessionRepository sessionRepository;
    private String aUsername;
    private UserCredentials someCredentials;
    private UserCredentials someWrongCredentials;

    @Before
    public void setUp() throws Exception {
        initUserRepository();
        initUserSessionRepository();

        sut = new CredentialsAuthenticateService(userRepository, sessionRepository);
    }

    private void initUserRepository() {
        aUsername = "aUsername";
        someCredentials = UserCredentials.fromUsername(aUsername);
        someWrongCredentials = UserCredentials.fromUsername("aDifferentUsername");
        userRepository = mock(UserRepository.class);
        when(userRepository.userByUsername(aUsername)).thenReturn(mock(User.class));
    }

    private void initUserSessionRepository() {
        sessionRepository = mock(UserSessionRepository.class);
    }

    @Test
    public void shouldCreateUserSessionWhenCredentialsAreValid() throws Exception, InvalidArgumentException {
        UserSession actual = sut.validate(aUsername, someCredentials);

        assertNotNull(actual);
    }

    @Test
    public void shouldReturnNullWhenCredentialsAreNotValid() throws Exception, InvalidArgumentException {
        UserSession actual = sut.validate(aUsername, someWrongCredentials);

        assertNull(actual);
    }

    @Test
    public void shouldPersistSessionWhenCredentialsAreValid() throws Exception, InvalidArgumentException {
        UserSession actual = sut.validate(aUsername, someCredentials);

        verify(sessionRepository).add(actual);
    }

    @Test(expected=InvalidArgumentException.class)
    public void shouldRaiseExceptionWhenNoUserFound() throws Exception, InvalidArgumentException {
        when(userRepository.userByUsername(aUsername)).thenReturn(null);

        UserSession actual = sut.validate(aUsername, someCredentials);
    }
}