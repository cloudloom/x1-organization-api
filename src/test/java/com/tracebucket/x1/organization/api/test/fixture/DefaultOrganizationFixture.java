package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.test.builder.DefaultOrganizationBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultOrganizationFixture {
    public static DefaultOrganization standardOrganization() {
        DefaultOrganization organization = DefaultOrganizationBuilder.anOrganizationBuilder()
                .build("Organization " + new Date().getTime(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), "image");
        return organization;
    }
}
