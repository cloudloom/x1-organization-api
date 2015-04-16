package com.tracebucket.x1.organization.api.rest.assembler.resource;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultBusinessLine;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.rest.resource.DefaultBusinessLineResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultDepartmentResource;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationUnitResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sadath on 31-Mar-15.
 */
@Component
public class OrganizationUnitResourceAssembler extends ResourceAssembler<DefaultOrganizationUnitResource, DefaultOrganizationUnit> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public DefaultOrganizationUnitResource toResource(DefaultOrganizationUnit entity, Class<DefaultOrganizationUnitResource> resourceClass) {
        DefaultOrganizationUnitResource organizationUnitResource = null;
        try {
            organizationUnitResource = resourceClass.newInstance();
            if (entity != null) {
                organizationUnitResource = new DefaultOrganizationUnitResource();
                organizationUnitResource.setUid(entity.getEntityId().getId());
                organizationUnitResource.setName(entity.getName());
                organizationUnitResource.setDescription(entity.getDescription());
                organizationUnitResource.setBusinessLines(assemblerResolver.resolveResourceAssembler(DefaultBusinessLineResource.class, DefaultBusinessLine.class)
                        .toResources(entity.getBusinessLines(), DefaultBusinessLineResource.class));
                organizationUnitResource.setDepartments(assemblerResolver.resolveResourceAssembler(DefaultDepartmentResource.class, DefaultDepartment.class)
                        .toResources(entity.getDepartments(), DefaultDepartmentResource.class));
                organizationUnitResource.setChildren(toResources(entity.getChildren(), DefaultOrganizationUnitResource.class));
                organizationUnitResource.setOrganizationFunctions(entity.getOrganizationFunctions());
                organizationUnitResource.setParent(toResource(entity.getParent(), DefaultOrganizationUnitResource.class));
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return organizationUnitResource;
    }

    @Override
    public Set<DefaultOrganizationUnitResource> toResources(Collection<DefaultOrganizationUnit> entities, Class<DefaultOrganizationUnitResource> resourceClass) {
        Set<DefaultOrganizationUnitResource> organizationUnits = new HashSet<DefaultOrganizationUnitResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<DefaultOrganizationUnit> iterator = entities.iterator();
            if(iterator.hasNext()) {
                DefaultOrganizationUnit organizationUnit = iterator.next();
                organizationUnits.add(toResource(organizationUnit, DefaultOrganizationUnitResource.class));
            }
        }
        return organizationUnits;
    }
}