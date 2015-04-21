package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.annotation.DomainMethod;
import com.tracebucket.tron.ddd.domain.BaseAggregateRoot;
import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.dictionary.api.domain.*;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.Organization;
import com.tracebucket.x1.organization.api.domain.OrganizationUnit;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by Vishwajit on 13-04-2015.
 */
@Entity
@Table(name = "ORGANIZATION")
public class DefaultOrganization extends BaseAggregateRoot implements Organization{

    @Column(name = "CODE", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String code;

    @Column(name = "NAME", nullable = false, unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.EAGER)
    private String description;

    @Column(name = "WEBSITE", unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String website;

    @Column(name = "IMAGE")
    @Basic(fetch = FetchType.EAGER)
    protected String image;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_ADDRESS", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_CURRENCY", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<DefaultCurrency> currencies = new HashSet<DefaultCurrency>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_TIMEZONE", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<DefaultTimezone> timezones = new HashSet<DefaultTimezone>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name = "ORGANIZATION_CONTACT_PERSON",
            joinColumns = { @JoinColumn(name = "ORGANIZATION__ID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "PERSON__ID", referencedColumnName = "ID", unique = true) }
    )
    private Set<DefaultPerson> contactPersons = new HashSet<DefaultPerson>(0);

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DefaultOrganizationUnit> organizationUnits = new HashSet<DefaultOrganizationUnit>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_CONTACT_PHONE", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<DefaultPhone> phones = new HashSet<DefaultPhone>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_CONTACT_EMAIL", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<DefaultEmail> emails = new HashSet<DefaultEmail>(0);

    public DefaultOrganization() {
    }

    public DefaultOrganization(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public DefaultOrganization(String name, String code, String description, String website, String image) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.website = website;
        this.image = image;
    }


    @Override
    @DomainMethod(event = "BaseCurrencyAdded")
    public void addBaseCurrency(DefaultCurrency baseCurrency) {
        if(baseCurrency != null) {
            baseCurrency.setCurrencyType(CurrencyType.BASE);
        }
        this.currencies.add(baseCurrency);
    }

    @Override
    @DomainMethod(event = "TimezoneAdded")
    public void addTimezone(DefaultTimezone timezone) {
        this.timezones.add(timezone);
    }

    @Override
    @DomainMethod(event = "OrganizationUnitAdded")
    public void addOrganizationUnit(DefaultOrganizationUnit organizationUnit) {
        if(organizationUnit != null) {
            organizationUnit.setOrganization(this);
            this.organizationUnits.add(organizationUnit);
        }
    }

    @Override
    @DomainMethod(event = "OrganizationUnitBelowAdded")
    public void addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit, DefaultOrganizationUnit parentOrganizationUnit) {
        OrganizationUnit parentOrganizationUnitFetched = organizationUnits.stream()
                .filter(t -> t.getId().equals(parentOrganizationUnit.getId()))
                .findFirst()
                .orElse(null);
        if(parentOrganizationUnitFetched != null) {
            parentOrganizationUnitFetched.addChild(organizationUnit);
        }
    }

    @Override
    @DomainMethod(event = "ContactPersonAdded")
    public void addContactPerson(DefaultPerson contactPerson) {
        if(contactPerson != null) {
            contactPerson.setDefaultContactPerson(false);
            this.contactPersons.add(contactPerson);
        }
    }

    @Override
    @DomainMethod(event = "DefaultContactPersonSet")
    public void setDefaultContactPerson(DefaultPerson defaultContactPerson) {
        if(defaultContactPerson != null) {
            Stream<DefaultPerson> stream = this.contactPersons.stream();
            stream.forEach(t -> t.setDefaultContactPerson(false));
            defaultContactPerson.setDefaultContactPerson(true);
            this.contactPersons.add(defaultContactPerson);
        }
    }

    @Override
    @DomainMethod(event = "ContactNumberAdded")
    public void addContactNumber(DefaultPhone phone) {
        if(phone != null) {
            phone.setDefaultPhone(false);
            this.phones.add(phone);
        }
    }

    @Override
    @DomainMethod(event = "DefaultContactNumberSet")
    public void setDefaultContactNumber(DefaultPhone defaultContactNumber) {
        if(defaultContactNumber != null) {
            Stream<DefaultPhone> stream = this.phones.stream();
            stream.forEach(t -> t.setDefaultPhone(false));
            defaultContactNumber.setDefaultPhone(true);
            this.phones.add(defaultContactNumber);
        }
    }

    @Override
    @DomainMethod(event = "EmailAdded")
    public void addEmail(DefaultEmail email) {
        if(email != null) {
            email.setDefaultEmail(false);
            this.emails.add(email);
        }
    }

    @Override
    @DomainMethod(event = "DefaultEmailSet")
    public void setDefaultEmail(DefaultEmail defaultEmail) {
        if(defaultEmail != null) {
            Stream<DefaultEmail> stream = this.emails.stream();
            stream.forEach(t -> t.setDefaultEmail(false));
            defaultEmail.setDefaultEmail(true);
            this.emails.add(defaultEmail);
        }
    }

    @Override
    @DomainMethod(event = "HeadOfficeSet")
    public void setHeadOffice(DefaultAddress headOfficeAddress) {
        if(headOfficeAddress != null) {
            headOfficeAddress.setAddressType(AddressType.HEAD_OFFICE);
            headOfficeAddress.setDefaultAddress(true);
            this.addresses.add(headOfficeAddress);
        }
    }

    @Override
    @DomainMethod(event = "HeadOfficeModedTo")
    public void moveHeadOfficeTo(DefaultAddress newHeadOfficeAddress) {
        if(newHeadOfficeAddress != null) {
            Stream<DefaultAddress> stream = this.addresses.stream().filter(t -> t.getAddressType() == AddressType.HEAD_OFFICE);
            stream.forEach(t -> t.setDefaultAddress(false));
            newHeadOfficeAddress.setAddressType(AddressType.HEAD_OFFICE);
            newHeadOfficeAddress.setDefaultAddress(true);
            this.addresses.add(newHeadOfficeAddress);
        }
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public DefaultAddress getHeadOfficeAddress() {
        DefaultAddress headOfficeAddress =
                addresses.parallelStream()
                        .filter(t -> t.getAddressType() == AddressType.HEAD_OFFICE)
                        .filter(t -> t.isDefaultAddress())
                        .findFirst()
                        .orElse(null);
        return headOfficeAddress;
    }

    @Override
    public Set<DefaultCurrency> getBaseCurrencies() {
        Set<DefaultCurrency> baseCurrencies = new HashSet<DefaultCurrency>();
        if(currencies != null && currencies.size() > 0) {
            for (DefaultCurrency currency : currencies) {
                if(currency.getCurrencyType().equals(CurrencyType.BASE)) {
                    baseCurrencies.add(currency);
                }
            }
        }
        return baseCurrencies;
    }

    @Override
    public Set<DefaultOrganizationUnit> getOrganizationUnits() {
        return this.organizationUnits;
    }

    @Override
    public Set<DefaultPhone> getContactNumbers() {
        return this.phones;
    }

    @Override
    public Set<DefaultEmail> getEmails() {
        return this.emails;
    }

    @Override
    public Set<DefaultTimezone> getTimezones() {
        return this.timezones;
    }

    @Override
    public Set<DefaultPerson> getContactPersons() {
        return contactPersons;
    }

    @Override
    public Set<DefaultPhone> getPhones() {
        return phones;
    }

    @Override
    public Set<DefaultAddress> getAddresses() {
        return addresses;
    }

    @Override
    public Set<DefaultCurrency> getCurrencies() {
        return currencies;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void setOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }

    @Override
    public void setAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void setCurrencies(Set<DefaultCurrency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public void setTimezones(Set<DefaultTimezone> timezones) {
        this.timezones = timezones;
    }

    @Override
    public void setContactPersons(Set<DefaultPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    @Override
    public void setPhones(Set<DefaultPhone> phones) {
        this.phones = phones;
    }

    @Override
    public void setEmails(Set<DefaultEmail> emails) {
        this.emails = emails;
    }
}
