package com.tracebucket.x1.organization.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by sadath on 19-Aug-2015.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultOrganizationUnitRestructureResource {
    private String organizationUid;
    private String organizationUnitUid;
    private String parentUid;

    public String getOrganizationUid() {
        return organizationUid;
    }

    public void setOrganizationUid(String organizationUid) {
        this.organizationUid = organizationUid;
    }

    public String getOrganizationUnitUid() {
        return organizationUnitUid;
    }

    public void setOrganizationUnitUid(String organizationUnitUid) {
        this.organizationUnitUid = organizationUnitUid;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }
}