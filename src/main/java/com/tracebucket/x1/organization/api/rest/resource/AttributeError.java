package com.tracebucket.x1.organization.api.rest.resource;

import java.io.Serializable;

/**
 * @author ffazil
 * @since 14/06/15
 */
public class AttributeError implements Serializable{
    private String attribute;
    private String resource;
    private String message;
    private Object rejectedValue;

    public AttributeError(String resource, Object rejectedValue, String attribute, String message) {
        this.resource = resource;
        this.attribute = attribute;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    public String getAttribute() {
        return attribute;
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