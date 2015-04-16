package com.tracebucket.x1.organization.api.service;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.x1.dictionary.api.domain.*;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
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
	public DefaultOrganization addTimezone(DefaultTimezone timezone, AggregateId organizationAggregateId);
	public DefaultOrganization addOrganizationUnit(DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId);
	public DefaultOrganization addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit,
			DefaultOrganizationUnit parentOrganizationUnit, AggregateId organizationAggregateId);
	public DefaultOrganization addContactPerson(DefaultPerson person, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultContactPerson(DefaultPerson person, AggregateId organizationAggregateId);
	public DefaultOrganization addContactNumber(DefaultPhone phone, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultContactNumber(DefaultPhone phone, AggregateId organizationAggregateId);
	public DefaultOrganization addEmail(DefaultEmail email, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultEmail(DefaultEmail email, AggregateId organizationAggregateId);
	public DefaultOrganization setHeadOffice(DefaultAddress address, AggregateId organizationAggregateId);
	public DefaultOrganization moveHeadOfficeTo(DefaultAddress address, AggregateId organizationAggregateId);
	public DefaultAddress getHeadOfficeAddress(AggregateId organizationAggregateId);
	//public Set<Currency> getBaseCurrencies(AggregateId organizationAggregateId);
	public Set<DefaultOrganizationUnit> getOrganizationUnits(AggregateId organizationAggregateId);
	public Set<DefaultPhone> getContactNumbers(AggregateId organizationAggregateId);
	public Set<DefaultEmail> getEmails(AggregateId organizationAggregateId);
}
