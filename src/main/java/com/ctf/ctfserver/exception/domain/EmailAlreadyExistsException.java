package com.ctf.ctfserver.exception.domain;


public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
