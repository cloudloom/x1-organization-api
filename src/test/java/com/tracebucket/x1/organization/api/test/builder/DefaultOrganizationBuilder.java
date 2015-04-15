package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultOrganizationBuilder {

    private DefaultOrganizationBuilder(){ }

    public static DefaultOrganizationBuilder anOrganizationBuilder(){
        return new DefaultOrganizationBuilder();
    }

    public DefaultOrganization build(String name, String code, String description){
        return new DefaultOrganization(name, code, description);
    }

    public DefaultOrganization build(String name, String code, String description, String website, String image){
        return new DefaultOrganization(name, code, description, website, image);
    }

}