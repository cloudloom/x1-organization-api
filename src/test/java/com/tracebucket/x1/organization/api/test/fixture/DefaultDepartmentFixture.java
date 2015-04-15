package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.domain.Department;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.test.builder.DefaultDepartmentBuilder;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultDepartmentFixture {
    public static DefaultDepartment standardDepartment(){
        DefaultDepartment department = DefaultDepartmentBuilder.aDepartment()
                .withName("Account")
                .withDescription("Account desc")
                .build();
        return department;
    }
}
