package com.tracebucket.x1.organization.api.rest.resource;

import java.util.Map;
import java.util.Set;

/**
 * Created by sadath on 03-Jul-2015.
 */
public class DefaultOrganizationUnitPositions {
    private Map<String, Set<DefaultPositionResource>> orgUnitPositions;

    public Map<String, Set<DefaultPositionResource>> getOrgUnitPositions() {
        return orgUnitPositions;
    }

    public void setOrgUnitPositions(Map<String, Set<DefaultPositionResource>> orgUnitPositions) {
        this.orgUnitPositions = orgUnitPositions;
    }
}