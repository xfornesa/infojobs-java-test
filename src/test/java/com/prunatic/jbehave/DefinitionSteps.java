package com.prunatic.jbehave;

import com.prunatic.domain.authentication.*;
import com.prunatic.domain.authorization.LoginRequiredException;
import com.prunatic.domain.authorization.PageAuthorizationService;
import com.prunatic.domain.authorization.SessionExpiredException;
import com.prunatic.domain.user.InMemoryUserRepository;
import com.prunatic.domain.user.User;
import com.prunatic.domain.user.UserCredentials;
import com.prunatic.domain.user.UserRepository;
import com.prunatic.domain.web.InMemoryPageRepository;
import com.prunatic.domain.web.Page;
import com.prunatic.domain.web.PageRepository;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;

import java.util.Map;

public class DefinitionSteps {

    private UserRepository userRepository;
    private PageRepository pageRepository;
    private UserSessionRepository sessionRepository;
    private CredentialsAuthenticateService authenticationService;
    private PageAuthorizationService authorizationService;
    private UserSession currentSession = null;
    private Page currentBrowsePage = null;
    private boolean lastPageAuthorizationResult = false;
    private String lastBrowsePageException = "";
    private DateTime currentSessionExpiringTime = null;

    public DefinitionSteps() {
        userRepository = new InMemoryUserRepository();
        pageRepository = new InMemoryPageRepository();
        sessionRepository = new InMemorySessionRepository();
        authenticationService = new CredentialsAuthenticateService(userRepository, sessionRepository);
        authorizationService = new PageAuthorizationService(sessionRepository);
    }

    @Given("it exists the following registered users: $users")
    public void givenSomeUsers(ExamplesTable users) {
        for(Map<String, String> userDetails : users.getRows()) {
            String username = userDetails.get("username");
            String[] roles = userDetails.get("roles").split(",");
            User user = User.fromRegistration(username, roles);
            userRepository.add(user);
        }
    }

    @Given("it exists the following pages: $pages")
    public void givenSomePages(ExamplesTable pages) {
        for(Map<String, String> pageDetails : pages.getRows()) {
            String name = pageDetails.get("name");
            String role = pageDetails.get("requiredRole");
            Page page = new Page(name, role);
            pageRepository.add(page);
        }
    }

    @Given("user '$username' has authenticated his credentials")
    public void authenticateUserCredentials(String username) {
        User user = userRepository.userByUsername(username);
        UserCredentials credentials = user.credentials();
        try {
            currentSession = authenticationService.authenticate(username, credentials);
        } catch (InvalidArgumentException exception) {
            currentSession = null;
        }
    }

    @When("the user browses the page '$pageName'")
    public void browsePageAuthenticated(String pageName) {
        Assert.assertNotNull(currentSession);
        currentBrowsePage = pageRepository.pageByName(pageName);

        authorizePage(currentSession, currentBrowsePage);
    }

    @Given("an unauthenticated user browses the page '$pageName'")
    public void browsePageUnauthenticated(String pageName) {
        Assert.assertNull(currentSession);
        currentBrowsePage = pageRepository.pageByName(pageName);

        authorizePage(currentSession, currentBrowsePage);
    }

    private void authorizePage(UserSession session, Page page) {
        try {
            lastPageAuthorizationResult = authorizationService.authorize(page, session);
        } catch (LoginRequiredException | SessionExpiredException e) {
            lastBrowsePageException = e.getMessage();
        }
    }

    @Then("he is granted for seeing that page")
    public void userIsGrantedForPage() throws SessionExpiredException {
        Assert.assertTrue(lastPageAuthorizationResult);
    }

    @Then("he is not granted for seeing that page")
    public void userIsNotGrantedForPage() throws SessionExpiredException {
        Assert.assertFalse(lastPageAuthorizationResult);
    }

    @Given("his session has expired")
    public void expireSession() {
        sessionRepository.invalidate(currentSession);
    }

    @Then("he will receive a message like '$message'")
    public void userIsNotGrantedForPage(String message) {
        Assert.assertEquals(message, lastBrowsePageException);
    }

    @When("his session is validated")
    public void validCurrentSession() {
        if (currentSession != null) {
            currentSessionExpiringTime = currentSession.expiresAt();
        }
        sessionRepository.validate(currentSession);
    }

    @Then("its expiring time will be increased by $minutes more minutes")
    public void checkSessionExpiringTime(int minutes) {
        Assert.assertNotNull(currentSessionExpiringTime);
        DateTime actualExpiringTime = currentSession.expiresAt();
        Interval interval = new Interval(currentSessionExpiringTime, actualExpiringTime);
        int diffInMinutes = (int) interval.toDuration().getStandardMinutes();
        Assert.assertEquals(minutes, diffInMinutes);
    }
}
