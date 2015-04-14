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
public class DefaultOrganizationUnit extends BaseEntity implements OrganizationUnit {

    @Column(name = "NAME", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.EAGER)
    private String description;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="ORGANIZATION__ID")
    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    private OrganizationUnit parent;

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
    private Set<Department> departments = new HashSet<Department>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="ORGANIZATION_UNIT__ID", referencedColumnName="ID")
    private Set<BusinessLine> businessLines = new HashSet<BusinessLine>(0);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrganizationUnit> children = new HashSet<OrganizationUnit>(0);

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
    public OrganizationUnit address(Address address) {
        this.addresses.add(address);
        return this;
    }

    @Override
    public Set<Address> addresses() {
        return this.addresses;
    }

    @Override
    public OrganizationUnit parent() {
        return this.parent;
    }

    @Override
    public Set<OrganizationUnit> children() {
        return this.children;
    }

    @Override
    public OrganizationUnit owner() {
        return null;
    }

    @Override
    public OrganizationUnit function(OrganizationFunction organizationFunction) {
        this.organizationFunctions.add(organizationFunction);
        return this;
    }

    @Override
    public Set<OrganizationFunction> functions() {
        return this.organizationFunctions;
    }

    @Override
    public OrganizationUnit department(Department department) {
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
    public OrganizationUnit getOrganization() {
        return null;
    }

    @Override
    public OrganizationUnit getParent() {
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
    public Set<Department> getDepartments() {
        return departments;
    }

    @Override
    public Set<BusinessLine> getBusinessLines() {
        return businessLines;
    }

    @Override
    public Set<OrganizationUnit> getChildren() {
        return children;
    }

    @Override
    public void addChild(OrganizationUnit child) {
        //child.setParent(this);
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
}
