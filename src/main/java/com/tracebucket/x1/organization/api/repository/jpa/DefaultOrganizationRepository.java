package com.tracebucket.x1.organization.api.repository.jpa;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.jpa.BaseAggregateRepository;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;

/**
 * Created by Vishwajit on 15-04-2015.
 */
public interface DefaultOrganizationRepository extends BaseAggregateRepository<DefaultOrganization, AggregateId> {
}
