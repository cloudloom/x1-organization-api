package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.organization.api.rest.resource.DefaultBusinessLineResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultBusinessLineResourceBuilder;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultBusinessLineResourceFixture {
    public static DefaultBusinessLineResource standardBusinessLine(){
        DefaultBusinessLineResource businessLine = DefaultBusinessLineResourceBuilder.aBusinessLineResource()
                .withName("Business Line1")
                .withDescription("Business line description")
                .build();
        return businessLine;
    }
}
