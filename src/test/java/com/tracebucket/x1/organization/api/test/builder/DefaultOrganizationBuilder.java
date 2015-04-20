package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.CurrencyType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.rest.resource.DefaultCurrencyResource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultOrganizationBuilder {
    private String code;
    private String name;
    private String description;
    private String website;
    protected String image;
/*    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);
    private Set<DefaultCurrency> currencies = new HashSet<DefaultCurrency>(0);
    private Set<DefaultTimezone> timezones = new HashSet<DefaultTimezone>(0);
    private Set<DefaultPerson> contactPersons = new HashSet<DefaultPerson>(0);
    private Set<DefaultPhone> phones = new HashSet<DefaultPhone>(0);
    private Set<DefaultEmail> emails = new HashSet<DefaultEmail>(0);
    private Set<DefaultOrganizationUnit> organizationUnits = new HashSet<DefaultOrganizationUnit>(0);*/

    private DefaultOrganizationBuilder(){ }

    public static DefaultOrganizationBuilder anOrganizationBuilder(){
        return new DefaultOrganizationBuilder();
    }

    public DefaultOrganizationBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultOrganizationBuilder withCode(String code){
        this.code = code;
        return this;
    }

    public DefaultOrganizationBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public DefaultOrganizationBuilder withWebsite(String website){
        this.website = website;
        return this;
    }

    public DefaultOrganizationBuilder withImage(String image){
        this.image = image;
        return this;
    }

/*    public DefaultOrganizationBuilder withAddresses(Set<DefaultAddress> addresses){
        this.addresses = addresses;
        return this;
    }

    public DefaultOrganizationBuilder withCurrencies(Set<DefaultCurrency> currencies){
        this.currencies = currencies;
        return this;
    }

    public DefaultOrganizationBuilder withTimezones(Set<DefaultTimezone> timezones){
        this.timezones = timezones;
        return this;
    }

    public DefaultOrganizationBuilder withContactPersons(Set<DefaultPerson> contactPersons){
        this.contactPersons = contactPersons;
        return this;
    }

    public DefaultOrganizationBuilder withPhones(Set<DefaultPhone> phones){
        this.phones = phones;
        return this;
    }

    public DefaultOrganizationBuilder withEmails(Set<DefaultEmail> emails){
        this.emails = emails;
        return this;
    }

    public DefaultOrganizationBuilder withOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits){
        this.organizationUnits = organizationUnits;
        return this;
    }*/

    public DefaultOrganization build(){
        DefaultOrganization organization = new DefaultOrganization();
        organization.setName(name);
        organization.setImage(image);
        organization.setCode(code);
        organization.setDescription(description);
        organization.setWebsite(website);
/*        organization.setAddresses(addresses);
        organization.setContactPersons(contactPersons);
        organization.setCurrencies(currencies);
        organization.setTimezones(timezones);
        organization.setPhones(phones);
        organization.setEmails(emails);
        organization.setOrganizationUnits(organizationUnits);*/
        return organization;
    }
}