package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.domain.OrganizationFunction;
import com.tracebucket.x1.organization.api.rest.resource.DefaultBusinessLineResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultDepartmentResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationUnitResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultOrganizationUnitResourceBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationUnitResourceFixture {
    public static DefaultOrganizationUnitResource standardOrganizationUnitResource(){
        DefaultOrganizationResource organization = null;
        DefaultOrganizationUnitResource parent = null;

        Set<DefaultDepartmentResource> departments = new HashSet<DefaultDepartmentResource>();
        departments.add(DefaultDepartmentResourceFixture.standardDepartment());

        Set<DefaultBusinessLineResource> businessLines = new HashSet<DefaultBusinessLineResource>();
        businessLines.add(DefaultBusinessLineResourceFixture.standardBusinessLine());

        Set<OrganizationFunction> organizationFunctions = new HashSet<OrganizationFunction>();
        organizationFunctions.add(OrganizationFunction.SALES);
        organizationFunctions.add(OrganizationFunction.PURCHASE);

/*        Set<DefaultOrganizationUnitResource> children = new HashSet<DefaultOrganizationUnitResource>();
        children.add(OrganizationUnitFixture.standardOrganizationUnit());*/

        DefaultOrganizationUnitResource organizationUnit = DefaultOrganizationUnitResourceBuilder.anOrganizationUnitResourceBuilder()
                .withName("DefaultOrganizationResource " + new Date().getTime())
                .withDescription(UUID.randomUUID().toString())
                .withBusinessLines(businessLines)
                        //.withChildren(children)
                .withDepartments(departments)
                .withOrganizationFunctions(organizationFunctions)
                .withOrganization(organization)
                .withParent(parent)
                .build();
        return organizationUnit;
    }
}