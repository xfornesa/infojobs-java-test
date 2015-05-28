package com.prunatic.domain.authorization;

import com.prunatic.domain.authentication.UserSessionRepository;
import com.prunatic.domain.cms.Page;
import com.prunatic.domain.authentication.UserSession;

import java.util.ArrayList;
import java.util.Arrays;

public class PageAuthorizationService {

    private UserSessionRepository sessionRepository;

    public PageAuthorizationService(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public boolean authorize(Page page, UserSession session) throws LoginRequiredException, SessionExpiredException {
        if (session == null) {
            throw new LoginRequiredException();
        }
        if (!sessionRepository.validate(session)) {
            throw new SessionExpiredException();
        }
        String requiredRole = page.requiredRole();
        String[] userRoles = session.userRoles();
        ArrayList<String> userRolesList = new ArrayList<String>(Arrays.asList(userRoles));

        return userRolesList.contains(requiredRole);
    }
}
