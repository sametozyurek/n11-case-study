package com.n11.case_study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class LightningTalkTimeExceedException extends RuntimeException {
    public LightningTalkTimeExceedException(String message) {
        super(message);
    }
}
