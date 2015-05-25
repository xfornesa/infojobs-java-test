package com.prunatic.jbehave;

import com.prunatic.user.User;
import org.jbehave.core.annotations.Given;

import static org.junit.Assert.*;

public class DefinitionSteps {

    @Given("the user '$username' is registered with roles '$roles'")
    public void givenAUserWithRole(String username, String roles) {
        String[] givenRoles = roles.split(",");
        User user = User.fromRegistration(username, givenRoles);
    }
}
