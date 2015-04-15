package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.organization.api.domain.BusinessLine;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultBusinessLine;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultBusinessLineBuilder {
    private String name;
    private String description;

    public DefaultBusinessLineBuilder(){ }

    public static DefaultBusinessLineBuilder aBusinessLine(){
        return new DefaultBusinessLineBuilder();
    }

    public DefaultBusinessLineBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultBusinessLineBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public DefaultBusinessLine build(){
        DefaultBusinessLine businessLine = new DefaultBusinessLine();
        businessLine.setName(name);
        businessLine.setDescription(description);
        return businessLine;
    }
}
