package com.tracebucket.x1.organization.api.rest.assembler.resource;

import com.tracebucket.tron.assembler.ResourceAssembler;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultDepartment;
import com.tracebucket.x1.organization.api.rest.resource.DefaultDepartmentResource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sadath on 06-Apr-15.
 */
@Component
public class DepartmentResourceAssembler extends ResourceAssembler<DefaultDepartmentResource, DefaultDepartment> {

    @Override
    public DefaultDepartmentResource toResource(DefaultDepartment entity, Class<DefaultDepartmentResource> resourceClass) {
        DefaultDepartmentResource departmentResource = null;
        try {
            departmentResource = resourceClass.newInstance();
            if (entity != null) {
                departmentResource = new DefaultDepartmentResource();
                departmentResource.setUid(entity.getEntityId().getId());
                departmentResource.setName(entity.getName());
                departmentResource.setDescription(entity.getDescription());
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return departmentResource;
    }

    @Override
    public Set<DefaultDepartmentResource> toResources(Collection<DefaultDepartment> entities, Class<DefaultDepartmentResource> resourceClass) {
        Set<DefaultDepartmentResource> departmentResources = new HashSet<DefaultDepartmentResource>();
        if(entities != null) {
            Iterator<DefaultDepartment> iterator = entities.iterator();
            if(iterator.hasNext()) {
                DefaultDepartment department = iterator.next();
                departmentResources.add(toResource(department, DefaultDepartmentResource.class));
            }
        }
        return departmentResources;
    }

}