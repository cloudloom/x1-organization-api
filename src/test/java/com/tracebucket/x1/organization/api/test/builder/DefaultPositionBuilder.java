package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultPosition;
import com.tracebucket.x1.organization.api.domain.impl.jpa.PositionType;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 24-07-2015.
 */
public class DefaultPositionBuilder {

    private String name;
    private String code;
    private DefaultPosition parent;
    private Set<DefaultPosition> children = new HashSet<DefaultPosition>(0);

    private DefaultPositionBuilder(){

    }

    public static DefaultPositionBuilder aPositionBuilder(){
        return new DefaultPositionBuilder();
    }

    public DefaultPositionBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultPositionBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public DefaultPositionBuilder withParent(DefaultPosition parent) {
        this.parent = parent;
        return this;
    }

    public DefaultPositionBuilder withChildren(Set<DefaultPosition> children) {
        this.children = children;
        return this;
    }

    public DefaultPosition build() {
        DefaultPosition position = new DefaultPosition();
        position.setName(name);
        position.setCode(code);
        position.setParent(parent);
        position.setChildren(children);

        return position;
    }
}
