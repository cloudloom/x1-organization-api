package com.tracebucket.x1.organization.api.rest.assembler.resource;

import com.tracebucket.tron.assembler.ResourceAssembler;

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
public class BusinessLineResourceAssembler extends ResourceAssembler<DefaultBusinessLineResource, DefaultBusinessLine> {

    @Override
    public DefaultBusinessLineResource toResource(DefaultBusinessLine entity, Class<DefaultBusinessLineResource> resourceClass) {
        DefaultBusinessLineResource businessLineResource = null;
        try {
            businessLineResource = resourceClass.newInstance();
            if (entity != null) {
                businessLineResource = new DefaultBusinessLineResource();
                businessLineResource.setUid(entity.getEntityId().getId());
                businessLineResource.setName(entity.getName());
                businessLineResource.setDescription(entity.getDescription());
            }
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return businessLineResource;
    }

    @Override
    public Set<DefaultBusinessLineResource> toResources(Collection<DefaultBusinessLine> entities, Class<DefaultBusinessLineResource> resourceClass) {
        Set<DefaultBusinessLineResource> businessLines = new HashSet<DefaultBusinessLineResource>();
        if(entities != null) {
            Iterator<DefaultBusinessLine> iterator = entities.iterator();
            if(iterator.hasNext()) {
                DefaultBusinessLine businessLine = iterator.next();
                businessLines.add(toResource(businessLine, DefaultBusinessLineResource.class));
            }
        }
        return businessLines;
    }
}