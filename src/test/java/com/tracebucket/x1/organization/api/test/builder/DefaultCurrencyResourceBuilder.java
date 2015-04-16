package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.CurrencyType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultCurrencyResource;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultCurrencyResourceBuilder {
    private String name;
    private String iso4217Code;
    private String subUnit110;
    private String image;
    private CurrencyType currencyType;

    private DefaultCurrencyResourceBuilder(){

    }

    public static DefaultCurrencyResourceBuilder aDefaultCurrencyResourceBuilder(){
        return new DefaultCurrencyResourceBuilder();
    }

    public DefaultCurrencyResourceBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultCurrencyResourceBuilder withIso4217Code(String iso4217Code){
        this.iso4217Code = iso4217Code;
        return this;
    }

    public DefaultCurrencyResourceBuilder withSubUnit110(String subUnit110){
        this.subUnit110 = subUnit110;
        return this;
    }

    public DefaultCurrencyResourceBuilder withImage(String image){
        this.image = image;
        return this;
    }

    public DefaultCurrencyResourceBuilder withCurrencyType(CurrencyType currencyType){
        this.currencyType = currencyType;
        return this;
    }

    public DefaultCurrencyResource build(){
        DefaultCurrencyResource currency = new DefaultCurrencyResource();
        currency.setName(name);
        currency.setImage(image);
        currency.setIso4217Code(iso4217Code);
        currency.setSubUnit110(subUnit110);
        currency.setCurrencyType(currencyType);
        return currency;
    }
}