package com.ivansh.exception;

public class NoCompanyException extends RuntimeException {

    public NoCompanyException() {
        super("No such company in database!");
    }
}
