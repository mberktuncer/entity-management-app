package com.mberktuncer.entity_management_app.handler;

import com.mberktuncer.entity_management_app.exception.AccountNotFoundException;
import com.mberktuncer.entity_management_app.exception.EmailAlreadyExistException;
import com.mberktuncer.entity_management_app.exception.UserNotFoundException;
import com.mberktuncer.entity_management_app.model.api.response.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleUserNotFoundException(UserNotFoundException ex){

        BaseErrorResponse baseErrorResponse = new BaseErrorResponse();

        baseErrorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        baseErrorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(baseErrorResponse, HttpStatusCode.valueOf(baseErrorResponse.getErrorCode()));

    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<BaseErrorResponse> handleEmailAlreadyExistException(EmailAlreadyExistException ex){

        BaseErrorResponse baseErrorResponse = new BaseErrorResponse();

        baseErrorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        baseErrorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(baseErrorResponse, HttpStatusCode.valueOf(baseErrorResponse.getErrorCode()));

    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex){

        BaseErrorResponse baseErrorResponse = new BaseErrorResponse();

        baseErrorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        baseErrorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(baseErrorResponse, HttpStatusCode.valueOf(baseErrorResponse.getErrorCode()));

    }

    @ExceptionHandler
    public ResponseEntity<?> handleGlobalException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
