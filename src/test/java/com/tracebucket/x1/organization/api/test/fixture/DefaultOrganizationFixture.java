package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.test.builder.DefaultOrganizationBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultOrganizationFixture {
    public static DefaultOrganization standardOrganization() {
        Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);
        addresses.add(DefaultAddressFixture.standardAddress());
        addresses.add(DefaultAddressFixture.headOffice());

        Set<DefaultCurrency> currencies = new HashSet<>(0);
        currencies.add(DefaultCurrencyFixture.standardBaseCurrency());

        Set<DefaultTimezone> timezones = new HashSet<DefaultTimezone>(0);
        timezones.add(DefaultTimezoneFixture.standardTimezone());

        Set<DefaultPerson> contactPersons = new HashSet<DefaultPerson>(0);
        contactPersons.add(DefaultPersonFixture.standardPerson());

        Set<DefaultPhone> phones = new HashSet<DefaultPhone>(0);
        phones.add(DefaultPhoneFixture.standardPhone());

        Set<DefaultEmail> emails = new HashSet<DefaultEmail>(0);
        emails.add(DefaultEmailFixture.standardEmail());

        Set<DefaultOrganizationUnit> organizationUnits = new HashSet<DefaultOrganizationUnit>(0);
        organizationUnits.add(DefaultOrganizationUnitFixture.standardOrganizationUnit());

        DefaultOrganization organization = DefaultOrganizationBuilder.anOrganizationBuilder()
                //.withName("ABC Bank")
                .withName(UUID.randomUUID().toString())
                .withDescription(UUID.randomUUID().toString())
                .withImage(UUID.randomUUID().toString())
                .withCode(UUID.randomUUID().toString().substring(0, 7))
                .withWebsite(UUID.randomUUID().toString())
                .withOrganizationUnits(organizationUnits)
                .withAddresses(addresses)
                .withCurrencies(currencies)
                .withContactPersons(contactPersons)
                .withEmails(emails)
                .withPhones(phones)
                .withTimezones(timezones)
                .withOrganizationUnits(organizationUnits)
                .build();
        return organization;
    }
}
