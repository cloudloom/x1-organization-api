package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.PhoneType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultPhoneResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultPhoneResourceBuilder;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultPhoneResourceFixture {
    public static DefaultPhoneResource standardPhone() {
        DefaultPhoneResource email = DefaultPhoneResourceBuilder.aPhoneBuilder()
                .withExtension("9786")
                .withNumber("80345213")
                .withPhoneType(PhoneType.HOME)
                .build();
        return email;
    }
}