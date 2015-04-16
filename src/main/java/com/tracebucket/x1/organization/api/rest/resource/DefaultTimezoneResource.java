package com.tracebucket.x1.organization.api.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.TimezoneType;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultTimezoneResource extends BaseResource {
    private String name;
    private String abbreviation;
    private Integer utcOffset;
    private String image;
    private TimezoneType timezoneType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public TimezoneType getTimezoneType() {
        return timezoneType;
    }

    public void setTimezoneType(TimezoneType timezoneType) {
        this.timezoneType = timezoneType;
    }
}