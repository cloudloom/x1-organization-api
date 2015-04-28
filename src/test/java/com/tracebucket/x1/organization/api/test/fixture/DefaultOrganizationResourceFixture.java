package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultOrganizationResourceBuilder;

import java.util.UUID;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationResourceFixture {
    public static DefaultOrganizationResource standardOrganization() {
/*        Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);
        addresses.add(DefaultAddressResourceFixture.standardAddress());
        addresses.add(DefaultAddressResourceFixture.headOffice());

        Set<DefaultCurrencyResource> currencies = new HashSet<>(0);
        currencies.add(DefaultCurrencyResourceFixture.standardBaseCurrency());

        Set<DefaultTimezoneResource> timezones = new HashSet<DefaultTimezoneResource>(0);
        timezones.add(DefaultTimezoneResourceFixture.standardTimezone());

        Set<DefaultPersonResource> contactPersons = new HashSet<DefaultPersonResource>(0);
        contactPersons.add(DefaultPersonResourceFixture.standardPerson());

        Set<DefaultPhoneResource> phones = new HashSet<DefaultPhoneResource>(0);
        phones.add(DefaultPhoneResourceFixture.standardPhone());

        Set<DefaultEmailResource> emails = new HashSet<DefaultEmailResource>(0);
        emails.add(DefaultEmailResourceFixture.standardEmail());

        Set<DefaultOrganizationUnitResource> organizationUnits = new HashSet<DefaultOrganizationUnitResource>(0);
        organizationUnits.add(DefaultOrganizationUnitResourceFixture.standardOrganizationUnitResource());*/

        DefaultOrganizationResource organization = DefaultOrganizationResourceBuilder.anOrganizationBuilder()
                .withName(UUID.randomUUID().toString())
                .withDescription(UUID.randomUUID().toString())
                .withImage(UUID.randomUUID().toString())
                .withCode(UUID.randomUUID().toString())
                .withWebsite(UUID.randomUUID().toString())
/*                .withAddresses(addresses)
                .withCurrencies(currencies)
                .withContactPersons(contactPersons)
                .withEmails(emails)
                .withPhones(phones)
                .withTimezones(timezones)
                .withOrganizationUnits(organizationUnits)*/
                .build();
        return organization;
    }
}