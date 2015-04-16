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

    @ElementCollection
    @JoinTable(name = "ORGANIZATION_ADDRESS", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

 /*   @ElementCollection
    @CollectionTable(name = "ORGANIZATION_CURRENCY", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    @Column(name = "CURRENCY_TYPE")
    @MapKeyJoinColumn(name = "CURRENCY__ID", referencedColumnName = "ID")
    private Map<Currency, CurrencyType> currencies = new HashMap<>(0);*/

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name="ORGANIZATION_TIMEZONE",
            joinColumns={ @JoinColumn(name="ORGANIZATION__ID", referencedColumnName="ID") },
            inverseJoinColumns={ @JoinColumn(name="TIMEZONE__ID", referencedColumnName="ID", unique=true) }
    )
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

    @ElementCollection
    @JoinTable(name = "ORGANIZATION_CONTACT_PHONE", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<DefaultPhone> phones = new HashSet<DefaultPhone>(0);

    @ElementCollection
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


/*    @Override
    @DomainMethod(event = "BaseCurrencyAdded")
    public void addBaseCurrency(Currency baseCurrency) {
        this.currencies.put(baseCurrency, CurrencyType.Base);
    }*/

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
                .get();
        if(parentOrganizationUnitFetched != null) {
            parentOrganizationUnitFetched.addChild(organizationUnit);
        }

    }
    @Override
    @DomainMethod(event = "ContactPersonAdded")
    public void addContactPerson(DefaultPerson contactPerson) {
        this.contactPersons.add(contactPerson);
    }

    @Override
    @DomainMethod(event = "DefaultContactPersonSet")
    public void setDefaultContactPerson(DefaultPerson defaultContactPerson) {
        this.contactPersons.add(defaultContactPerson);
    }

    @Override
    @DomainMethod(event = "ContactNumberAdded")
    public void addContactNumber(DefaultPhone phone) {
        this.phones.add(phone);
    }

    @Override
    @DomainMethod(event = "DefaultContactNumberSet")
    public void setDefaultContactNumber(DefaultPhone defaultContactNumber) {
        this.phones.add(defaultContactNumber);
    }

    @Override
    @DomainMethod(event = "EmailAdded")
    public void addEmail(DefaultEmail email) {
        this.emails.add(email);
    }

    @Override
    @DomainMethod(event = "DefaultEmailSet")
    public void setDefaultEmail(DefaultEmail defaultEmail) {
        this.emails.add(defaultEmail);
    }

    @Override
    @DomainMethod(event = "HeadOfficeSet")
    public void setHeadOffice(DefaultAddress headOfficeAddress) {
        this.addresses.add(headOfficeAddress);
    }

    @Override
    @DomainMethod(event = "HeadOfficeModedTo")
    public void moveHeadOfficeTo(DefaultAddress newHeadOfficeAddress) {
        this.addresses.add(newHeadOfficeAddress);
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
                                //.filter(t -> t.isCurrentAddress())
                        .findFirst()
                        .get();
        return headOfficeAddress;
    }

/*    @Override
    public Set<Currency> getBaseCurrencies() {
        Set<Currency> baseCurrencies = new HashSet<Currency>();

        if(currencies != null && currencies.size() > 0) {
            for (Map.Entry<Currency, CurrencyType> currencyTypeEntry : currencies.entrySet()) {
                if(currencyTypeEntry.getValue().equals(CurrencyType.Base)) {
                    baseCurrencies.add(currencyTypeEntry.getKey());
                }
            }
        }
        return baseCurrencies;
    }*/

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

/*    @Override
    public Map<Currency, CurrencyType> getCurrencies() {
        return currencies;
    }*/
}
