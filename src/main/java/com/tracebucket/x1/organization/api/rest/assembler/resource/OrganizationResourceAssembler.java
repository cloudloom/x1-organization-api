package com.tracebucket.x1.organization.api.rest.assembler.resource;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;
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
public class OrganizationResourceAssembler extends ResourceAssembler<DefaultOrganizationResource, DefaultOrganization> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public DefaultOrganizationResource toResource(DefaultOrganization entity, Class<DefaultOrganizationResource> resourceClass) {
        DefaultOrganizationResource organizationResource = null;
        try {
            organizationResource = resourceClass.newInstance();
            if (entity != null) {
                organizationResource = new DefaultOrganizationResource();
                organizationResource.setUid(entity.getAggregateId().getAggregateId());
                organizationResource.setName(entity.getName());
                organizationResource.setDescription(entity.getDescription());
                organizationResource.setCode(entity.getCode());
                organizationResource.setImage(entity.getImage());
                organizationResource.setWebsite(entity.getWebsite());
                organizationResource.setOrganizationUnits(assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class)
                        .toResources(entity.getOrganizationUnits(), DefaultOrganizationUnitResource.class));
            }
        } catch (IllegalAccessException iae) {

        } catch (InstantiationException ie) {

        }
        return organizationResource;
    }

    @Override
    public Set<DefaultOrganizationResource> toResources(Collection<DefaultOrganization> entities, Class<DefaultOrganizationResource> resourceClass) {
        Set<DefaultOrganizationResource> organizations = new HashSet<DefaultOrganizationResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<DefaultOrganization> iterator = entities.iterator();
            if(iterator.hasNext()) {
                DefaultOrganization organization = iterator.next();
                organizations.add(toResource(organization, DefaultOrganizationResource.class));
            }
        }
        return organizations;
    }
}
