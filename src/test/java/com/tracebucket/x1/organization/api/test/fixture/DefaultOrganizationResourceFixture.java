package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultOrganizationResourceBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationResourceFixture {
    public static DefaultOrganizationResource standardOrganization() {
        DefaultOrganizationResource organization = DefaultOrganizationResourceBuilder.anOrganizationResourceBuilder()
                .build("Organization " + new Date().getTime(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), "image");
        return organization;
    }
}