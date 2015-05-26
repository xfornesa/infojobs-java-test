package com.prunatic.model;

import com.prunatic.model.User;

public interface UserRepository {
    void add(User user);

    User userByUsername(String username);
}
