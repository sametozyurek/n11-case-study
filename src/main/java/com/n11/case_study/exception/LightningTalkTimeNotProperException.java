package com.n11.case_study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class LightningTalkTimeNotProperException extends RuntimeException {
    public LightningTalkTimeNotProperException(String message) {
        super(message);
    }
}
