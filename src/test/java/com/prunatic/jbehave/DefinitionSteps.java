package com.prunatic.jbehave;

import com.prunatic.domain.authentication.*;
import com.prunatic.domain.authorization.PageAuthorizationService;
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
import org.junit.Assert;

import java.util.Map;

public class DefinitionSteps {

    private UserRepository userRepository = new InMemoryUserRepository();
    private PageRepository pageRepository = new InMemoryPageRepository();
    private UserSessionRepository sessionRepository = new InMemorySessionRepository();
    private CredentialsAuthenticateService validationService = new CredentialsAuthenticateService(userRepository, sessionRepository);
    private UserSession lastValidSession = null;
    private Page currentBrowsePage = null;

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

    @Given("user '$username' has validated his credentials")
    public void validateUserCredentials(String username) {
        User user = userRepository.userByUsername(username);
        UserCredentials credentials = user.credentials();
        try {
            lastValidSession = validationService.validate(username, credentials);
        } catch (InvalidArgumentException exception) {
            lastValidSession = null;
        }
    }

    @When("logged in user browses the page '$pageName'")
    public void browsePage(String pageName) {
        Assert.assertNotNull(lastValidSession);
        currentBrowsePage = pageRepository.pageByName(pageName);
    }

    @Then("he is granted for seeing that page")
    public void userIsGrantedForPage() {
        Assert.assertNotNull(currentBrowsePage);
        PageAuthorizationService authorizationService = new PageAuthorizationService();
        boolean isGranted = authorizationService.grant(currentBrowsePage, lastValidSession);
        Assert.assertTrue(isGranted);
    }
}
