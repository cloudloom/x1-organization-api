package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.dictionary.api.domain.*;
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
public class DefaultOrganization extends BaseEntity implements Organization{


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
    private Set<Address> addresses = new HashSet<Address>(0);

    public enum CurrencyType{
        Base,
        Optional
    }

    @ElementCollection
    @CollectionTable(name = "ORGANIZATION_CURRENCY", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    @Column(name = "CURRENCY_TYPE")
    @MapKeyJoinColumn(name = "CURRENCY__ID", referencedColumnName = "ID")
    private Map<Currency, CurrencyType> currencies = new HashMap<>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name="ORGANIZATION_TIMEZONE",
            joinColumns={ @JoinColumn(name="ORGANIZATION__ID", referencedColumnName="ID") },
            inverseJoinColumns={ @JoinColumn(name="TIMEZONE__ID", referencedColumnName="ID", unique=true) }
    )
    private Set<Timezone> timezones = new HashSet<Timezone>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name = "ORGANIZATION_CONTACT_PERSON",
            joinColumns = { @JoinColumn(name = "ORGANIZATION__ID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "PERSON__ID", referencedColumnName = "ID", unique = true) }
    )
    private Set<Person> contactPersons = new HashSet<Person>(0);

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrganizationUnit> organizationUnits = new HashSet<OrganizationUnit>(0);

    @ElementCollection
    @JoinTable(name = "ORGANIZATION_CONTACT_PHONE", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<Phone> phones = new HashSet<Phone>(0);

    @ElementCollection
    @JoinTable(name = "ORGANIZATION_CONTACT_EMAIL", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    private Set<Email> emails = new HashSet<Email>(0);

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
    public void addBaseCurrency(Currency baseCurrency) {
        this.currencies.put(baseCurrency, CurrencyType.Base);
    }

    @Override
    public void addTimezone(Timezone timezone) {
        this.timezones.add(timezone);
    }

    /*@Override
    public void addOrganizationUnit(OrganizationUnit organizationUnit) {
        if(organizationUnit != null) {
            organizationUnit.setOrganization(this);
            this.organizationUnits.add(organizationUnit);
        }
    }*/

   /* @Override
    public void addOrganizationUnitBelow(OrganizationUnit organizationUnit, OrganizationUnit parentOrganizationUnit) {

        OrganizationUnit parentOrganizationUnitFetched = organizationUnits.parallelStream()
                .filter(t -> t.getId() == parentOrganizationUnit.getId())
                .findFirst()
                .get();
        if(parentOrganizationUnitFetched != null) {
            parentOrganizationUnitFetched.addChild(organizationUnit);
        }

    }*/

    @Override
    public void addContactPerson(Person contactPerson) {
        this.contactPersons.add(contactPerson);
    }

    @Override
    public void setDefaultContactPerson(Person defaultContactPerson) {
        this.contactPersons.add(defaultContactPerson);
    }

    @Override
    public void addContactNumber(Phone phone) {
        this.phones.add(phone);
    }

    @Override
    public void setDefaultContactNumber(Phone defaultContactNumber) {
        this.phones.add(defaultContactNumber);
    }

    @Override
    public void addEmail(Email email) {
        this.emails.add(email);
    }

    @Override
    public void setDefaultEmail(Email defaultEmail) {
        this.emails.add(defaultEmail);
    }

    @Override
    public void setHeadOffice(Address headOfficeAddress) {
        this.addresses.add(headOfficeAddress);
    }

    @Override
    public void moveHeadOfficeTo(Address newHeadOfficeAddress) {
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
    public Address getHeadOfficeAddress() {
        Address headOfficeAddress =
                addresses.parallelStream()
                        .filter(t -> t.getAddressType() == AddressType.HEAD_OFFICE)
                                //.filter(t -> t.isCurrentAddress())
                        .findFirst()
                        .get();
        return headOfficeAddress;
    }

    @Override
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
    }

    @Override
    public Set<OrganizationUnit> getOrganizationUnits() {
        return this.organizationUnits;
    }

    @Override
    public Set<Phone> getContactNumbers() {
        return this.phones;
    }

    @Override
    public Set<Email> getEmails() {
        return this.emails;
    }

    @Override
    public Set<Timezone> getTimezones() {
        return this.timezones;
    }

    @Override
    public Set<Person> getContactPersons() {
        return contactPersons;
    }

    @Override
    public Set<Phone> getPhones() {
        return phones;
    }

    @Override
    public Set<Address> getAddresses() {
        return addresses;
    }

 /*   @Override
    public Map<Currency, CurrencyType> getCurrencies() {
        return currencies;
    }*/
}
