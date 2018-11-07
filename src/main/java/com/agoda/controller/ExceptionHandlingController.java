package com.agoda.controller;

import com.agoda.exception.RateLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlingController extends BaseController {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<String> global(HttpServletRequest httpServletRequest, Exception exception) {
        return badRequest(exception.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    public ResponseEntity<String> internalServerError(HttpServletRequest httpServletRequest, Exception exception) {
        return internalServerError(exception.getMessage());
    }

    @ExceptionHandler(value = {RateLimitExceededException.class})
    @ResponseBody
    public ResponseEntity<String> tooManyReqeusts(HttpServletRequest httpServletRequest, Exception exception) {
        return tooManyRequests(exception.getMessage());
    }
}
