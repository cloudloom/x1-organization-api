package com.tracebucket.x1.organization.api.rest.assembler.entity;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.EntityAssembler;
import com.tracebucket.tron.ddd.domain.AggregateId;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationUnitResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sadath on 06-Apr-15.
 */
@Component
public class OrganizationEntityAssembler extends EntityAssembler<DefaultOrganization, DefaultOrganizationResource> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    public DefaultOrganization toEntity(DefaultOrganizationResource resource, Class<DefaultOrganization> entityClass) {
        DefaultOrganization organization = null;
        if(resource != null) {
            organization = new DefaultOrganization();
            if (resource.getUid() != null) {
                organization.setAggregateId(new AggregateId(resource.getUid()));
            }
            organization.setName(resource.getName());
            organization.setCode(resource.getCode());
            organization.setDescription(resource.getDescription());
            organization.setImage(resource.getImage());
            organization.setWebsite(resource.getWebsite());
            organization.setOrganizationUnits(assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class)
                    .toEntities(resource.getOrganizationUnits(), DefaultOrganizationUnit.class));
        }
        return organization;
    }

    @Override
    public Set<DefaultOrganization> toEntities(Collection<DefaultOrganizationResource> resources, Class<DefaultOrganization> entityClass) {
        Set<DefaultOrganization> organizations = new HashSet<DefaultOrganization>();
        if(resources != null) {
            Iterator<DefaultOrganizationResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                DefaultOrganizationResource organizationResource = iterator.next();
                organizations.add(toEntity(organizationResource, DefaultOrganization.class));
            }
        }
        return organizations;
    }
}
