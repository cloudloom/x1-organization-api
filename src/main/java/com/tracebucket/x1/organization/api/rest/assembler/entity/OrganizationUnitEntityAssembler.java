package com.tracebucket.x1.organization.api.rest.assembler.entity;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.EntityAssembler;
import com.tracebucket.tron.ddd.domain.EntityId;

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
public class OrganizationUnitEntityAssembler extends EntityAssembler<DefaultOrganizationUnit, DefaultOrganizationUnitResource> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    public DefaultOrganizationUnit toEntity(DefaultOrganizationUnitResource resource, Class<DefaultOrganizationUnit> entityClass) {
        DefaultOrganizationUnit organizationUnit = null;
        if(resource != null) {
            organizationUnit = new DefaultOrganizationUnit();
            if (resource.getUid() != null) {
                organizationUnit.setEntityId(new EntityId(resource.getUid()));
            }
            organizationUnit.setName(resource.getName());
            organizationUnit.setDescription(resource.getDescription());
            organizationUnit.setOrganizationFunctions(resource.getOrganizationFunctions());
            organizationUnit.setBusinessLines(assemblerResolver.resolveEntityAssembler(DefaultBusinessLine.class, DefaultBusinessLineResource.class)
                    .toEntities(resource.getBusinessLines(), DefaultBusinessLine.class));
            organizationUnit.setDepartments(assemblerResolver.resolveEntityAssembler(DefaultDepartment.class, DefaultDepartmentResource.class)
                    .toEntities(resource.getDepartments(), DefaultDepartment.class));
            organizationUnit.setChildren(toEntities(resource.getChildren(), DefaultOrganizationUnit.class));
            organizationUnit.setParent(toEntity(resource.getParent(), DefaultOrganizationUnit.class));
        }
        return organizationUnit;
    }

    @Override
    public Set<DefaultOrganizationUnit> toEntities(Collection<DefaultOrganizationUnitResource> resources, Class<DefaultOrganizationUnit> entityClass) {
        Set<DefaultOrganizationUnit> organizationUnits = new HashSet<DefaultOrganizationUnit>();
        if(resources != null) {
            Iterator<DefaultOrganizationUnitResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                DefaultOrganizationUnitResource organizationUnitResource = iterator.next();
                organizationUnits.add(toEntity(organizationUnitResource, DefaultOrganizationUnit.class));
            }
        }
        return organizationUnits;
    }
}