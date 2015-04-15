package com.tracebucket.x1.organization.api.domain;

import com.tracebucket.x1.dictionary.api.domain.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by ffl on 09-04-2015.
 */
public interface Organization extends Serializable{

	public enum CurrencyType{
		Base,
		Optional
	}

	//void addBaseCurrency(Currency baseCurrency);


	void addTimezone(Timezone timezone);


	void addOrganizationUnit(DefaultOrganizationUnit organizationUnit);


	void addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit, DefaultOrganizationUnit parentOrganizationUnit);


	void addContactPerson(Person contactPerson);


	void setDefaultContactPerson(Person defaultContactPerson);


	void addContactNumber(Phone phone);


	void setDefaultContactNumber(Phone defaultContactNumber);


	void addEmail(Email email);


	void setDefaultEmail(Email defaultEmail);


	void setHeadOffice(Address headOfficeAddress);


	void moveHeadOfficeTo(Address newHeadOfficeAddress);

	String getCode();

	String getName();

	String getDescription();

	String getWebsite();

	String getImage();

	Address getHeadOfficeAddress();

	//Set<Currency> getBaseCurrencies();

	Set<DefaultOrganizationUnit> getOrganizationUnits();

	Set<Phone> getContactNumbers();

	Set<Email> getEmails();

	Set<Timezone> getTimezones();

	Set<Person> getContactPersons();

	Set<Phone> getPhones();

	Set<Address> getAddresses();

	//Map<Currency, CurrencyType> getCurrencies();
}
