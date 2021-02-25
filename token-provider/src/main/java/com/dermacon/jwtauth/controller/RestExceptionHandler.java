package com.dermacon.jwtauth.controller;

import com.dermacon.jwtauth.exception.ApiError;
import com.dermacon.jwtauth.exception.CredentialsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * source: https://www.toptal.com/java/spring-boot-rest-api-error-handling
 *
 * overview http headers: https://www.baeldung.com/cs/http-status-codes
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles exception thrown by service.
     * To centralize exception handling use @ControllerAdvice annotation
     * see: https://howtodoinjava.com/spring-core/spring-exceptionhandler-annotation/
     * @param ex
     * @return
     */ // todo maybe return Exception as payload???
    // https://stackoverflow.com/questions/52183546/not-able-to-return-responseentity-with-exception-details-in-spring
//    @ExceptionHandler(value = {EntityNotFoundException.class, CredentialsException.class})
//    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
//        ApiError apiError = new ApiError(NOT_FOUND, ex);
//        return new ResponseEntity(apiError, apiError.getStatus());
//    }


    class ErrorInfo {
        public final String url;
        public final String ex;

        public ErrorInfo(String url, Exception ex) {
            this.url = url;
            this.ex = ex.getLocalizedMessage();
        }
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody ErrorInfo
    handleBadRequest(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }




//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        String error = "Malformed JSON request";
//        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
//    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    protected ResponseEntity<Object> handleEntityNotFound(
//            EntityNotFoundException ex) {
//        ApiError apiError = new ApiError(NOT_FOUND);
//        apiError.setMessage(ex.getMessage());
//        return buildResponseEntity(apiError);
//    }

}