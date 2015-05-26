package com.prunatic.model;

public class CredentialsValidationService {

    private UserRepository userRepository;
    private UserSessionRepository sessionRepository;

    public CredentialsValidationService(UserRepository userRepository, UserSessionRepository sessionRepository) {

        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public UserSession validate(String username, UserCredentials credentials) throws InvalidArgumentException {
        User user = userRepository.userByUsername(username);
        if (user == null) {
            throw new InvalidArgumentException();
        }
        UserCredentials toValidate = UserCredentials.fromUsername(username);
        if (credentials.equals(toValidate)) {
            UserSession session = new UserSession(user.roles());
            sessionRepository.add(session);

            return session;
        }

        return null;
    }
}
