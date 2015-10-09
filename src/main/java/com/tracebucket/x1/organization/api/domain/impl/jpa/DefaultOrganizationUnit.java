package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.organization.api.domain.OrganizationFunction;
import com.tracebucket.x1.organization.api.domain.OrganizationUnit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 14-04-2015.
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
    @Enumerated(EnumType.STRING)
    @Column(name = "ORGANIZATION_FUNCTION", nullable = false, columnDefinition = "ENUM('SALES','PURCHASE') default 'SALES'")
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

    @Override
    public Boolean hasAddresses() {
        if(this.addresses == null)
            return false;
        if(this.addresses.size() <= 0)
            return false;
        return true;
    }

    @Override
    public DefaultOrganizationUnit address(DefaultAddress address) {
        this.addresses.add(address);
        return this;
    }

    @Override
    public Set<DefaultAddress> addresses() {
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
    public Set<DefaultAddress> getAddresses() {
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
    public Set<DefaultPerson> getContactPersons() {
        return contactPersons;
    }

    @Override
    public Set<DefaultPhone> getPhones() {
        return phones;
    }

    @Override
    public Set<DefaultEmail> getEmails() {
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

    public void setAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
    }

    public void setContactPersons(Set<DefaultPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public void setPhones(Set<DefaultPhone> phones) {
        this.phones = phones;
    }

    public void setEmails(Set<DefaultEmail> emails) {
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

    public Set<DefaultPosition> getPositions() {
        return positions;
    }

    public void setPositions(Set<DefaultPosition> positions) {
        this.positions = positions;
    }
}
