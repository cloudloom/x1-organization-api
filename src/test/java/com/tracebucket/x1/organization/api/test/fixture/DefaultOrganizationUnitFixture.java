package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.organization.api.domain.OrganizationFunction;
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

        Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);
        addresses.add(DefaultAddressFixture.standardAddress());
        addresses.add(DefaultAddressFixture.headOffice());

        Set<DefaultPerson> contactPersons = new HashSet<DefaultPerson>(0);
        contactPersons.add(DefaultPersonFixture.standardPerson());

        Set<DefaultPhone> phones = new HashSet<DefaultPhone>(0);
        phones.add(DefaultPhoneFixture.standardPhone());

        Set<DefaultEmail> emails = new HashSet<DefaultEmail>(0);
        emails.add(DefaultEmailFixture.standardEmail());

        DefaultOrganizationUnit organizationUnit = DefaultOrganizationUnitBuilder.anOrganizationUnitBuilder()
                .withName("Yorkshire")
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
