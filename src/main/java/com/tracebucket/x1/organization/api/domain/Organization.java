package com.tracebucket.x1.organization.api.domain;

import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultPosition;
import org.dozer.Mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by ffl on 09-04-2015.
 */
public interface Organization extends Serializable{

	void addBaseCurrency(DefaultCurrency baseCurrency);


	void addTimezone(DefaultTimezone timezone);


	void addOrganizationUnit(DefaultOrganizationUnit organizationUnit);

    void deleteOrganizationUnit(EntityId organizationUnitEntityId);

    void updateOrganizationUnit(DefaultOrganizationUnit organizationUnit, Mapper mapper);

	void addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit, DefaultOrganizationUnit parentOrganizationUnit);


	void addContactPerson(DefaultPerson contactPerson);


	void setDefaultContactPerson(DefaultPerson defaultContactPerson);


	void addContactNumber(DefaultPhone phone);


	void setDefaultContactNumber(DefaultPhone defaultContactNumber);

    void markOrganizationUnitAsRoot(EntityId organizationUnit);

	void addEmail(DefaultEmail email);


	void setDefaultEmail(DefaultEmail defaultEmail);


	void setHeadOffice(DefaultAddress headOfficeAddress);


	void moveHeadOfficeTo(DefaultAddress newHeadOfficeAddress);

    void addPosition(DefaultPosition position);

    void addPositionBelow(DefaultPosition position, DefaultPosition parentPosition);

    void updatePosition(DefaultPosition position, Mapper mapper);

    void addPositionToOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultPosition> position);

    void updatePositionsOfOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultPosition> position);

    void removePositionsOfOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultPosition> position);

    Set<DefaultPosition>  getPositionsOfOrganizationUnit(EntityId organizationUnitEntityId);

    void addDepartmentToOrganization(Set<DefaultDepartment> departments);

    void updateDepartmentOfOrganization(Set<DefaultDepartment> departments, Mapper mapper);

    Set<DefaultDepartment> getDepartmentsOfOrganization();

    void addDepartmentToOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultDepartment> departments);

    void updateDepartmentOfOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultDepartment> departments);

    Set<DefaultDepartment> getDepartmentsOfOrganizationUnit(EntityId organizationUnitEntityId);


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

    Set<DefaultPosition> getPositions();

    DefaultPosition getPosition(EntityId positionEntityId);

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

    void updateOrganizationUnitPositions(DefaultOrganizationUnit organizationUnit);

    void restructureOrganizationUnits(String rootOrganizationUnit, String parentOrganizationUnitUid, String childOrganizationUnitUid);

    void restructurePositionHierarchy(String rootOrganizationUnit, String parentPositionUid, String childPositionUid);

    void restructureOrganizationUnitsPositions(ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> positionsInput);

    void restructureOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits);

    void deleteOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits);

}
