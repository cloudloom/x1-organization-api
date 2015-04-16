package com.tracebucket.x1.organization.api.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.organization.api.domain.OrganizationFunction;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultBusinessLine;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationUnitResource extends BaseResource {
    private String name;
    private String description;
    private DefaultOrganizationResource organization;
    private DefaultOrganizationUnitResource parent;
    private Set<OrganizationFunction> organizationFunctions = new HashSet<OrganizationFunction>(0);
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);
    private Set<DefaultPersonResource> contactPersons = new HashSet<DefaultPersonResource>(0);
    private Set<DefaultPhoneResource> phones = new HashSet<DefaultPhoneResource>(0);
    private Set<DefaultEmailResource> emails = new HashSet<DefaultEmailResource>(0);
    private Set<DefaultDepartmentResource> departments = new HashSet<DefaultDepartmentResource>(0);
    private Set<DefaultBusinessLineResource> businessLines = new HashSet<DefaultBusinessLineResource>(0);
    private Set<DefaultOrganizationUnitResource> children = new HashSet<DefaultOrganizationUnitResource>(0);

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

    public DefaultOrganizationResource getOrganization() {
        return organization;
    }

    public void setOrganization(DefaultOrganizationResource organization) {
        this.organization = organization;
    }

    public DefaultOrganizationUnitResource getParent() {
        return parent;
    }

    public void setParent(DefaultOrganizationUnitResource parent) {
        this.parent = parent;
    }

    public Set<OrganizationFunction> getOrganizationFunctions() {
        return organizationFunctions;
    }

    public void setOrganizationFunctions(Set<OrganizationFunction> organizationFunctions) {
        this.organizationFunctions = organizationFunctions;
    }

    public Set<DefaultAddressResource> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
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

    public Set<DefaultDepartmentResource> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<DefaultDepartmentResource> departments) {
        this.departments = departments;
    }

    public Set<DefaultBusinessLineResource> getBusinessLines() {
        return businessLines;
    }

    public void setBusinessLines(Set<DefaultBusinessLineResource> businessLines) {
        this.businessLines = businessLines;
    }

    public Set<DefaultOrganizationUnitResource> getChildren() {
        return children;
    }

    public void setChildren(Set<DefaultOrganizationUnitResource> children) {
        this.children = children;
    }
}