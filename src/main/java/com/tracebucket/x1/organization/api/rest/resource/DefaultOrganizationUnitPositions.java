package com.tracebucket.x1.organization.api.rest.resource;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;

import java.util.*;

/**
 * Created by sadath on 03-Jul-2015.
 */
@com.fasterxml.jackson.databind.annotation.JsonSerialize(include = com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultOrganizationUnitPositions {
    private HashMap<String, List<String>> orgUnitPositions;
    private HashMap<String, List<Map<String, String>>> organizationUnitPositions;

    public HashMap<String, List<String>> getOrgUnitPositions() {
        return orgUnitPositions;
    }

    public void setOrgUnitPositions(HashMap<String, List<String>> orgUnitPositions) {
        this.orgUnitPositions = orgUnitPositions;
    }

    public HashMap<String, List<Map<String, String>>> getOrganizationUnitPositions() {
        return organizationUnitPositions;
    }

    public void setOrganizationUnitPositions(HashMap<String, List<Map<String, String>>> organizationUnitPositions) {
        this.organizationUnitPositions = organizationUnitPositions;
    }
}