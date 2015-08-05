package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultPosition;
import com.tracebucket.x1.organization.api.test.builder.DefaultPositionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 24-07-2015.
 */
public class DefaultPositionFixture {

    public static DefaultPosition standardPosition() {

        DefaultPosition defaultPosition = new DefaultPosition();
        defaultPosition.setName("Manager");
        defaultPosition.setCode("MMP-150");

        Set<DefaultPosition> children = new HashSet<>();

        DefaultPosition position1 = new DefaultPosition();
        position1.setName("CEO");
        position1.setCode("MMP-101");
        children.add(position1);

        DefaultPosition position = DefaultPositionBuilder.aPositionBuilder()
                .withName("Cust_Serv_Agent")
                .withCode("MMP-100")
                .withParent(defaultPosition)
                .withChildren(children)
                .build();
        return position;
    }

    public static DefaultPosition standardPosition2() {

        DefaultPosition defaultPosition = new DefaultPosition();
        defaultPosition.setName("Manager_2");
        defaultPosition.setCode("MMP-152");

        Set<DefaultPosition> children = new HashSet<>();

        DefaultPosition position1 = new DefaultPosition();
        position1.setName("CEO_2");
        position1.setCode("MMP-102");
        children.add(position1);

        DefaultPosition position = DefaultPositionBuilder.aPositionBuilder()
                .withName("Cust_Serv_Agent_2")
                .withCode("MMP-102")
                .withParent(defaultPosition)
                .withChildren(children)
                .build();
        return position;
    }
}
