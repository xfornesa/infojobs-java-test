package com.prunatic.domain.authentication;

import java.util.ArrayList;
import java.util.List;

public class InMemorySessionRepository implements UserSessionRepository {

    List<UserSession> elements;

    public InMemorySessionRepository() {
        elements = new ArrayList<UserSession>();
    }

    public void add(UserSession session) {
        elements.add(session);
    }

    public UserSession[] findAllSessions() {
        return elements.toArray(new UserSession[elements.size()]);
    }
}
