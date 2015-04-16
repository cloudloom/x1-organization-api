package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.TimezoneType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultTimezone;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultTimezoneBuilder {
    private String name;
    private String abbreviation;
    private Integer utcOffset;
    private String image;
    private TimezoneType timezoneType;

    private DefaultTimezoneBuilder(){

    }

    public static DefaultTimezoneBuilder aDefaultTimezoneBuilder(){
        return new DefaultTimezoneBuilder();
    }

    public DefaultTimezoneBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultTimezoneBuilder withAbbreviation(String abbreviation){
        this.abbreviation = abbreviation;
        return this;
    }

    public DefaultTimezoneBuilder withUtcOffset(Integer utcOffset){
        this.utcOffset = utcOffset;
        return this;
    }

    public DefaultTimezoneBuilder withImage(String image){
        this.image = image;
        return this;
    }

    public DefaultTimezoneBuilder withTimezoneType(TimezoneType timezoneType){
        this.timezoneType = timezoneType;
        return this;
    }

    public DefaultTimezone build(){
        DefaultTimezone timezone = new DefaultTimezone();
        timezone.setImage(image);
        timezone.setAbbreviation(abbreviation);
        timezone.setName(name);
        timezone.setTimezoneType(timezoneType);
        timezone.setUtcOffset(utcOffset);
        return timezone;
    }
}