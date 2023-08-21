package com.ivansh.exception;

public class NoCompanyFieldException extends RuntimeException {

    public NoCompanyFieldException() {

        super("You have to fill in \"Company\" field!");

    }

}
