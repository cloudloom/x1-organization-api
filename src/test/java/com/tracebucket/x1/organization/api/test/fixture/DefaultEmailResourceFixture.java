package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.organization.api.rest.resource.DefaultEmailResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultEmailResourceBuilder;

import java.util.UUID;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultEmailResourceFixture {
    public static DefaultEmailResource standardEmail() {
        DefaultEmailResource email = DefaultEmailResourceBuilder.anEmailResourceBuilder()
                .withEmail(UUID.randomUUID().toString())
                .withEmailType(EmailType.BUSINESS)
                .build();
        return email;
    }
}