package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.CurrencyType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultCurrencyResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultCurrencyResourceBuilder;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultCurrencyResourceFixture {
    public static DefaultCurrencyResource standardBaseCurrency() {
        DefaultCurrencyResource currency = DefaultCurrencyResourceBuilder.aDefaultCurrencyResourceBuilder()
                .withImage("Image")
                .withIso4217Code("iso4217code")
                .withSubUnit110("subunit110")
                .withName("INR")
                .withCurrencyType(CurrencyType.BASE)
                .build();
        return currency;
    }
}