package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.organization.api.domain.OrganizationFunction;
import com.tracebucket.x1.organization.api.domain.OrganizationUnit;
import com.tracebucket.x1.organization.api.enums.converter.OrganizationFunctionConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 14-04-2015.
 * JPA Entity For Organization Unit
 */
@Entity
@Table(name = "ORGANIZATION_UNIT")
public class DefaultOrganizationUnit extends BaseEntity implements OrganizationUnit {

    @Column(name = "NAME", nullable = false, unique = true)
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
    @Convert(converter = OrganizationFunctionConverter.class)
    @Column(name = "ORGANIZATION_FUNCTION")
    @Fetch(value = FetchMode.JOIN)
    private Set<OrganizationFunction> organizationFunctions = new HashSet<OrganizationFunction>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_UNIT_ADDRESS", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name="ORGANIZATION_UNIT_CONTACT_PERSONS",
            joinColumns={ @JoinColumn(name="ORGANIZATION_UNIT__ID", referencedColumnName="ID") },
            inverseJoinColumns={ @JoinColumn(name="PERSON__ID", referencedColumnName="ID", unique=true) }
    )
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPerson> contactPersons = new HashSet<DefaultPerson>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_UNIT_CONTACT_PHONE", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPhone> phones = new HashSet<DefaultPhone>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_UNIT_CONTACT_EMAIL", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultEmail> emails = new HashSet<DefaultEmail>(0);

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_UNIT_DEPARTMENT", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "DEPARTMENT__ID", referencedColumnName = "ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultDepartment> departments = new HashSet<DefaultDepartment>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="ORGANIZATION_UNIT__ID", referencedColumnName="ID")
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultBusinessLine> businessLines = new HashSet<DefaultBusinessLine>(0);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultOrganizationUnit> children = new HashSet<DefaultOrganizationUnit>(0);

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_UNIT_POSITION", joinColumns = @JoinColumn(name = "ORGANIZATION_UNIT__ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "POSITION__ID", referencedColumnName = "ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPosition> positions = new HashSet<DefaultPosition>(0);

 /*   @OneToMany(mappedBy="organizationUnit", fetch = FetchType.EAGER)
    private Set<SaleChannel> saleChannels = new HashSet<SaleChannel>(0);*/

    /**
     * Check If Organization unit has addresses
     * @return
     */
    @Override
    public Boolean hasAddresses() {
        if(this.addresses == null)
            return false;
        if(this.addresses.size() <= 0)
            return false;
        return true;
    }

    /**
     * Add Address
     * @param address
     * @return
     */
    @Override
    public DefaultOrganizationUnit address(DefaultAddress address) {
        //add address to 'this' organizationUnit addresses
        this.addresses.add(address);
        return this;
    }

    /**
     * Get Addresses
     * @return
     */
    @Override
    public Set<DefaultAddress> addresses() {
        return this.addresses;
    }

    /**
     * Set Parent
     * @param parent
     */
    public void setParent(DefaultOrganizationUnit parent) {
        //set parent of 'this' organizationUnit
        this.parent = parent;
    }

    /**
     * Set Organization
     * @param organization
     */
    public void setOrganization(DefaultOrganization organization) {
        //set organization of 'this' organizationUnit
        this.organization = organization;
    }

    /**
     * Get Parent
     * @return
     */
    @Override
    public DefaultOrganizationUnit parent() {
        //returns parent of 'this' organizationUnit
        return this.parent;
    }

    /**
     * Get Children
     * @return
     */
    @Override
    public Set<DefaultOrganizationUnit> children() {
        //return children of this organiztionUnit
        return this.children;
    }

    /**
     * Set Owner
     * @return
     */
    @Override
    public DefaultOrganizationUnit owner() {
        return null;
    }

    /**
     * Set OrganizationFunction
     * @param organizationFunction
     * @return
     */
    @Override
    public DefaultOrganizationUnit function(OrganizationFunction organizationFunction) {
        //add organizationFunction of 'this' organizationUnit
        this.organizationFunctions.add(organizationFunction);
        return this;
    }

    /**
     * Get Organization Functions
     * @return
     */
    @Override
    public Set<OrganizationFunction> functions() {
        return this.organizationFunctions;
    }

    /**
     * Add Department
     * @param department
     * @return
     */
    @Override
    public DefaultOrganizationUnit department(DefaultDepartment department) {
        //add 'this' organizationUnits department
        this.departments.add(department);
        return this;
    }

    /**
     * Get Name
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get Description
     * @return
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Get Organization
     * @return
     */
    @Override
    public DefaultOrganizationUnit getOrganization() {
        return null;
    }

    /**
     * Get Parent
     * @return
     */
    @Override
    public DefaultOrganizationUnit getParent() {
        return parent;
    }

    /**
     * Get OrganizationFunctions
     * @return
     */
    @Override
    public Set<OrganizationFunction> getOrganizationFunctions() {
        return organizationFunctions;
    }

    /**
     * Get Addresses
     * @return
     */
    @Override
    public Set<DefaultAddress> getAddresses() {
        return addresses;
    }

    /**
     * Get Departments
     * @return
     */
    @Override
    public Set<DefaultDepartment> getDepartments() {
        return departments;
    }

    /**
     * Get BusinessLines
     * @return
     */
    @Override
    public Set<DefaultBusinessLine> getBusinessLines() {
        return businessLines;
    }

    /**
     * Get Children
     * @return
     */
    @Override
    public Set<DefaultOrganizationUnit> getChildren() {
        return children;
    }

    /**
     * Add Child Organization Unit
     * @param child
     */
    @Override
    public void addChild(DefaultOrganizationUnit child) {
        //set childs parent as this
        child.setParent(this);
        //add child to 'this' organizationUnits children
        this.children.add(child);
    }

    /**
     * Get ContactPersons
     * @return
     */
    @Override
    public Set<DefaultPerson> getContactPersons() {
        return contactPersons;
    }

    /**
     * Get Phones
     * @return
     */
    @Override
    public Set<DefaultPhone> getPhones() {
        return phones;
    }

    /**
     * Get Emails
     * @return
     */
    @Override
    public Set<DefaultEmail> getEmails() {
        return emails;
    }

    /**
     * Get Id
     * @return
     */
    @Override
    public Object getId() {
        return super.getId();
    }

    /**
     * Set Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set Description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set Organization Functions
     * @param organizationFunctions
     */
    public void setOrganizationFunctions(Set<OrganizationFunction> organizationFunctions) {
        this.organizationFunctions = organizationFunctions;
    }

    /**
     * Set Addresses
     * @param addresses
     */
    public void setAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
    }

    /**
     * Set ContactPersons
     * @param contactPersons
     */
    public void setContactPersons(Set<DefaultPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    /**
     * Set Phones
     * @param phones
     */
    public void setPhones(Set<DefaultPhone> phones) {
        this.phones = phones;
    }

    /**
     * Set Emails
     * @param emails
     */
    public void setEmails(Set<DefaultEmail> emails) {
        this.emails = emails;
    }

    /**
     * Set Departments
     * @param departments
     */
    public void setDepartments(Set<DefaultDepartment> departments) {
        this.departments = departments;
    }

    /**
     * Set BusinessLines
     * @param businessLines
     */
    public void setBusinessLines(Set<DefaultBusinessLine> businessLines) {
        this.businessLines = businessLines;
    }

    /**
     * Set Children
     * @param children
     */
    public void setChildren(Set<DefaultOrganizationUnit> children) {
        this.children = children;
    }

    /**
     * Get Positions
     * @return
     */
    public Set<DefaultPosition> getPositions() {
        return positions;
    }

    /**
     * Set Positions
     * @param positions
     */
    public void setPositions(Set<DefaultPosition> positions) {
        this.positions = positions;
    }
}
