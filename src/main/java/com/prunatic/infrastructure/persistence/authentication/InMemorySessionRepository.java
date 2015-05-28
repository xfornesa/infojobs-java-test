package com.prunatic.infrastructure.persistence.authentication;

import com.prunatic.domain.authentication.UserSession;
import com.prunatic.domain.authentication.UserSessionRepository;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class InMemorySessionRepository implements UserSessionRepository {

    List<UserSession> elements;

    public InMemorySessionRepository() {
        elements = new ArrayList<UserSession>();
    }

    @Override
    public void add(UserSession session) {
        elements.add(session);
    }

    @Override
    public boolean validate(UserSession session) {
        boolean exists = elements.contains(session);
        boolean hasNotExpired = checkExpiringTime(session);
        boolean isValid = exists && hasNotExpired;
        if (isValid) {
            refresh(session);
        }

        return isValid;
    }

    private boolean checkExpiringTime(UserSession session) {
        DateTime expiringTime = session.expiresAt();

        return expiringTime.isAfterNow();
    }

    @Override
    public void refresh(UserSession session) {
        session.increaseExpiringDate(TTL_MINUTES);
    }

    @Override
    public void invalidate(UserSession session) {
        elements.remove(session);
        session.expire();
    }

    @Override
    public UserSession[] findAllSessions() {
        return elements.toArray(new UserSession[elements.size()]);
    }
}
