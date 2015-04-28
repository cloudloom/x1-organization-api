package com.tracebucket.x1.organization.api.rest.controller;

import com.tracebucket.x1.organization.api.rest.exception.OrganizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sadath on 28-Apr-15.
 */
@ControllerAdvice
public class OrganizationAdvice {
    @ExceptionHandler(OrganizationException.class)
    @ResponseBody
    public ResponseEntity<String> handleOrganizationException(OrganizationException ex) {
        return new ResponseEntity<String>(ex.getMessage(), ex.getHttpStatus());
    }
}