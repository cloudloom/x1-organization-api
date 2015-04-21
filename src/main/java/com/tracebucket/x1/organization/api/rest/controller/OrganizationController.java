package com.tracebucket.x1.organization.api.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.rest.resource.*;
import com.tracebucket.x1.organization.api.service.DefaultOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sadath on 10-Feb-15.
 */
@RestController
public class OrganizationController implements Organization{

    private static Logger log = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private DefaultOrganizationService organizationService;

    @Autowired
    private AssemblerResolver assemblerResolver;

    @RequestMapping(value = "/organization", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> createOrganization(@RequestBody DefaultOrganizationResource organizationResource) {
        DefaultOrganization organization = assemblerResolver.resolveEntityAssembler(DefaultOrganization.class, DefaultOrganizationResource.class).toEntity(organizationResource, DefaultOrganization.class);
        organization = organizationService.save(organization);
        organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}/organizationunit", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnit(@PathVariable("organizationUid") String organizationUid, @RequestBody DefaultOrganizationUnitResource organizationUnitResource) {
        DefaultOrganizationUnit organizationUnit = assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class).toEntity(organizationUnitResource, DefaultOrganizationUnit.class);
        DefaultOrganization organization = organizationService.addOrganizationUnit(organizationUnit, new AggregateId(organizationUid));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> getOrganization(@PathVariable("organizationUid") String organizationUid) {
        DefaultOrganization organization = organizationService.findOne(new AggregateId(organizationUid));
        DefaultOrganizationResource organizationResource = null;
        if(organization != null) {
            organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
            return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
        }
        return new ResponseEntity<DefaultOrganizationResource>(new DefaultOrganizationResource(), HttpStatus.OK);
    }


