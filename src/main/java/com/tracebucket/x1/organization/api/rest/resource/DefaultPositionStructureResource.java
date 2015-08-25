package com.tracebucket.x1.organization.api.rest.resource;

/**
 * Created by sadath on 25-Aug-2015.
 */
public class DefaultPositionStructureResource {
    private String uid;
    private String parentUid;
    private String organizationUid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getOrganizationUid() {
        return organizationUid;
    }

    public void setOrganizationUid(String organizationUid) {
        this.organizationUid = organizationUid;
    }

}