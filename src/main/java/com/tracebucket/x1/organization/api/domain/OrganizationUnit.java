package com.tracebucket.x1.organization.api.domain;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultBusinessLine;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;

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

	Boolean hasSaleChannels();

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

	Set<DefaultPerson> getContactPersons();

	Set<DefaultPhone> getPhones();

	Set<DefaultEmail> getEmails();

    void setParent(DefaultOrganizationUnit parent);

    void setOrganization(DefaultOrganization organization);

    Object getId();
}
