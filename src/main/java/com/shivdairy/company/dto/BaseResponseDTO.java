package com.shivdairy.company.dto;

import com.shivdairy.company.exception.CompanyError;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@Data
public class BaseResponseDTO<T> {

    private Boolean success = Boolean.TRUE;

    private String message;

    private T data;

    private List<CompanyError> errors;

    public BaseResponseDTO(Boolean success, String message, T data, List<CompanyError> errors) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public BaseResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public BaseResponseDTO(Boolean success, String message, List<CompanyError> errors) {
        this.success = success;
        this.message = message;
        this.errors = errors;
    }
}