package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.rest.resource.DefaultDepartmentResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultDepartmentResourceBuilder;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultDepartmentResourceFixture {
    public static DefaultDepartmentResource standardDepartment(){
        DefaultDepartmentResource department = DefaultDepartmentResourceBuilder.aDepartmentResource()
                .withName("Account")
                .withDescription("Account desc")
                .build();
        return department;
    }
}