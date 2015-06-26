package com.tracebucket.x1.organization.api.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.organization.api.domain.impl.jpa.PositionType;

/**
 * @author ffazil
 * @since 16/05/15
 */
public class DefaultPositionResource extends BaseResource{
    private String name;
    private PositionType positionType;
    private String code;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultPositionResource that = (DefaultPositionResource) o;

        if (!code.equals(that.code)) return false;
        if (!name.equals(that.name)) return false;
        if (positionType != that.positionType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + positionType.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
