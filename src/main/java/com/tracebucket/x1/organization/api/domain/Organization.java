package com.tracebucket.x1.organization.api.domain;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import org.dozer.Mapper;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by ffl on 09-04-2015.
 */
public interface Organization extends Serializable{

	void addBaseCurrency(DefaultCurrency baseCurrency);


	void addTimezone(DefaultTimezone timezone);


	void addOrganizationUnit(DefaultOrganizationUnit organizationUnit);

    void updateOrganizationUnit(DefaultOrganizationUnit organizationUnit, Mapper mapper);

	void addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit, DefaultOrganizationUnit parentOrganizationUnit);


	void addContactPerson(DefaultPerson contactPerson);


	void setDefaultContactPerson(DefaultPerson defaultContactPerson);


	void addContactNumber(DefaultPhone phone);


	void setDefaultContactNumber(DefaultPhone defaultContactNumber);


	void addEmail(DefaultEmail email);


	void setDefaultEmail(DefaultEmail defaultEmail);


	void setHeadOffice(DefaultAddress headOfficeAddress);


	void moveHeadOfficeTo(DefaultAddress newHeadOfficeAddress);

	String getCode();

	String getName();

	String getDescription();

	String getWebsite();

	String getImage();

	DefaultAddress getHeadOfficeAddress();

	Set<DefaultCurrency> getBaseCurrencies();

	Set<DefaultOrganizationUnit> getOrganizationUnits();

	Set<DefaultPhone> getContactNumbers();

	Set<DefaultEmail> getEmails();

	Set<DefaultTimezone> getTimezones();

	Set<DefaultPerson> getContactPersons();

	Set<DefaultPhone> getPhones();

	Set<DefaultAddress> getAddresses();

	Set<DefaultCurrency> getCurrencies();

    void setCode(String code);

    void setName(String name);

    void setDescription(String description);

    void setWebsite(String website);

    void setImage(String image);

    void setOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits);

    void setAddresses(Set<DefaultAddress> addresses);

    void setCurrencies(Set<DefaultCurrency> currencies);

    void setTimezones(Set<DefaultTimezone> timezones);

    void setContactPersons(Set<DefaultPerson> contactPersons);

    void setPhones(Set<DefaultPhone> phones);

    void setEmails(Set<DefaultEmail> emails);
}