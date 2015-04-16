package com.tracebucket.x1.organization.api.rest.assembler.entity;

import com.tracebucket.tron.assembler.EntityAssembler;
import com.tracebucket.tron.ddd.domain.EntityId;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultBusinessLine;
import com.tracebucket.x1.organization.api.rest.resource.DefaultBusinessLineResource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sadath on 06-Apr-15.
 */
@Component
public class BusinessLineEntityAssembler extends EntityAssembler<DefaultBusinessLine, DefaultBusinessLineResource> {

    @Override
    public DefaultBusinessLine toEntity(DefaultBusinessLineResource resource, Class<DefaultBusinessLine> entityClass) {
        DefaultBusinessLine businessLine = null;
        if(resource != null) {
            businessLine = new DefaultBusinessLine();
            if (resource.getUid() != null) {
                businessLine.setEntityId(new EntityId(resource.getUid()));
            }
            businessLine.setName(resource.getName());
            businessLine.setDescription(resource.getDescription());
        }
        return businessLine;
    }

    @Override
    public Set<DefaultBusinessLine> toEntities(Collection<DefaultBusinessLineResource> resources, Class<DefaultBusinessLine> entityClass) {
        Set<DefaultBusinessLine> businessLines = new HashSet<DefaultBusinessLine>();
        if(resources != null) {
            Iterator<DefaultBusinessLineResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                DefaultBusinessLineResource businessLineResource = iterator.next();
                businessLines.add(toEntity(businessLineResource, DefaultBusinessLine.class));
            }
        }
        return businessLines;
    }
}
