package com.example.application.backend.exception;

public class NoteNotFoundException extends RuntimeException {

    private final String message;

    public NoteNotFoundException(Long id) {
        message = "Note with id: " + id + " not found";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
