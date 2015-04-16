package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.rest.resource.DefaultBusinessLineResource;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultBusinessLineResourceBuilder {
    private String name;
    private String description;

    public DefaultBusinessLineResourceBuilder(){ }

    public static DefaultBusinessLineResourceBuilder aBusinessLineResource(){
        return new DefaultBusinessLineResourceBuilder();
    }

    public DefaultBusinessLineResourceBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultBusinessLineResourceBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public DefaultBusinessLineResource build(){
        DefaultBusinessLineResource businessLine = new DefaultBusinessLineResource();
        businessLine.setName(name);
        businessLine.setDescription(description);
        return businessLine;
    }
}