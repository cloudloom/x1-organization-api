package com.tracebucket.x1.organization.api.domain;

import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.dictionary.api.domain.Email;
import com.tracebucket.x1.dictionary.api.domain.Person;
import com.tracebucket.x1.dictionary.api.domain.Phone;

import java.util.Set;

/**
 * Created by ffl on 09-04-2015.
 */
public interface OrganizationUnit {
	Boolean hasAddresses();

	OrganizationUnit address(Address address);

	Set<Address> addresses();

	OrganizationUnit parent();

	Set<OrganizationUnit> children();

	OrganizationUnit owner();

	OrganizationUnit function(OrganizationFunction organizationFunction);

	Set<OrganizationFunction> functions();

	OrganizationUnit department(Department department);

	Boolean hasSaleChannels();

	String getName();

	String getDescription();

	OrganizationUnit getOrganization();

	OrganizationUnit getParent();

	Set<OrganizationFunction> getOrganizationFunctions();

	Set<Address> getAddresses();

	Set<Department> getDepartments();

	Set<BusinessLine> getBusinessLines();

	Set<OrganizationUnit> getChildren();

	void addChild(OrganizationUnit child);

	Set<Person> getContactPersons();

	Set<Phone> getPhones();

	Set<Email> getEmails();
}
