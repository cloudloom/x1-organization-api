package com.tracebucket.x1.organization.api.service;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultPosition;
import com.tracebucket.x1.organization.api.domain.impl.jpa.PositionType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ffl on 09-04-2015.
 */
public interface DefaultOrganizationService {
	public DefaultOrganization save(DefaultOrganization organization);
	public DefaultOrganization findOne(String tenantId, AggregateId aggregateId);
	public boolean delete(String tenantId, AggregateId organizationAggregateId);
	public DefaultOrganization addBaseCurrency(String tenantId, DefaultCurrency baseCurrency, AggregateId organizationAggregateId);
	public DefaultOrganization addTimezone(String tenantId, DefaultTimezone timezone, AggregateId organizationAggregateId);
	public DefaultOrganization addOrganizationUnit(String tenantId, DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId);
    public DefaultOrganization updateOrganizationUnit(String tenantId, DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId);
    public DefaultOrganization addOrganizationUnitBelow(String tenantId, DefaultOrganizationUnit organizationUnit,
			EntityId parentOrganizationUnitEntityId, AggregateId organizationAggregateId);
	public DefaultOrganization addContactPerson(String tenantId, DefaultPerson person, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultContactPerson(String tenantId, DefaultPerson person, AggregateId organizationAggregateId);
	public DefaultOrganization addContactNumber(String tenantId, DefaultPhone phone, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultContactNumber(String tenantId, DefaultPhone phone, AggregateId organizationAggregateId);
	public DefaultOrganization addEmail(String tenantId, DefaultEmail email, AggregateId organizationAggregateId);
	public DefaultOrganization setDefaultEmail(String tenantId, DefaultEmail email, AggregateId organizationAggregateId);
	public DefaultOrganization setHeadOffice(String tenantId, DefaultAddress address, AggregateId organizationAggregateId);
	public DefaultOrganization moveHeadOfficeTo(String tenantId, DefaultAddress address, AggregateId organizationAggregateId);
	public DefaultAddress getHeadOfficeAddress(String tenantId, AggregateId organizationAggregateId);
	public Set<DefaultCurrency> getBaseCurrencies(String tenantId, AggregateId organizationAggregateId);
	public Set<DefaultOrganizationUnit> getOrganizationUnits(String tenantId, AggregateId organizationAggregateId);
	public Set<DefaultPhone> getContactNumbers(String tenantId, AggregateId organizationAggregateId);
	public Set<DefaultEmail> getEmails(String tenantId, AggregateId organizationAggregateId);
    public List<DefaultOrganization> findAll();
    public DefaultOrganization addPosition(String tenantId, AggregateId organizationAggregateId, Set<DefaultPosition> position);
    public DefaultOrganization addPositionToOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> positions);
    public DefaultOrganization updatePosition(String tenantId, AggregateId organizationAggregateId, DefaultPosition position);
    public DefaultOrganization updatePositionsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> positions);
    public Set<DefaultPosition> getPositions(String tenantId, AggregateId organizationAggregateId);
    public Set<DefaultPosition> getPositionsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId);
    public DefaultOrganization restructureOrganizationUnits(String tenantId, AggregateId organizationAggregateId, Set<DefaultOrganizationUnit> restructureOrganizationUnits);
    public DefaultOrganization restructureOrganizationUnits2(String tenantId, AggregateId organizationAggregateId, Set<DefaultOrganizationUnit> organizationUnits);
    public DefaultPosition getPosition(String tenantId, AggregateId organizationAggregateId, EntityId positionEntityId);
    public PositionType[] getPositionTypes(String tenantId);
    public Map<String, Set<DefaultPosition>> getOrganizationUnitPositions(String tenantId, AggregateId organizationAggregateId);
    public Set<DefaultOrganizationUnit> searchOrganizationUnits(String tenantId, AggregateId organizationAggregateId, String searchTerm);
}