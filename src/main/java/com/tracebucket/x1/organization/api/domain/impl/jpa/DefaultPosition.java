package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.organization.api.domain.Position;

import javax.persistence.*;

/**
 * @author ffazil
 * @since 16/05/15
 */
@Entity
@Table(name = "POSITION")
public class DefaultPosition extends BaseEntity implements Position{

    @Column(name = "NAME", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "POSITION_TYPE", nullable = false, columnDefinition = "ENUM('TOP LEVEL EXECUTIVES', 'MID LEVEL EXECUTIVES', 'MANAGERIAL', 'FRONT OFFICE', 'Back Office', 'FIELD STAFF', 'SUPPORT STAFF') default 'SUPPORT STAFF'")
    @Enumerated(EnumType.STRING)
    private PositionType positionType;

    @Column(name = "CODE", nullable = false, unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String code;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public PositionType getPositionType() {
        return positionType;
    }

    @Override
    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}