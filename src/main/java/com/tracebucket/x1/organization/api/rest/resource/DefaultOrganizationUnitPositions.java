package com.tracebucket.x1.organization.api.rest.resource;

import java.util.*;

/**
 * Created by sadath on 03-Jul-2015.
 */
public class DefaultOrganizationUnitPositions {
    private HashMap<String, List<String>> orgUnitPositions;

    public HashMap<String, List<String>> getOrgUnitPositions() {
        return orgUnitPositions;
    }

    public void setOrgUnitPositions(HashMap<String, List<String>> orgUnitPositions) {
        this.orgUnitPositions = orgUnitPositions;
    }
}