package com.n11.case_study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SessionTimeOutException extends RuntimeException {
    public SessionTimeOutException(String message) {
        super(message);
    }
}
