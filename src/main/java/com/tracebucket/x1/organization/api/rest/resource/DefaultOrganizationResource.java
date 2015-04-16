package com.tracebucket.x1.organization.api.rest.resource;


import com.tracebucket.tron.assembler.BaseResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationResource extends BaseResource {
    private String code;
    private String name;
    private String description;
    private String website;
    protected String image;
    private Set<DefaultOrganizationUnitResource> organizationUnits = new HashSet<DefaultOrganizationUnitResource>(0);

    public DefaultOrganizationResource() {
    }

    public DefaultOrganizationResource(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public DefaultOrganizationResource(String name, String code, String description, String website, String image) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.website = website;
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<DefaultOrganizationUnitResource> getOrganizationUnits() {
        return organizationUnits;
    }

    public void setOrganizationUnits(Set<DefaultOrganizationUnitResource> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }
}