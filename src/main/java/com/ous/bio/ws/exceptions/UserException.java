package com.ous.bio.ws.exceptions;

public class UserException extends RuntimeException{

    public static final long serialVersionUID = 877254186385297L;

    public UserException(String message) {
        super(message);
    }
}
