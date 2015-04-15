package com.tracebucket.x1.organization.api.service;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.x1.dictionary.api.domain.*;
import com.tracebucket.x1.organization.api.domain.OrganizationUnit;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;

import java.util.Set;

/**
 * Created by ffl on 09-04-2015.
 */
public interface DefaultOrganizationService {
	public DefaultOrganization save(DefaultOrganization organization);
	public DefaultOrganization findOne(AggregateId aggregateId);
	public boolean delete(AggregateId organizationAggregateId);
	//public DefaultOrganization addBaseCurrency(Currency baseCurrency, AggregateId organizationAggregateId);
	public DefaultOrganization addTimezone(Timezone timezone, AggregateId organizationAggregateId);
	public DefaultOrganization addOrganizationUnit(DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId);
	public DefaultOrganization addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit,
			DefaultOrganizationUnit parentOrganizationUnit, AggregateId organizationAggregateId);
	public DefaultOrganization addContactPerson(Person person, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultContactPerson(Person person, AggregateId organizationAggregateId);
	public DefaultOrganization addContactNumber(Phone phone, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultContactNumber(Phone phone, AggregateId organizationAggregateId);
	public DefaultOrganization addEmail(Email email, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultEmail(Email email, AggregateId organizationAggregateId);
	public DefaultOrganization setHeadOffice(Address address, AggregateId organizationAggregateId);
	public DefaultOrganization moveHeadOfficeTo(Address address, AggregateId organizationAggregateId);
	public Address getHeadOfficeAddress(AggregateId organizationAggregateId);
	//public Set<Currency> getBaseCurrencies(AggregateId organizationAggregateId);
	public Set<DefaultOrganizationUnit> getOrganizationUnits(AggregateId organizationAggregateId);
	public Set<Phone> getContactNumbers(AggregateId organizationAggregateId);
	public Set<Email> getEmails(AggregateId organizationAggregateId);
}
