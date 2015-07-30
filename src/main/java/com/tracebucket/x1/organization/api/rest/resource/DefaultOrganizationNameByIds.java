package com.tracebucket.x1.organization.api.rest.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sadath on 30-Jul-2015.
 */
@com.fasterxml.jackson.databind.annotation.JsonSerialize(include = com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultOrganizationNameByIds {
    private Map<String, String> organizations = new HashMap<String, String>();
    private Map<String, String> organizationUnits = new HashMap<String, String>();
    private Map<String, String> departments = new HashMap<String, String>();
    private Map<String, String> positions = new HashMap<String, String>();

    public Map<String, String> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Map<String, String> organizations) {
        this.organizations = organizations;
    }

    public Map<String, String> getOrganizationUnits() {
        return organizationUnits;
    }

    public void setOrganizationUnits(Map<String, String> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }

    public Map<String, String> getDepartments() {
        return departments;
    }

    public void setDepartments(Map<String, String> departments) {
        this.departments = departments;
    }

    public Map<String, String> getPositions() {
        return positions;
    }

    public void setPositions(Map<String, String> positions) {
        this.positions = positions;
    }
}