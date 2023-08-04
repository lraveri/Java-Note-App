package com.example.application.backend.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {
        super();
    }

    public ObjectNotFoundException(String s) {
        super(s);
    }

}
