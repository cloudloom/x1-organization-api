package com.tracebucket.x1.organization.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.x1.organization.api.DefaultOrganizationStarter;
import com.tracebucket.x1.organization.api.rest.resource.*;
import com.tracebucket.x1.organization.api.test.fixture.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by sadath on 20-Apr-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultOrganizationStarter.class)
@WebIntegrationTest
public class OrganizationControllerTest {

    private static final Logger log = LoggerFactory.getLogger(OrganizationControllerTest.class);

    RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    private DefaultOrganizationResource organization = null;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    private void createOrganization() throws Exception{
        organization = DefaultOrganizationResourceFixture.standardOrganization();
        log.info("Create Organization : " + objectMapper.writeValueAsString(organization));
        organization = restTemplate.postForObject(basePath+"/organization", organization, DefaultOrganizationResource.class);
        log.info("Created Organization : " + objectMapper.writeValueAsString(organization));
        Assert.assertNotNull(organization);
    }

    @Test
    public void testCreate() throws Exception {
        createOrganization();
        Assert.assertNotNull(organization.getUid());
    }

    @Test
    public void testFindOne() throws Exception {
        createOrganization();
        String uid = organization.getUid();
        log.info("Find Organization with UID : " + organization.getUid());
        organization = restTemplate.getForObject(basePath + "/organization/" + uid, DefaultOrganizationResource.class);
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(uid, organization.getUid());
        log.info("Found : " + objectMapper.writeValueAsString(organization));
    }

    @Test
    public void testAddOrganizationUnit() throws Exception{
        createOrganization();
        DefaultOrganizationUnitResource organizationUnit = DefaultOrganizationUnitResourceFixture.standardOrganizationUnitResource();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/organizationunit", organizationUnit);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
    }

    @Test
    public void testAddBaseCurrency() throws Exception{
        createOrganization();
        DefaultCurrencyResource defaultCurrencyResource = DefaultCurrencyResourceFixture.standardBaseCurrency();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/basecurrency", defaultCurrencyResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getCurrencies().size());
    }

    @Test
    public void testAddTimezone() throws Exception{
        createOrganization();
        DefaultTimezoneResource defaultTimezoneResource = DefaultTimezoneResourceFixture.standardTimezone();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/timezone", defaultTimezoneResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getTimezones().size());
    }

    @Test
    public void testAddOrganizationUnitBelow() throws Exception{
        createOrganization();
        DefaultOrganizationUnitResource organizationUnit = DefaultOrganizationUnitResourceFixture.standardOrganizationUnitResource();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/organizationunit", organizationUnit);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
        DefaultOrganizationUnitResource childOrganizationUnitResource = DefaultOrganizationUnitResourceFixture.standardOrganizationUnitResource();
        DefaultOrganizationUnitResource parentOrganizationUnitResource = null;
        for(DefaultOrganizationUnitResource organizationUnitResource : organization.getOrganizationUnits()) {
            parentOrganizationUnitResource = organizationUnitResource;
        }
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/organizationunit/"+parentOrganizationUnitResource.getUid()+"/below", childOrganizationUnitResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getOrganizationUnits());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
    }

    @Test
    public void testAddContactPerson() throws Exception{
        createOrganization();
        DefaultPersonResource defaultPersonResource = DefaultPersonResourceFixture.standardPerson();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/contactperson", defaultPersonResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getContactPersons().size());
    }

    @Test
    public void testSetDefaultContactPerson() throws Exception{
        createOrganization();
        DefaultPersonResource defaultPersonResource = DefaultPersonResourceFixture.standardPerson();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/contactperson/default", defaultPersonResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getContactPersons().size());
        Assert.assertTrue(new ArrayList<>(organization.getContactPersons()).get(0).isDefaultContactPerson());
    }

    @Test
    public void testAddContactNumber() throws Exception{
        createOrganization();
        DefaultPhoneResource defaultPhoneResource = DefaultPhoneResourceFixture.standardPhone();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/contactnumber", defaultPhoneResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getPhones().size());
    }

    @Test
    public void testSetDefaultContactNumber() throws Exception{
        createOrganization();
        DefaultPhoneResource defaultPhoneResource = DefaultPhoneResourceFixture.standardPhone();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/contactnumber/default", defaultPhoneResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertTrue(new ArrayList<>(organization.getPhones()).get(0).isDefaultContactNumber());
    }
    @Test
    public void testAddEmail() throws Exception{
        createOrganization();
        DefaultEmailResource defaultEmailResource = DefaultEmailResourceFixture.standardEmail();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/email", defaultEmailResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getEmails().size());
    }

    @Test
    public void testSetDefaultEmail() throws Exception{
        createOrganization();
        DefaultEmailResource defaultEmailResource = DefaultEmailResourceFixture.standardEmail();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/email/default", defaultEmailResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertTrue(new ArrayList<>(organization.getEmails()).get(0).isDefaultEmail());
    }

    @Test
    public void testSetHeadOffice() throws Exception{
        createOrganization();
        DefaultAddressResource defaultAddressResource = DefaultAddressResourceFixture.standardAddress();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/headoffice", defaultAddressResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getAddresses().size());
    }

    @Test
    public void testMoveHeadOfficeTo() throws Exception{
        createOrganization();
        DefaultAddressResource defaultAddressResource = DefaultAddressResourceFixture.standardAddress();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/headoffice/to", defaultAddressResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getAddresses().size());

    }

    @Test
    public void testGetHeadOfficeAddress() throws Exception{
        createOrganization();
        DefaultAddressResource defaultAddressResource = DefaultAddressResourceFixture.standardAddress();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/headoffice", defaultAddressResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getAddresses().size());

    }

    @Test
    public void testGetBaseCurrencies() throws Exception{
        createOrganization();
        DefaultCurrencyResource defaultCurrencyResource = DefaultCurrencyResourceFixture.standardBaseCurrency();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/basecurrency", defaultCurrencyResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getCurrencies().size());
    }

    @Test
    public void testGetOrganizationUnits() throws Exception{
        createOrganization();
        DefaultOrganizationUnitResource organizationResource = DefaultOrganizationUnitResourceFixture.standardOrganizationUnitResource();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/organizationunit", organizationResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
    }

    @Test
    public void testGetContactNumbers() throws Exception{
        createOrganization();
        DefaultPhoneResource defaultPhoneResource = DefaultPhoneResourceFixture.standardPhone();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/contactnumber", defaultPhoneResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getPhones().size());
    }

    @Test
    public void testGetEmails() throws Exception{
        createOrganization();
        DefaultEmailResource defaultEmailResource = DefaultEmailResourceFixture.standardEmail();
        restTemplate.put(basePath+"/organization/"+organization.getUid()+"/email", defaultEmailResource);
        organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getEmails().size());
    }

    @After
    public void tearDown() throws Exception{
        if(organization != null && organization.getUid() != null) {
            restTemplate.delete(basePath + "/organization/" + organization.getUid());
            organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
            Assert.assertNotNull(organization);
            Assert.assertNull(organization.getUid());
        }
    }
}
