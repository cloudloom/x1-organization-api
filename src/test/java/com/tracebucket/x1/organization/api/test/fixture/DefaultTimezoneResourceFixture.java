package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.TimezoneType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultTimezoneResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultTimezoneResourceBuilder;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultTimezoneResourceFixture {
    public static DefaultTimezoneResource standardTimezone() {
        DefaultTimezoneResource timezone = DefaultTimezoneResourceBuilder.aDefaultTimezoneResourceBuilder()
                .withTimezoneType(TimezoneType.DEFAULT)
                .withAbbreviation("Greenwich")
                .withImage("image")
                .withName("GMT")
                .withUtcOffset(0)
                .build();
        return timezone;
    }
}