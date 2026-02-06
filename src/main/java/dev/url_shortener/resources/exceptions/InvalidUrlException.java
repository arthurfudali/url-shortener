package dev.url_shortener.resources.exceptions;

public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException() {
    }

    public InvalidUrlException(String message) {
        super(message);
    }
}
