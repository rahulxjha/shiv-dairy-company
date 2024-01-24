package com.shivdairy.company.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CompanyError {
    private String messages;

    public CompanyError(String messages) {
        this.messages = messages;
    }
}
