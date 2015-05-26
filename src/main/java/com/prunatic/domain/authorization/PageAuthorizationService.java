package com.prunatic.domain.authorization;

import com.prunatic.domain.web.Page;
import com.prunatic.domain.authentication.UserSession;

import java.util.ArrayList;
import java.util.Arrays;

public class PageAuthorizationService {
    public boolean grant(Page page, UserSession session) {
        String requiredRole = page.requiredRole();
        String[] userRoles = session.userRoles();
        ArrayList<String> userRolesList = new ArrayList<String>(Arrays.asList(userRoles));

        return userRolesList.contains(requiredRole);
    }
}
