package com.tracebucket.x1.organization.api.domain;

import com.tracebucket.x1.organization.api.domain.impl.jpa.PositionType;

/**
 * Created by sadath on 18-Jun-2015.
 */
public interface Position {
    public String getName();
    public void setName(String name);
    public PositionType getPositionType();
    public void setPositionType(PositionType positionType);
    public String getCode();
    public void setCode(String code);
}