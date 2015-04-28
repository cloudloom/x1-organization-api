package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultDepartmentBuilder {
    private String name;
    private String description;

    public DefaultDepartmentBuilder(){ }

    public static DefaultDepartmentBuilder aDepartment(){
        return new DefaultDepartmentBuilder();
    }

    public DefaultDepartmentBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultDepartmentBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public DefaultDepartment build(){
        DefaultDepartment department = new DefaultDepartment();
        department.setName(name);
        department.setDescription(description);
        return department;
    }
}
