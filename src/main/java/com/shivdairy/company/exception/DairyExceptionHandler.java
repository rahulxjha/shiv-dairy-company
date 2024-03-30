package com.shivdairy.company.exception;

import com.shivdairy.company.dto.BaseResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class DairyExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String VALIDATION_FAILED = "VALIDATION FAILED";
    private static final String BAD_REQUEST = "BAD REQUEST";
    private static final String DATA_NOT_FOUND = "DATA NOT FOUND";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException exception, @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        log.info("DairyExceptionHandler.handleMethodArgumentNotValid {}", exception.getMessage());
        List<CompanyError> companyErrors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                companyErrors.add(new CompanyError(fieldError.getDefaultMessage())));
        BaseResponseDTO errorDto = new BaseResponseDTO<>(Boolean.FALSE, VALIDATION_FAILED, companyErrors);
        return ResponseEntity.status(status).body(errorDto);
    }

    @ExceptionHandler(DTOValidationException.class)
    public final ResponseEntity<Object> handleDTOValidationException(DTOValidationException exception){
        log.error("DairyExceptionHandler.handleDTOValidationException {}, {}", exception.getMessage(), exception.getValidationMessages());
        List<CompanyError> companyErrors = new ArrayList<>();
        exception.getValidationMessages().forEach(msg -> companyErrors.add(new CompanyError(msg)));
        BaseResponseDTO errorDto = new BaseResponseDTO<>(Boolean.FALSE, VALIDATION_FAILED, companyErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception){
        log.error("DairyExceptionHandler.handleAllExceptions {}", exception.getMessage());
        BaseResponseDTO errorDto = new BaseResponseDTO(Boolean.FALSE, BAD_REQUEST,
                List.of(new CompanyError(exception.getMessage())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(NoItemFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NoItemFoundException exception){
        log.info("DairyExceptionHandler.handleNotFoundException {}", exception.getMessage());
        BaseResponseDTO errorDto = new BaseResponseDTO<>(Boolean.FALSE, DATA_NOT_FOUND,
                List.of(new CompanyError(exception.getMessage())));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }
}
