package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.test.builder.DefaultDepartmentBuilder;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultDepartmentFixture {
    public static DefaultDepartment standardDepartment(){
        DefaultDepartment department = DefaultDepartmentBuilder.aDepartment()
                .withName("Loan")
                .withDescription("Account desc")
                .build();
        return department;
    }
    public static DefaultDepartment standardDepartment2(){
        DefaultDepartment department = DefaultDepartmentBuilder.aDepartment()
                .withName("Credit")
                .withDescription("Account desc")
                .build();
        return department;
    }
    public static DefaultDepartment standardDepartment3(){
        DefaultDepartment department = DefaultDepartmentBuilder.aDepartment()
                .withName("Debit")
                .withDescription("Account desc")
                .build();
        return department;
    }
    public static DefaultDepartment standardDepartment4(){
        DefaultDepartment department = DefaultDepartmentBuilder.aDepartment()
                .withName("Foreign Exchange")
                .withDescription("Account desc")
                .build();
        return department;
    }
}
