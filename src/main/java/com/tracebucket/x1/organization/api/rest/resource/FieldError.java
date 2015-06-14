package com.tracebucket.x1.organization.api.rest.resource;

import java.io.Serializable;

/**
 * @author ffazil
 * @since 14/06/15
 */
public class FieldError implements Serializable{
    private String field;
    private String resource;
    private String message;
    private Object rejectedValue;

    public FieldError(String resource, Object rejectedValue, String field, String message) {
        this.resource = resource;
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public String getResource() {
        return resource;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }
}