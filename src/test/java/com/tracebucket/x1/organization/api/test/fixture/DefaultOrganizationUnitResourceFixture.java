package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.domain.OrganizationFunction;
import com.tracebucket.x1.organization.api.rest.resource.*;
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

        Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);
        addresses.add(DefaultAddressResourceFixture.standardAddress());
        addresses.add(DefaultAddressResourceFixture.headOffice());

        Set<DefaultPersonResource> contactPersons = new HashSet<DefaultPersonResource>(0);
        contactPersons.add(DefaultPersonResourceFixture.standardPerson());

        Set<DefaultPhoneResource> phones = new HashSet<DefaultPhoneResource>(0);
        phones.add(DefaultPhoneResourceFixture.standardPhone());

        Set<DefaultEmailResource> emails = new HashSet<DefaultEmailResource>(0);
        emails.add(DefaultEmailResourceFixture.standardEmail());

        DefaultOrganizationUnitResource organizationUnit = DefaultOrganizationUnitResourceBuilder.anOrganizationUnitResourceBuilder()
                .withName("DefaultOrganizationResource " + new Date().getTime())
                .withDescription(UUID.randomUUID().toString())
                .withBusinessLines(businessLines)
                        //.withChildren(children)
                .withDepartments(departments)
                .withOrganizationFunctions(organizationFunctions)
                .withOrganization(organization)
                .withParent(parent)
                .withAddresses(addresses)
                .withContactPersons(contactPersons)
                .withPhones(phones)
                .withEmails(emails)
                .build();
        return organizationUnit;
    }
}