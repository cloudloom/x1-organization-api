package com.tracebucket.x1.organization.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.x1.organization.api.DefaultOrganizationStarter;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationUnitResource;
import com.tracebucket.x1.organization.api.test.fixture.DefaultOrganizationResourceFixture;
import com.tracebucket.x1.organization.api.test.fixture.DefaultOrganizationUnitResourceFixture;
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

/**
 * Created by sadath on 20-Apr-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultOrganizationStarter.class)
@WebIntegrationTest
public class OrganizationControllerTest {

    private static final Logger log = LoggerFactory.getLogger(OrganizationControllerTest.class);

    RestTemplate restTemplate = null;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    private DefaultOrganizationResource organization = null;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    private void createOrganization() throws Exception{
        organization = DefaultOrganizationResourceFixture.standardOrganization();
        organization = restTemplate.postForObject(basePath+"/organization", organization, DefaultOrganizationResource.class);
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
        organization = restTemplate.getForObject(basePath + "/organization/" + uid, DefaultOrganizationResource.class);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(uid, organization.getUid());
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

    @After
    public void tearDown() throws Exception{
        if(organization != null && organization.getUid() != null) {
            restTemplate.delete(basePath + "/organization/" + organization.getUid());
            organization = restTemplate.getForObject(basePath + "/organization/" + organization.getUid(), DefaultOrganizationResource.class);
            Assert.assertNull(organization.getUid());
        }
    }
}
