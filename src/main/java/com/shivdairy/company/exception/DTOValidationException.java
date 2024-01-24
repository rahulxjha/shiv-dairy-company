package com.shivdairy.company.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class DTOValidationException extends RuntimeException{
    private final List<String> validationMessages;

    public DTOValidationException(List<String> validationMessages) {
        this.validationMessages = validationMessages;
    }
}
