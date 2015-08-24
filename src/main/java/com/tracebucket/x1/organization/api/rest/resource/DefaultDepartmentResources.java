package com.tracebucket.x1.organization.api.rest.resource;

import javax.validation.Valid;
import java.util.HashSet;

/**
 * Created by sadath on 24-Aug-2015.
 */
public class DefaultDepartmentResources {
    @Valid
    private HashSet<DefaultDepartmentResource> departments = new HashSet<>();

    public HashSet<DefaultDepartmentResource> getDepartments() {
        return departments;
    }

    public void setDepartments(HashSet<DefaultDepartmentResource> departments) {
        this.departments = departments;
    }
}