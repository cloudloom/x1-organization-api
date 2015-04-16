package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.domain.OrganizationFunction;
import com.tracebucket.x1.organization.api.rest.resource.DefaultBusinessLineResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultDepartmentResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationUnitResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationUnitResourceBuilder {
    private String name;
    private String description;
    private DefaultOrganizationResource organization;
    private DefaultOrganizationUnitResource parent;
    private Set<OrganizationFunction> organizationFunctions = new HashSet<OrganizationFunction>(0);
    private Set<DefaultDepartmentResource> departments = new HashSet<DefaultDepartmentResource>(0);
    private Set<DefaultBusinessLineResource> businessLines = new HashSet<DefaultBusinessLineResource>(0);
    private Set<DefaultOrganizationUnitResource> children = new HashSet<DefaultOrganizationUnitResource>(0);

    private DefaultOrganizationUnitResourceBuilder(){ }

    public static DefaultOrganizationUnitResourceBuilder anOrganizationUnitResourceBuilder(){
        return new DefaultOrganizationUnitResourceBuilder();
    }

    public DefaultOrganizationUnitResourceBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public DefaultOrganizationUnitResourceBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultOrganizationUnitResourceBuilder withOrganization(DefaultOrganizationResource organization){
        this.organization = organization;
        return this;
    }

    public DefaultOrganizationUnitResourceBuilder withParent(DefaultOrganizationUnitResource parent){
        this.parent = parent;
        return this;
    }

    public DefaultOrganizationUnitResourceBuilder withOrganizationFunctions(Set<OrganizationFunction> organizationFunctions){
        this.organizationFunctions = organizationFunctions;
        return this;
    }

    public DefaultOrganizationUnitResourceBuilder withDepartments(Set<DefaultDepartmentResource> departments){
        this.departments = departments;
        return this;
    }

    public DefaultOrganizationUnitResourceBuilder withBusinessLines(Set<DefaultBusinessLineResource> businessLines){
        this.businessLines = businessLines;
        return this;
    }

    public DefaultOrganizationUnitResourceBuilder withChildren(Set<DefaultOrganizationUnitResource> children){
        this.children = children;
        return this;
    }

    public DefaultOrganizationUnitResource build(){
        DefaultOrganizationUnitResource organizationUnit = new DefaultOrganizationUnitResource();
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