    @RequestMapping(value = "/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationResource>> getOrganizations() {
        return new ResponseEntity<Set<DefaultOrganizationResource>>(assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResources(organizationService.findAll(), DefaultOrganizationResource.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteOrganization(@PathVariable("organizationUid") String organizationUid) {
        return new ResponseEntity<Boolean>(organizationService.delete(new AggregateId(organizationUid)), HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/basecurrency", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addBaseCurrency(@RequestBody DefaultCurrencyResource baseCurrency, @PathVariable("organizationUid") String aggregateId) {
        DefaultCurrency defaultCurrency = assemblerResolver.resolveEntityAssembler(DefaultCurrency.class, DefaultCurrencyResource.class).toEntity(baseCurrency, DefaultCurrency.class);
        DefaultOrganization organization = organizationService.addBaseCurrency(defaultCurrency, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/timezone", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addTimezone(@RequestBody DefaultTimezoneResource timezone, @PathVariable("organizationUid") String aggregateId) {
        DefaultTimezone defaultTimezone = assemblerResolver.resolveEntityAssembler(DefaultTimezone.class, DefaultTimezoneResource.class).toEntity(timezone, DefaultTimezone.class);
        DefaultOrganization organization = organizationService.addTimezone(defaultTimezone, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/organizationunit/{parentOrganizationUnitUid}/below", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnitBelow(@RequestBody DefaultOrganizationUnitResource organizationUnit, @PathVariable("parentOrganizationUnitUid") String parentOrganizationUnitUid, @PathVariable("organizationUid") String aggregateId) {
        DefaultOrganizationUnit organizationUnit1 = assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class).toEntity(organizationUnit, DefaultOrganizationUnit.class);
        DefaultOrganization organization = organizationService.addOrganizationUnitBelow(organizationUnit1, new EntityId(parentOrganizationUnitUid), new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }

     @RequestMapping(value = "/organization/{organizationUid}/contactperson", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addContactPerson(@RequestBody DefaultPersonResource person, @PathVariable("organizationUid") String aggregateId) {
        DefaultPerson defaultPerson = assemblerResolver.resolveEntityAssembler(DefaultPerson.class, DefaultPersonResource.class).toEntity(person, DefaultPerson.class);
        DefaultOrganization organization = organizationService.addContactPerson(defaultPerson, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/contactperson/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactPerson(@RequestBody DefaultPersonResource person, @PathVariable("organizationUid") String aggregateId) {
        DefaultPerson defaultPerson = assemblerResolver.resolveEntityAssembler(DefaultPerson.class, DefaultPersonResource.class).toEntity(person, DefaultPerson.class);
        DefaultOrganization organization = organizationService.setDefaultContactPerson(defaultPerson, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}/contactnumber", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addContactNumber(@RequestBody DefaultPhoneResource phone, @PathVariable("organizationUid") String aggregateId) {
        DefaultPhone defaultPhone = assemblerResolver.resolveEntityAssembler(DefaultPhone.class, DefaultPhoneResource.class).toEntity(phone, DefaultPhone.class);
        DefaultOrganization organization = organizationService.addContactNumber(defaultPhone, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}/contactnumber/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactNumber(@RequestBody DefaultPhoneResource phone, @PathVariable("organizationUid") String aggregateId) {
        DefaultPhone defaultPhone = assemblerResolver.resolveEntityAssembler(DefaultPhone.class, DefaultPhoneResource.class).toEntity(phone, DefaultPhone.class);
        DefaultOrganization organization = organizationService.setDefaultContactNumber(defaultPhone, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/email", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addEmail(@RequestBody DefaultEmailResource email, @PathVariable("organizationUid") String aggregateId) {
        DefaultEmail defaultEmail = assemblerResolver.resolveEntityAssembler(DefaultEmail.class, DefaultEmailResource.class).toEntity(email, DefaultEmail.class);
        DefaultOrganization organization = organizationService.addEmail(defaultEmail, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}/email/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultEmail(@RequestBody DefaultEmailResource email, @PathVariable("organizationUid") String aggregateId) {
        DefaultEmail defaultEmail = assemblerResolver.resolveEntityAssembler(DefaultEmail.class, DefaultEmailResource.class).toEntity(email, DefaultEmail.class);
        DefaultOrganization organization = organizationService.setDefaultEmail(defaultEmail, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}/headoffice", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setHeadOffice(@RequestBody DefaultAddressResource address, @PathVariable("organizationUid") String aggregateId) {
        DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
        DefaultOrganization organization = organizationService.setHeadOffice(defaultAddress, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/headoffice/to", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> moveHeadOfficeTo(@RequestBody DefaultAddressResource address, @PathVariable("organizationUid") String aggregateId) {
        DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
        DefaultOrganization organization = organizationService.moveHeadOfficeTo(defaultAddress, new AggregateId(aggregateId));
        DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
        return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/headoffice/address", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultAddressResource> getHeadOfficeAddress(@PathVariable("organizationUid") String aggregateId) {
        DefaultAddress address = organizationService.getHeadOfficeAddress(new AggregateId(aggregateId));
        DefaultAddressResource addressResource = assemblerResolver.resolveResourceAssembler(DefaultAddressResource.class, DefaultAddress.class).toResource(address, DefaultAddressResource.class);
        return new ResponseEntity<DefaultAddressResource>(addressResource, HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/currencies/base", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultCurrencyResource>> getBaseCurrencies(@PathVariable("organizationUid") String aggregateId) {
        Set<DefaultCurrency> currencies = organizationService.getBaseCurrencies(new AggregateId(aggregateId));
        Set<DefaultCurrencyResource> currencyResources = assemblerResolver.resolveResourceAssembler(DefaultCurrencyResource.class, DefaultCurrency.class).toResources(currencies, DefaultCurrencyResource.class);
        return new ResponseEntity<Set<DefaultCurrencyResource>>(currencyResources, HttpStatus.OK);
    }


    @RequestMapping(value = "/organization/{organizationUid}/organizationunits", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> getOrganizationUnits(@PathVariable("organizationUid") String aggregateId) {
        Set<DefaultOrganizationUnit> organizationUnits = organizationService.getOrganizationUnits(new AggregateId(aggregateId));
        Set<DefaultOrganizationUnitResource> organizationUnitResources = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResources(organizationUnits, DefaultOrganizationUnitResource.class);
        return new ResponseEntity<Set<DefaultOrganizationUnitResource>>(organizationUnitResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}/contactnumbers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPhoneResource>> getContactNumbers(@PathVariable("organizationUid") String aggregateId) {
        Set<DefaultPhone> phones = organizationService.getContactNumbers(new AggregateId(aggregateId));
        Set<DefaultPhoneResource> phoneResources = assemblerResolver.resolveResourceAssembler(DefaultPhoneResource.class, DefaultPhone.class).toResources(phones, DefaultPhoneResource.class);
        return new ResponseEntity<Set<DefaultPhoneResource>>(phoneResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/organization/{organizationUid}/emails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultEmailResource>> getEmails(@PathVariable("organizationUid") String aggregateId) {
        Set<DefaultEmail> emails = organizationService.getEmails(new AggregateId(aggregateId));
        Set<DefaultEmailResource> emailResources = assemblerResolver.resolveResourceAssembler(DefaultEmailResource.class, DefaultEmail.class).toResources(emails, DefaultEmailResource.class);
        return new ResponseEntity<Set<DefaultEmailResource>>(emailResources, HttpStatus.OK);
    }

}
