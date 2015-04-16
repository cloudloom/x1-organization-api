package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationResource;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultOrganizationResourceBuilder {
    private DefaultOrganizationResourceBuilder(){ }

    public static DefaultOrganizationResourceBuilder anOrganizationResourceBuilder(){
        return new DefaultOrganizationResourceBuilder();
    }

    public DefaultOrganizationResource build(String name, String code, String description){
        return new DefaultOrganizationResource(name, code, description);
    }

    public DefaultOrganizationResource build(String name, String code, String description, String website, String image){
        return new DefaultOrganizationResource(name, code, description, website, image);
    }
}