package com.tracebucket.x1.organization.api.rest.resource;


import com.tracebucket.tron.assembler.BaseResource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationResource extends BaseResource {

    //@NotNull(message = "Organization code cannot be empty")
    //@Size(min = 3, max = 8)
    private String code;
    private String name;
    private String description;
    private String website;
    protected String image;
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);
    private Set<DefaultCurrencyResource> currencies = new HashSet<DefaultCurrencyResource>(0);
    private Set<DefaultTimezoneResource> timezones = new HashSet<DefaultTimezoneResource>(0);
    private Set<DefaultPersonResource> contactPersons = new HashSet<DefaultPersonResource>(0);
    private Set<DefaultPhoneResource> phones = new HashSet<DefaultPhoneResource>(0);
    private Set<DefaultEmailResource> emails = new HashSet<DefaultEmailResource>(0);
    private Set<DefaultOrganizationUnitResource> organizationUnits = new HashSet<DefaultOrganizationUnitResource>(0);

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<DefaultAddressResource> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
    }

    public Set<DefaultCurrencyResource> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Set<DefaultCurrencyResource> currencies) {
        this.currencies = currencies;
    }

    public Set<DefaultTimezoneResource> getTimezones() {
        return timezones;
    }

    public void setTimezones(Set<DefaultTimezoneResource> timezones) {
        this.timezones = timezones;
    }

    public Set<DefaultPersonResource> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(Set<DefaultPersonResource> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public Set<DefaultPhoneResource> getPhones() {
        return phones;
    }

    public void setPhones(Set<DefaultPhoneResource> phones) {
        this.phones = phones;
    }

    public Set<DefaultEmailResource> getEmails() {
        return emails;
    }

    public void setEmails(Set<DefaultEmailResource> emails) {
        this.emails = emails;
    }

    public Set<DefaultOrganizationUnitResource> getOrganizationUnits() {
        return organizationUnits;
    }

    public void setOrganizationUnits(Set<DefaultOrganizationUnitResource> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }
}