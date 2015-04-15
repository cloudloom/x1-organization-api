package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.dictionary.api.domain.Email;
import com.tracebucket.x1.dictionary.api.domain.Person;
import com.tracebucket.x1.dictionary.api.domain.Phone;
import com.tracebucket.x1.organization.api.domain.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 14-04-2015.
 */
@Entity
@Table(name = "ORGANIZATION_UNIT")
public class DefaultOrganizationUnit extends BaseEntity implements OrganizationUnit {

    @Column(name = "NAME", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.EAGER)
    private String description;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="ORGANIZATION__ID")
    private DefaultOrganization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    private DefaultOrganizationUnit parent;

    @ElementCollection(targetClass=OrganizationFunction.class, fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_UNIT_FUNCTION", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID"))
    @Enumerated(EnumType.STRING)
    @Column(name = "ORGANIZATION_FUNCTION", nullable = false, columnDefinition = "ENUM('SALES','PURCHASE') default 'SALES'")
    private Set<OrganizationFunction> organizationFunctions = new HashSet<OrganizationFunction>(0);

    @ElementCollection
    @JoinTable(name = "ORGANIZATION_UNIT_ADDRESS", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID"))
    private Set<Address> addresses = new HashSet<Address>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name="ORGANIZATION_UNIT_CONTACT_PERSONS",
            joinColumns={ @JoinColumn(name="ORGANIZATION_UNIT__ID", referencedColumnName="ID") },
            inverseJoinColumns={ @JoinColumn(name="PERSON__ID", referencedColumnName="ID", unique=true) }
    )
    private Set<Person> contactPersons = new HashSet<Person>(0);

    @ElementCollection
    @JoinTable(name = "ORGANIZATION_UNIT_CONTACT_PHONE", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID"))
    private Set<Phone> phones = new HashSet<Phone>(0);

    @ElementCollection
    @JoinTable(name = "ORGANIZATION_UNIT_CONTACT_EMAIL", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID"))
    private Set<Email> emails = new HashSet<Email>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="ORGANIZATION_UNIT__ID", referencedColumnName="ID")
    private Set<DefaultDepartment> departments = new HashSet<DefaultDepartment>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="ORGANIZATION_UNIT__ID", referencedColumnName="ID")
    private Set<DefaultBusinessLine> businessLines = new HashSet<DefaultBusinessLine>(0);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<DefaultOrganizationUnit> children = new HashSet<DefaultOrganizationUnit>(0);

 /*   @OneToMany(mappedBy="organizationUnit", fetch = FetchType.EAGER)
    private Set<SaleChannel> saleChannels = new HashSet<SaleChannel>(0);*/

    @Override
    public Boolean hasAddresses() {
        if(this.addresses == null)
            return false;
        if(this.addresses.size() <= 0)
            return false;
        return true;
    }

    @Override
    public DefaultOrganizationUnit address(Address address) {
        this.addresses.add(address);
        return this;
    }

    @Override
    public Set<Address> addresses() {
        return this.addresses;
    }

    public void setParent(DefaultOrganizationUnit parent) {
        this.parent = parent;
    }

    public void setOrganization(DefaultOrganization organization) {
        this.organization = organization;
    }

    @Override
    public DefaultOrganizationUnit parent() {
        return this.parent;
    }

    @Override
    public Set<DefaultOrganizationUnit> children() {
        return this.children;
    }

    @Override
    public DefaultOrganizationUnit owner() {
        return null;
    }

    @Override
    public DefaultOrganizationUnit function(OrganizationFunction organizationFunction) {
        this.organizationFunctions.add(organizationFunction);
        return this;
    }

    @Override
    public Set<OrganizationFunction> functions() {
        return this.organizationFunctions;
    }

    @Override
    public DefaultOrganizationUnit department(DefaultDepartment department) {
        this.departments.add(department);
        return this;
    }

    @Override
    public Boolean hasSaleChannels() {
       //TODO
        return null;
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
    public DefaultOrganizationUnit getOrganization() {
        return null;
    }

    @Override
    public DefaultOrganizationUnit getParent() {
        return parent;
    }

    @Override
    public Set<OrganizationFunction> getOrganizationFunctions() {
        return organizationFunctions;
    }

    @Override
    public Set<Address> getAddresses() {
        return addresses;
    }

    @Override
    public Set<DefaultDepartment> getDepartments() {
        return departments;
    }

    @Override
    public Set<DefaultBusinessLine> getBusinessLines() {
        return businessLines;
    }

    @Override
    public Set<DefaultOrganizationUnit> getChildren() {
        return children;
    }

    @Override
    public void addChild(DefaultOrganizationUnit child) {
        child.setParent(this);
        this.children.add(child);
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
    public Set<Email> getEmails() {
        return emails;
    }

    @Override
    public Object getId() {
        return super.getId();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrganizationFunctions(Set<OrganizationFunction> organizationFunctions) {
        this.organizationFunctions = organizationFunctions;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void setContactPersons(Set<Person> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    public void setDepartments(Set<DefaultDepartment> departments) {
        this.departments = departments;
    }

    public void setBusinessLines(Set<DefaultBusinessLine> businessLines) {
        this.businessLines = businessLines;
    }

    public void setChildren(Set<DefaultOrganizationUnit> children) {
        this.children = children;
    }
}
