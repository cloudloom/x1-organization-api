package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultPosition;
import com.tracebucket.x1.organization.api.rest.resource.DefaultPositionResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultPositionBuilder;
import com.tracebucket.x1.organization.api.test.builder.DefaultPositionResourceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 24-07-2015.
 */
public class DefaultPositionResourceFixture {

    public static DefaultPositionResource standardPositionResource() {

        DefaultPositionResource defaultPosition = new DefaultPositionResource();
        defaultPosition.setName("Manager");
        defaultPosition.setCode("MMP-150");

        Set<DefaultPositionResource> children = new HashSet<>();

        DefaultPositionResource position1 = new DefaultPositionResource();
        position1.setName("CEO");
        position1.setCode("MMP-101");
        children.add(position1);

        DefaultPositionResource position = DefaultPositionResourceBuilder.aPositionBuilder()
                .withName("Cust_Serv_Agent")
                .withCode("MMP-100")
                .withParent(defaultPosition)
                .withChildren(children)
                .build();
        return position;
    }
}
