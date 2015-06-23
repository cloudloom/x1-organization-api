package com.tracebucket.x1.organization.api.domain;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.organization.api.domain.impl.jpa.*;

import java.util.Set;

/**
 * Created by ffl on 09-04-2015.
 */
public interface OrganizationUnit {
	Boolean hasAddresses();

	DefaultOrganizationUnit address(DefaultAddress address);

	Set<DefaultAddress> addresses();

	DefaultOrganizationUnit parent();

	Set<DefaultOrganizationUnit> children();

	DefaultOrganizationUnit owner();

	DefaultOrganizationUnit function(OrganizationFunction organizationFunction);

	Set<OrganizationFunction> functions();

	DefaultOrganizationUnit department(DefaultDepartment department);

	String getName();

	String getDescription();

	DefaultOrganizationUnit getOrganization();

	DefaultOrganizationUnit getParent();

	Set<OrganizationFunction> getOrganizationFunctions();

	Set<DefaultAddress> getAddresses();

	Set<DefaultDepartment> getDepartments();

	Set<DefaultBusinessLine> getBusinessLines();

	Set<DefaultOrganizationUnit> getChildren();

	void addChild(DefaultOrganizationUnit child);

    Set<DefaultPosition> getPositions();

    void setPositions(Set<DefaultPosition> positions);

	Set<DefaultPerson> getContactPersons();

	Set<DefaultPhone> getPhones();

	Set<DefaultEmail> getEmails();

    void setParent(DefaultOrganizationUnit parent);

    void setOrganization(DefaultOrganization organization);

    Object getId();
}
