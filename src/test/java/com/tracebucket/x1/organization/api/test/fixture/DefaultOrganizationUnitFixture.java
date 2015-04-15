package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.domain.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultBusinessLine;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.test.builder.DefaultOrganizationUnitBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultOrganizationUnitFixture {
    public static DefaultOrganizationUnit standardOrganizationUnit(){
        DefaultOrganization organization = null;
        DefaultOrganizationUnit parent = null;

        Set<DefaultDepartment> departments = new HashSet<DefaultDepartment>();
        departments.add(DefaultDepartmentFixture.standardDepartment());

        Set<DefaultBusinessLine> businessLines = new HashSet<DefaultBusinessLine>();
        businessLines.add(DefaultBusinessLineFixture.standardBusinessLine());

        Set<OrganizationFunction> organizationFunctions = new HashSet<OrganizationFunction>();
        organizationFunctions.add(OrganizationFunction.SALES);
        organizationFunctions.add(OrganizationFunction.PURCHASE);

/*        Set<DefaultOrganizationUnit> children = new HashSet<DefaultOrganizationUnit>();
        children.add(DefaultOrganizationUnitFixture.standardOrganizationUnit());*/

        DefaultOrganizationUnit organizationUnit = DefaultOrganizationUnitBuilder.anOrganizationUnitBuilder()
                .withName("Organization " + new Date().getTime())
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
