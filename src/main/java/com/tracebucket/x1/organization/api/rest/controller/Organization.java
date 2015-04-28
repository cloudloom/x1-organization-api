package com.tracebucket.x1.organization.api.rest.controller;

import com.tracebucket.x1.organization.api.rest.resource.*;
import org.springframework.http.ResponseEntity;

import java.util.Set;

/**
 * Created by sadath on 20-Apr-15.
 */
public interface Organization {
    public ResponseEntity<DefaultOrganizationResource> createOrganization(DefaultOrganizationResource organization);
    public ResponseEntity<DefaultOrganizationResource> getOrganization(String aggregateId);
    public ResponseEntity<Boolean> deleteOrganization(String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addBaseCurrency(DefaultCurrencyResource baseCurrency, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addTimezone(DefaultTimezoneResource timezone, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnit(String aggregateId, DefaultOrganizationUnitResource organizationUnit);
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnitBelow(DefaultOrganizationUnitResource organizationUnit,
                                                        String parentOrganizationUnitEntityId, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addContactPerson(DefaultPersonResource person, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactPerson(DefaultPersonResource person, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addContactNumber(DefaultPhoneResource phone, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactNumber(DefaultPhoneResource phone, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> addEmail(DefaultEmailResource email, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> setDefaultEmail(DefaultEmailResource email, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> setHeadOffice(DefaultAddressResource address, String aggregateId);
    public ResponseEntity<DefaultOrganizationResource> moveHeadOfficeTo(DefaultAddressResource address, String aggregateId);
    public ResponseEntity<DefaultAddressResource> getHeadOfficeAddress(String aggregateId);
    public ResponseEntity<Set<DefaultCurrencyResource>> getBaseCurrencies(String aggregateId);
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> getOrganizationUnits(String aggregateId);
    public ResponseEntity<Set<DefaultPhoneResource>> getContactNumbers(String aggregateId);
    public ResponseEntity<Set<DefaultEmailResource>> getEmails(String aggregateId);
    public ResponseEntity<Set<DefaultOrganizationResource>> getOrganizations();
}