package com.sad.sadcrm.hibernate.exception;

public class UserUpdateException extends RuntimeException {
    public UserUpdateException(Exception cause) {
        super(cause);
    }
}
