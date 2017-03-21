package com.sad.sadcrm;

public class UserLoginException extends RuntimeException {
    public UserLoginException(Exception exception) {
        super(exception);
    }
}
