package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.rest.resource.DefaultPositionResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 24-07-2015.
 */
public class DefaultPositionResourceBuilder {

    private String name;
    private String code;
    private DefaultPositionResource parent;
    private Set<DefaultPositionResource> children = new HashSet<DefaultPositionResource>(0);

    private DefaultPositionResourceBuilder(){

    }

    public static DefaultPositionResourceBuilder aPositionBuilder(){
        return new DefaultPositionResourceBuilder();
    }

    public DefaultPositionResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultPositionResourceBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public DefaultPositionResourceBuilder withParent(DefaultPositionResource parent) {
        this.parent = parent;
        return this;
    }

    public DefaultPositionResourceBuilder withChildren(Set<DefaultPositionResource> children) {
        this.children = children;
        return this;
    }

    public DefaultPositionResource build() {
        DefaultPositionResource positionResource = new DefaultPositionResource();
        positionResource.setName(name);
        positionResource.setCode(code);
        positionResource.setParent(parent);
        positionResource.setChildren(children);

        return positionResource;
    }
}
