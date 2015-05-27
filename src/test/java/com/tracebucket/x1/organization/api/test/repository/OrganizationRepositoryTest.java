package com.tracebucket.x1.organization.api.test.repository;

import com.tracebucket.x1.organization.api.DefaultOrganizationStarter;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.repository.jpa.DefaultOrganizationRepository;
import com.tracebucket.x1.organization.api.test.fixture.DefaultOrganizationFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by sadath on 13-Jan-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes  = {DefaultOrganizationStarter.class})
public class OrganizationRepositoryTest {

    @Autowired
    private DefaultOrganizationRepository organizationRepository;

    private DefaultOrganization organization = null;

    private void createOrganization() throws Exception{
        organization = DefaultOrganizationFixture.standardOrganization();
        organization = organizationRepository.save(organization);
    }

    @Test
    @Rollback(value = false)
    public void testSave() throws Exception{
        createOrganization();
        Assert.assertNotNull(organization.getAggregateId());
    }

    @Test
    @Rollback(value = false)
    public void testFindById() throws Exception {
        createOrganization();
        organization = organizationRepository.findOne(organization.getAggregateId());
        Assert.assertNotNull(organization);
    }

    @After
    public void tearDown(){
        if(organization != null && organization.getAggregateId() != null) {
            organizationRepository.delete(organization.getAggregateId());
            organization = organizationRepository.findOne(organization.getAggregateId());
            Assert.assertNull(organization);
        }
    }
}