package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.domain.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultBusinessLine;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultOrganizationUnitBuilder {
    private String name;
    private String description;
    private DefaultOrganization organization;
    private DefaultOrganizationUnit parent;
    private Set<OrganizationFunction> organizationFunctions = new HashSet<OrganizationFunction>(0);
    private Set<DefaultDepartment> departments = new HashSet<DefaultDepartment>(0);
    private Set<DefaultBusinessLine> businessLines = new HashSet<DefaultBusinessLine>(0);
    private Set<DefaultOrganizationUnit> children = new HashSet<DefaultOrganizationUnit>(0);

    private DefaultOrganizationUnitBuilder(){ }

    public static DefaultOrganizationUnitBuilder anOrganizationUnitBuilder(){
        return new DefaultOrganizationUnitBuilder();
    }

    public DefaultOrganizationUnitBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public DefaultOrganizationUnitBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultOrganizationUnitBuilder withOrganization(DefaultOrganization organization){
        this.organization = organization;
        return this;
    }

    public DefaultOrganizationUnitBuilder withParent(DefaultOrganizationUnit parent){
        this.parent = parent;
        return this;
    }

    public DefaultOrganizationUnitBuilder withOrganizationFunctions(Set<OrganizationFunction> organizationFunctions){
        this.organizationFunctions = organizationFunctions;
        return this;
    }

    public DefaultOrganizationUnitBuilder withDepartments(Set<DefaultDepartment> departments){
        this.departments = departments;
        return this;
    }

    public DefaultOrganizationUnitBuilder withBusinessLines(Set<DefaultBusinessLine> businessLines){
        this.businessLines = businessLines;
        return this;
    }

    public DefaultOrganizationUnitBuilder withChildren(Set<DefaultOrganizationUnit> children){
        this.children = children;
        return this;
    }

    public DefaultOrganizationUnit build(){
        DefaultOrganizationUnit organizationUnit = new DefaultOrganizationUnit();
        organizationUnit.setName(name);
        organizationUnit.setDescription(description);
        organizationUnit.setBusinessLines(businessLines);
        organizationUnit.setChildren(children);
        organizationUnit.setDepartments(departments);
        organizationUnit.setOrganizationFunctions(organizationFunctions);
        organizationUnit.setParent(parent);
        organizationUnit.setOrganization(organization);
        return organizationUnit;
    }

}
