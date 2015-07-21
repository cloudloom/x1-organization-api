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

    @Column(name = "POSITION_TYPE", nullable = false, columnDefinition = "ENUM('TOP_LEVEL_EXECUTIVES', 'MID_LEVEL_EXECUTIVES', 'MANAGERIAL', 'FRONT_OFFICE', 'BACK_OFFICE', 'FIELD_STAFF', 'SUPPORT_STAFF', 'CUSTOMER_SERVICE_AGENT') default 'SUPPORT_STAFF'")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultPosition that = (DefaultPosition) o;

        if (!code.equals(that.code)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}