package com.tracebucket.x1.organization.api.rest.resource;


import com.tracebucket.tron.assembler.BaseResource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultBusinessLineResource extends BaseResource {

    @NotNull
    @Size(min = 1, max = 250)
    //alphabets only
    private String name;

    @Size(min = 1, max = 255)
    //can have any character.
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}