package com.tracebucket.x1.organization.api.rest.resource;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ffazil
 * @since 14/06/15
 */
public class ValidationError implements Serializable{
    private Set<AttributeError> attributeErrors = new HashSet<>(0);

    public ValidationError(){

    }

    public void addFieldError(String objectName, Object rejectedValue, String path, String message){
        AttributeError attributeError = new AttributeError(objectName,rejectedValue, path, message);
        attributeErrors.add(attributeError);
    }

    public Set<AttributeError> getAttributeErrors() {
        return attributeErrors;
    }
}