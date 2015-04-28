package com.tracebucket.x1.organization.api.test.fixture;


import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultBusinessLine;
import com.tracebucket.x1.organization.api.test.builder.DefaultBusinessLineBuilder;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultBusinessLineFixture {
    public static DefaultBusinessLine standardBusinessLine(){
        DefaultBusinessLine businessLine = DefaultBusinessLineBuilder.aBusinessLine()
                .withName("Business Line1")
                .withDescription("Business line description")
                .build();
        return businessLine;
    }
}
