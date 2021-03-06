package com.prunatic.domain.authentication;

import com.prunatic.domain.user.User;
import com.prunatic.domain.user.UserCredentials;
import com.prunatic.domain.user.UserRepository;

public class CredentialsAuthenticationService {

    private UserRepository userRepository;
    private UserSessionRepository sessionRepository;

    public CredentialsAuthenticationService(UserRepository userRepository, UserSessionRepository sessionRepository) {

        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public UserSession authenticate(String username, UserCredentials credentials) throws InvalidArgumentException {
        User user = userRepository.userByUsername(username);
        if (user == null) {
            throw new InvalidArgumentException();
        }
        UserCredentials toValidate = UserCredentials.fromUsername(username);
        if (credentials.equals(toValidate)) {
            UserSession session = new UserSession(username, user.roles());
            sessionRepository.add(session);

            return session;
        }

        return null;
    }
}
