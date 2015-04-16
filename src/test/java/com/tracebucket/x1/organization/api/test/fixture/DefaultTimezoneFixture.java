package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.TimezoneType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultTimezone;
import com.tracebucket.x1.organization.api.test.builder.DefaultTimezoneBuilder;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultTimezoneFixture {
    public static DefaultTimezone standardTimezone() {
        DefaultTimezone timezone = DefaultTimezoneBuilder.aDefaultTimezoneBuilder()
                .withTimezoneType(TimezoneType.DEFAULT)
                .withAbbreviation("Greenwich")
                .withImage("image")
                .withName("GMT")
                .withUtcOffset(0)
                .build();
        return timezone;
    }
}