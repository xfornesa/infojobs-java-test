package com.prunatic.domain.authentication;

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
        boolean isValid = elements.contains(session);
        if (isValid) {
            refresh(session);
        }

        return isValid;
    }

    @Override
    public void refresh(UserSession session) {
        session.increaseExpiringDate(TTL_MINUTES);
    }

    @Override
    public void invalidate(UserSession session) {
        elements.remove(session);
    }

    public UserSession[] findAllSessions() {
        return elements.toArray(new UserSession[elements.size()]);
    }
}
