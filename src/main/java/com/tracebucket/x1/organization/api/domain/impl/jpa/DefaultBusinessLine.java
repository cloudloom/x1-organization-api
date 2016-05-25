package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.organization.api.domain.BusinessLine;

import javax.persistence.*;

/**
 * Created by Vishwajit on 13-04-2015.
 * JPA BusinessLine
 */

@Entity
@Table(name = "BUSINESS_LINE")
public class DefaultBusinessLine extends BaseEntity implements BusinessLine {

    @Column(name = "NAME", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.EAGER)
    private String description;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
