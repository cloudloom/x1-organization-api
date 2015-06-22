package com.tracebucket.x1.organization.api.rest.controller;

import com.tracebucket.x1.organization.api.domain.impl.jpa.PositionType;
import com.tracebucket.x1.organization.api.rest.resource.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by sadath on 20-Apr-15.
 */
public interface Organization {
    public ResponseEntity<DefaultOrganizationResource> createOrganization(DefaultOrganizationResource organization);
    public ResponseEntity<DefaultOrganizationResource> getOrganization(HttpServletRequest request, String aggregateId);
    public ResponseEntity<Boolean> deleteOrganization(HttpServletRequest request, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addBaseCurrency(HttpServletRequest request, DefaultCurrencyResource baseCurrency, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addTimezone(HttpServletRequest request, DefaultTimezoneResource timezone, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnit(HttpServletRequest request, String aggregateId, DefaultOrganizationUnitResource organizationUnit);
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnitBelow(HttpServletRequest request, DefaultOrganizationUnitResource organizationUnit,
                                                        String parentOrganizationUnitEntityId, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addContactPerson(HttpServletRequest request, DefaultPersonResource person, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactPerson(HttpServletRequest request, DefaultPersonResource person, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addContactNumber(HttpServletRequest request, DefaultPhoneResource phone, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactNumber(HttpServletRequest request, DefaultPhoneResource phone, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addEmail(HttpServletRequest request, DefaultEmailResource email, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> setDefaultEmail(HttpServletRequest request, DefaultEmailResource email, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> setHeadOffice(HttpServletRequest request, DefaultAddressResource address, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> moveHeadOfficeTo(HttpServletRequest request, DefaultAddressResource address, String aggregateId);
    public ResponseEntity<DefaultAddressResource> getHeadOfficeAddress(HttpServletRequest request, String aggregateId);
    public ResponseEntity<Set<DefaultCurrencyResource>> getBaseCurrencies(HttpServletRequest request, String aggregateId);
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> getOrganizationUnits(HttpServletRequest request, String aggregateId);
    public ResponseEntity<Set<DefaultPhoneResource>> getContactNumbers(HttpServletRequest request, String aggregateId);
    public ResponseEntity<Set<DefaultEmailResource>> getEmails(HttpServletRequest request, String aggregateId);
    public ResponseEntity<Set<DefaultOrganizationResource>> getOrganizations();
    public ResponseEntity<Set<DefaultPositionResource>> getPositions(HttpServletRequest request, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addPosition(HttpServletRequest request, List<DefaultPositionResource> position, String aggregateId);
    public ResponseEntity<DefaultPositionResource> getPosition(HttpServletRequest request, String aggregateId, String entityId);
    public ResponseEntity<DefaultOrganizationResource> updatePosition(HttpServletRequest request, DefaultPositionResource position, String aggregateId, String entityId);
    public ResponseEntity<DefaultOrganizationResource> restructureOrganizationUnits(HttpServletRequest request, DefaultOrganizationResource organizationResource);
    public ResponseEntity<PositionType[]> getPositionTypes(HttpServletRequest request);
}