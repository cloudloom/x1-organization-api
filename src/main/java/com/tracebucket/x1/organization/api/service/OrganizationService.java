package com.tracebucket.x1.organization.api.service;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.x1.dictionary.api.domain.*;
import com.tracebucket.x1.organization.api.domain.Organization;
import com.tracebucket.x1.organization.api.domain.OrganizationUnit;

import java.util.Set;

/**
 * Created by ffl on 09-04-2015.
 */
public interface OrganizationService {
	public Organization save(Organization organization);
	public Organization findOne(AggregateId aggregateId);
	public boolean delete(AggregateId organizationAggregateId);
	public Organization addBaseCurrency(Currency baseCurrency, AggregateId organizationAggregateId);
	public Organization addTimezone(Timezone timezone, AggregateId organizationAggregateId);
	public Organization addOrganizationUnit(OrganizationUnit organizationUnit, AggregateId organizationAggregateId);
	public Organization addOrganizationUnitBelow(OrganizationUnit organizationUnit,
			OrganizationUnit parentOrganizationUnit, AggregateId organizationAggregateId);
	public Organization addContactPerson(Person person, AggregateId organizationAggregateId);
	public Organization setDefaultContactPerson(Person person, AggregateId organizationAggregateId);
	public Organization addContactNumber(Phone phone, AggregateId organizationAggregateId);
	public Organization setDefaultContactNumber(Phone phone, AggregateId organizationAggregateId);
	public Organization addEmail(Email email, AggregateId organizationAggregateId);
	public Organization setDefaultEmail(Email email, AggregateId organizationAggregateId);
	public Organization setHeadOffice(Address address, AggregateId organizationAggregateId);
	public Organization moveHeadOfficeTo(Address address, AggregateId organizationAggregateId);
	public Address getHeadOfficeAddress(AggregateId organizationAggregateId);
	public Set<Currency> getBaseCurrencies(AggregateId organizationAggregateId);
	public Set<OrganizationUnit> getOrganizationUnits(AggregateId organizationAggregateId);
	public Set<Phone> getContactNumbers(AggregateId organizationAggregateId);
	public Set<Email> getEmails(AggregateId organizationAggregateId);
}
