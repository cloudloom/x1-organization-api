package com.tracebucket.x1.organization.api.rest.resource;


import com.tracebucket.tron.assembler.BaseResource;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.executable.ValidateOnExecution;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationResource extends BaseResource implements Comparable<DefaultOrganizationResource>{

    @NotNull
    @Size(min = 3, max = 8)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String code;

    @NotNull
    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[a-zA-Z0-9\\-@&]*$")//alphanumeric @ - &
    private String name;

    @Size(min = 0, max = 255)
    private String description;

    @Size(min = 2, max = 250)
    @URL
    private String website;

    @Size(min = 1, max = 250)
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)")
    protected String image;

    @Valid
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);

    @Valid
    private Set<DefaultCurrencyResource> currencies = new HashSet<DefaultCurrencyResource>(0);

    @Valid
    private Set<DefaultTimezoneResource> timezones = new HashSet<DefaultTimezoneResource>(0);

    @Valid
    private Set<DefaultPersonResource> contactPersons = new HashSet<DefaultPersonResource>(0);

    @Valid
    private Set<DefaultPhoneResource> phones = new HashSet<DefaultPhoneResource>(0);

    @Valid
    private Set<DefaultEmailResource> emails = new HashSet<DefaultEmailResource>(0);

    @Valid
    private Set<DefaultOrganizationUnitResource> organizationUnits = new HashSet<DefaultOrganizationUnitResource>(0);

    private Set<DefaultPositionResource> positions = new HashSet<DefaultPositionResource>(0);

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

    public Set<DefaultPositionResource> getPositions() {
        return positions;
    }

    public void setPositions(Set<DefaultPositionResource> positions) {
        this.positions = positions;
    }


    @Override
    public int compareTo(DefaultOrganizationResource o) {
        if(o != null) {
            return o.getName().compareTo(this.getName());
        }
        return 0;
    }
}