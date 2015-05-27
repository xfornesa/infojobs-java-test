package com.prunatic.domain.authorization;

public class LoginRequiredException extends Throwable {
    public LoginRequiredException() {
        super("Login is required");
    }
}
