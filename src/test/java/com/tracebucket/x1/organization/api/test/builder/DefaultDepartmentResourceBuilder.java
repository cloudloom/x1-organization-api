package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.rest.resource.DefaultDepartmentResource;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultDepartmentResourceBuilder {
    private String name;
    private String description;

    public DefaultDepartmentResourceBuilder(){ }

    public static DefaultDepartmentResourceBuilder aDepartmentResource(){
        return new DefaultDepartmentResourceBuilder();
    }

    public DefaultDepartmentResourceBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultDepartmentResourceBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public DefaultDepartmentResource build(){
        DefaultDepartmentResource department = new DefaultDepartmentResource();
        department.setName(name);
        department.setDescription(description);
        return department;
    }
}
