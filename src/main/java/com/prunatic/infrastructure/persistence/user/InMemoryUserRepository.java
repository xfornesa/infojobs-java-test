package com.prunatic.infrastructure.persistence.user;

import com.prunatic.domain.user.User;
import com.prunatic.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    List<User> elements;

    public InMemoryUserRepository() {
        elements = new ArrayList<User>();
    }

    public void add(User user) {
        elements.add(user);
    }

    public User userByUsername(String username) {
        for(User user : elements.toArray(new User[elements.size()])) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User[] allUsers() {
        return elements.toArray(new User[elements.size()]);
    }
}
