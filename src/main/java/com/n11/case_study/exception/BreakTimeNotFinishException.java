package com.n11.case_study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BreakTimeNotFinishException extends RuntimeException {
    public BreakTimeNotFinishException(String message) {
        super(message);
    }
}
