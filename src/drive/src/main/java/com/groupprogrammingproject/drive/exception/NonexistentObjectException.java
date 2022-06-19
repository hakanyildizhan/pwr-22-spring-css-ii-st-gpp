package com.groupprogrammingproject.drive.exception;

public class NonexistentObjectException extends RuntimeException {
    public NonexistentObjectException() {

    }

    public NonexistentObjectException(String message) {
        super(message);
    }
}
