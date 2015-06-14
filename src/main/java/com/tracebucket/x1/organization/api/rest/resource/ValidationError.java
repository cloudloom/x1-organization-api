package com.tracebucket.x1.organization.api.rest.resource;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ffazil
 * @since 14/06/15
 */
public class ValidationError implements Serializable{
    private Set<FieldError> fieldErrors = new HashSet<>(0);

    public ValidationError(){

    }

    public void addFieldError(String objectName, Object rejectedValue, String path, String message){
        FieldError fieldError = new FieldError(objectName,rejectedValue, path, message);
        fieldErrors.add(fieldError);
    }

    public Set<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}