package com.tracebucket.x1.organization.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.x1.organization.api.DefaultOrganizationStarter;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationUnitResource;
import com.tracebucket.x1.organization.api.service.DefaultOrganizationService;
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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by sadath on 10-Feb-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes  = {DefaultOrganizationStarter.class})
public class OrganizationControllerTest {

    private static final Logger log = LoggerFactory.getLogger(OrganizationControllerTest.class);

    private static MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DefaultOrganizationService organizationService;

    private DefaultOrganizationResource organization = null;

    @Before
    public void setUp(){
        mockMvc = webAppContextSetup(this.wac).build();
    }

    private void createOrganization() throws Exception{
        organization = DefaultOrganizationResourceFixture.standardOrganization();
        MvcResult mvcResult = null;
        log.info("Add Organization : "+ objectMapper.writeValueAsString(organization));
        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/organization")
                        .content(objectMapper.writeValueAsString(organization))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        organization = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization);
    }

    @Test
    public void testCreate() throws Exception {
        createOrganization();
        Assert.assertNotNull(organization.getUid());
    }

    @Test
    public void testAddOrganizationUnit() throws Exception{
        createOrganization();
        DefaultOrganizationUnitResource organizationUnit = DefaultOrganizationUnitResourceFixture.standardOrganizationUnitResource();
        MvcResult mvcResult = null;
        log.info("Add Organization Unit : "+ objectMapper.writeValueAsString(organizationUnit));
        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.put("/organization/"+organization.getUid()+"/organizationunit/")
                        .content(objectMapper.writeValueAsString(organizationUnit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        organization = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
    }


    @Test
    public void testFindOne() throws Exception {
        createOrganization();
        MvcResult mvcResult = null;
        log.info("Find Organization By UID : "+ organization.getUid());
        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/organization/"+organization.getUid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String uid = organization.getUid();
        organization = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DefaultOrganizationResource.class);
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getUid());
        Assert.assertEquals(uid, organization.getUid());
    }

    @After
    public void tearDown() throws Exception{
        if(organization != null && organization.getUid() != null) {
            MvcResult mvcResult = null;
            log.info("Delete Organization By UID : "+ organization.getUid());
            mvcResult = mockMvc
                    .perform(MockMvcRequestBuilders.delete("/organization/"+organization.getUid())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            Boolean status = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Boolean.class);
            Assert.assertTrue(status);
        }
    }

}