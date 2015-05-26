package com.prunatic.jbehave;

import com.prunatic.model.InMemoryPageRepository;
import com.prunatic.model.Page;
import com.prunatic.model.PageRepository;
import com.prunatic.model.InMemoryUserRepository;
import com.prunatic.model.User;
import com.prunatic.model.UserRepository;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.model.ExamplesTable;

import java.util.Map;

public class DefinitionSteps {

    UserRepository userRepository = new InMemoryUserRepository();
    PageRepository pageRepository = new InMemoryPageRepository();

    @Given("it exists the following registered users: $users")
    public void givenSomeUsers(ExamplesTable users) {
        for(Map<String, String> userDetails : users.getRows()) {
            String username = userDetails.get("username").trim();
            String[] roles = userDetails.get("roles").trim().split(",");
            User user = User.fromRegistration(username, roles);
            userRepository.add(user);
        }
    }

    @Given("it exists the following pages: $pages")
    public void givenSomePages(ExamplesTable pages) {
        for(Map<String, String> pageDetails : pages.getRows()) {
            String name = pageDetails.get("name").trim();
            String role = pageDetails.get("requiredRole").trim();
            Page page = new Page(name, role);
            pageRepository.add(page);
        }
    }
}
