package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.CurrencyType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultCurrency;
import com.tracebucket.x1.organization.api.test.builder.DefaultCurrencyBuilder;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultCurrencyFixture {
    public static DefaultCurrency standardBaseCurrency() {
        DefaultCurrency currency = DefaultCurrencyBuilder.aDefaultCurrencyBuilder()
                .withImage("Image")
                .withIso4217Code("iso4217code")
                .withSubUnit110("subunit110")
                .withName("INR")
                .withCurrencyType(CurrencyType.BASE)
                .build();
        return currency;
    }
}