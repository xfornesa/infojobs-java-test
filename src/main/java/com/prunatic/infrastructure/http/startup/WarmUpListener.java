package com.prunatic.infrastructure.http.startup;

import com.prunatic.domain.authentication.CredentialsAuthenticationService;
import com.prunatic.domain.authentication.UserSessionRepository;
import com.prunatic.domain.authorization.PageAuthorizationService;
import com.prunatic.domain.cms.Page;
import com.prunatic.domain.cms.PageRepository;
import com.prunatic.domain.user.User;
import com.prunatic.domain.user.UserRepository;
import com.prunatic.infrastructure.persistence.authentication.InMemorySessionRepository;
import com.prunatic.infrastructure.persistence.cms.InMemoryPageRepository;
import com.prunatic.infrastructure.persistence.user.InMemoryUserRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WarmUpListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        initPageRepository(servletContext);
        initUserRepository(servletContext);
        initUserSessionRepository(servletContext);
        initApplicationServices(servletContext);
    }

    private void initPageRepository(ServletContext servletContext) {
        PageRepository pageRepository = new InMemoryPageRepository();
        pageRepository.add(new Page("page1", "PAG_1"));
        pageRepository.add(new Page("page2", "PAG_2"));
        pageRepository.add(new Page("page3", "PAG_3"));

        servletContext.setAttribute("pageRepository", pageRepository);
    }

    private void initUserRepository(ServletContext servletContext) {
        UserRepository userRepository = new InMemoryUserRepository();
        userRepository.add(User.fromRegistration("user1withRolePAG_1", new String[] {"PAG_1"}));
        userRepository.add(User.fromRegistration("user2withRolePAG_2", new String[] {"PAG_2"}));
        userRepository.add(User.fromRegistration("user3withRolePAG_3", new String[] {"PAG_3"}));
        userRepository.add(User.fromRegistration("userwithRolesPAG_2_PAG_3", new String[] {"PAG_2", "PAG_3"}));

        servletContext.setAttribute("userRepository", userRepository);
    }

    private void initUserSessionRepository(ServletContext servletContext) {
        UserSessionRepository sessionRepository = new InMemorySessionRepository();
        servletContext.setAttribute("sessionRepository", sessionRepository);
    }

    private void initApplicationServices(ServletContext servletContext) {
        UserSessionRepository sessionRepository = (UserSessionRepository) servletContext.getAttribute("sessionRepository");
        UserRepository userRepository = (UserRepository) servletContext.getAttribute("userRepository");

        PageAuthorizationService pageAuthorizationService = new PageAuthorizationService(sessionRepository);
        servletContext.setAttribute("pageAuthorizationService", pageAuthorizationService);

        CredentialsAuthenticationService credentialsAuthenticationService = new CredentialsAuthenticationService(userRepository, sessionRepository);
        servletContext.setAttribute("credentialsAuthenticationService", credentialsAuthenticationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
