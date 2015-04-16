package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.CurrencyType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultCurrency;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultCurrencyBuilder {
    private String name;
    private String iso4217Code;
    private String subUnit110;
    private String image;
    private CurrencyType currencyType;

    private DefaultCurrencyBuilder(){

    }

    public static DefaultCurrencyBuilder aDefaultCurrencyBuilder(){
        return new DefaultCurrencyBuilder();
    }

    public DefaultCurrencyBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultCurrencyBuilder withIso4217Code(String iso4217Code){
        this.iso4217Code = iso4217Code;
        return this;
    }

    public DefaultCurrencyBuilder withSubUnit110(String subUnit110){
        this.subUnit110 = subUnit110;
        return this;
    }

    public DefaultCurrencyBuilder withImage(String image){
        this.image = image;
        return this;
    }

    public DefaultCurrencyBuilder withCurrencyType(CurrencyType currencyType){
        this.currencyType = currencyType;
        return this;
    }

    public DefaultCurrency build(){
        DefaultCurrency currency = new DefaultCurrency();
        currency.setName(name);
        currency.setImage(image);
        currency.setIso4217Code(iso4217Code);
        currency.setSubUnit110(subUnit110);
        currency.setCurrencyType(currencyType);
        return currency;
    }
}
