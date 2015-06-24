package com.tracebucket.x1.organization.api.repository.jpa;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.jpa.BaseAggregateRepository;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Vishwajit on 15-04-2015.
 */
public interface DefaultOrganizationRepository extends BaseAggregateRepository<DefaultOrganization, AggregateId> {
    @Query(value = "select o.* from ORGANIZATION AS o inner join ORGANIZATION_UNIT AS ou ON o.ID = ou.ORGANIZATION__ID where ou.parent_ID IS NULL", nativeQuery = true)
    public List<DefaultOrganization> findAllWithParentOrganizationUnits();
}