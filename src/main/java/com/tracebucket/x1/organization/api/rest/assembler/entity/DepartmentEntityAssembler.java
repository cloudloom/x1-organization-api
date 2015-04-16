package com.tracebucket.x1.organization.api.rest.assembler.entity;

import com.tracebucket.tron.assembler.EntityAssembler;
import com.tracebucket.tron.ddd.domain.EntityId;
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
public class DepartmentEntityAssembler extends EntityAssembler<DefaultDepartment, DefaultDepartmentResource> {

    @Override
    public DefaultDepartment toEntity(DefaultDepartmentResource resource, Class<DefaultDepartment> entityClass) {
        DefaultDepartment department = null;
        if(resource != null) {
            department = new DefaultDepartment();
            if (resource.getUid() != null) {
                department.setEntityId(new EntityId(resource.getUid()));
            }
            department.setName(resource.getName());
            department.setDescription(resource.getDescription());
        }
        return department;
    }

    @Override
    public Set<DefaultDepartment> toEntities(Collection<DefaultDepartmentResource> resources, Class<DefaultDepartment> entityClass) {
        Set<DefaultDepartment> departments = new HashSet<DefaultDepartment>();
        if(resources != null) {
            Iterator<DefaultDepartmentResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                DefaultDepartmentResource organizationUnitResource = iterator.next();
                departments.add(toEntity(organizationUnitResource, DefaultDepartment.class));
            }
        }
        return departments;
    }
}