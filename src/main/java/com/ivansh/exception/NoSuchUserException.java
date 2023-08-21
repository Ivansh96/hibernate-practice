package com.ivansh.exception;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException() {
        super("No such user in database!");
    }
}
