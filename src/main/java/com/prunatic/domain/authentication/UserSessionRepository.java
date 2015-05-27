package com.prunatic.domain.authentication;

import com.prunatic.domain.authentication.UserSession;

public interface UserSessionRepository {

    int TTL_MINUTES = 5;

    void add(UserSession session);

    boolean validate(UserSession session);

    void refresh(UserSession session);

    void invalidate(UserSession session);
}
