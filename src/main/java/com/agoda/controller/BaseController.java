package com.agoda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public static <T> ResponseEntity<T> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> ok(T object) {
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> badRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> badRequest(T object) {
        return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> internalServerError() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<T> internalServerError(T object) {
        return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<T> tooManyRequests() {
        return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
    }

    public static <T> ResponseEntity<T> tooManyRequests(T object) {
        return new ResponseEntity<>(object, HttpStatus.TOO_MANY_REQUESTS);
    }
}
