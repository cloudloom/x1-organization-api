package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.TimezoneType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultTimezoneResource;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultTimezoneResourceBuilder {
    private String name;
    private String abbreviation;
    private Integer utcOffset;
    private String image;
    private TimezoneType timezoneType;

    private DefaultTimezoneResourceBuilder(){

    }

    public static DefaultTimezoneResourceBuilder aDefaultTimezoneResourceBuilder(){
        return new DefaultTimezoneResourceBuilder();
    }

    public DefaultTimezoneResourceBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultTimezoneResourceBuilder withAbbreviation(String abbreviation){
        this.abbreviation = abbreviation;
        return this;
    }

    public DefaultTimezoneResourceBuilder withUtcOffset(Integer utcOffset){
        this.utcOffset = utcOffset;
        return this;
    }

    public DefaultTimezoneResourceBuilder withImage(String image){
        this.image = image;
        return this;
    }

    public DefaultTimezoneResourceBuilder withTimezoneType(TimezoneType timezoneType){
        this.timezoneType = timezoneType;
        return this;
    }

    public DefaultTimezoneResource build(){
        DefaultTimezoneResource timezone = new DefaultTimezoneResource();
        timezone.setImage(image);
        timezone.setAbbreviation(abbreviation);
        timezone.setName(name);
        timezone.setTimezoneType(timezoneType);
        timezone.setUtcOffset(utcOffset);
        return timezone;
    }
}