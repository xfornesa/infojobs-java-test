package com.prunatic.domain.authentication;

import com.prunatic.domain.authentication.UserSession;

public interface UserSessionRepository {
    void add(UserSession session);
}
