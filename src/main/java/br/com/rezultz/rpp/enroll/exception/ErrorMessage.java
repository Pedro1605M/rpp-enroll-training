package br.com.rezultz.rpp.enroll.exception;

import java.time.LocalDateTime;

public record ErrorMessage(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
){
    public ErrorMessage(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now());
    }
}

