package com.tracebucket.x1.organization.api.rest.resource;


import com.tracebucket.tron.assembler.BaseResource;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by sadath on 31-Mar-15.
 */
public class DefaultDepartmentResource extends BaseResource {
    @NotEmpty
    @Size(min = 1, max = 200)
    @Pattern(regexp = "^[A-Za-z ]*$")
    private String name;

    @Size(min = 1, max = 255)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}