package com.prunatic.model;


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

    public User[] allUsers() {
        return elements.toArray(new User[elements.size()]);
    }
}
