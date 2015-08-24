package com.tracebucket.x1.organization.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.organization.api.domain.impl.jpa.PositionType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ffazil
 * @since 16/05/15
 */
public class DefaultPositionResource extends BaseResource implements Comparable<DefaultPositionResource>{
    @NotNull
    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z ]*$")
    private String name;

    @NotNull
    private PositionType positionType;

    @NotNull
    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z0-9\\-]*$")
    private String code;

    @JsonBackReference
    private DefaultPositionResource parent;

    @JsonManagedReference
    private Set<DefaultPositionResource> children = new HashSet<DefaultPositionResource>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DefaultPositionResource getParent() {
        return parent;
    }

    public void setParent(DefaultPositionResource parent) {
        this.parent = parent;
    }

    public Set<DefaultPositionResource> getChildren() {
        return children;
    }

    public void setChildren(Set<DefaultPositionResource> children) {
        this.children = children;
    }

    @Override
    public int compareTo(DefaultPositionResource o) {
        if(o != null) {
            if (this == o) {
                return 0;
            }
            return this.name.compareTo(o.getName());
        }
        return -1;
    }

    /*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultPositionResource that = (DefaultPositionResource) o;

        if (!code.equals(that.code)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }*/
}
