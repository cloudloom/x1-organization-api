package com.tracebucket.x1.organization.api.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.rest.exception.X1Exception;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.*;
import com.tracebucket.x1.organization.api.rest.resource.*;
import com.tracebucket.x1.organization.api.service.DefaultOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by sadath on 10-Feb-15.
 */
@RestController
public class OrganizationController implements Organization {

    private static Logger log = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private DefaultOrganizationService organizationService;

    @Autowired
    private AssemblerResolver assemblerResolver;

    @RequestMapping(value = "/organization", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> createOrganization(@Valid @RequestBody DefaultOrganizationResource organizationResource) {
        DefaultOrganization organization = assemblerResolver.resolveEntityAssembler(DefaultOrganization.class, DefaultOrganizationResource.class).toEntity(organizationResource, DefaultOrganization.class);
        try {
            organization = organizationService.save(organization);
        } catch (DataIntegrityViolationException dive) {
            throw new X1Exception("Organization With Name : " + organizationResource.getName() + "Exists", HttpStatus.CONFLICT);
        }
        if (organization != null) {
            organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
            return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/organizationunit", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @Valid @RequestBody DefaultOrganizationUnitResource organizationUnitResource) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganizationUnit organizationUnit = assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class).toEntity(organizationUnitResource, DefaultOrganizationUnit.class);
            DefaultOrganization organization = null;
            try {
                organization = organizationService.addOrganizationUnit(tenantId, organizationUnit, new AggregateId(organizationUid));
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                if (organizationUnits != null) {
                    Iterator<DefaultOrganizationUnit> iterator1 = organizationUnits.iterator();
                    while (iterator1.hasNext()) {
                        DefaultOrganizationUnit organizationUnit1 = iterator1.next();
                        if (organizationUnit1.getParent() != null) {
                            iterator1.remove();
                        } else if(organizationUnit1.getParent() == null && organizationUnit1.isPassive()) {
                            iterator1.remove();
                        }
                    }
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception("Organization Unit With Name : " + organizationUnitResource.getName() + "Exists", HttpStatus.CONFLICT);
            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/organizationunit/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> updateOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @Valid @RequestBody DefaultOrganizationUnitResource organizationUnitResource) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganizationUnit organizationUnit = assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class).toEntity(organizationUnitResource, DefaultOrganizationUnit.class);
            DefaultOrganization organization = null;
            try {
                organization = organizationService.updateOrganizationUnit(tenantId, organizationUnit, new AggregateId(organizationUid));
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                if (organizationUnits != null) {
                    Iterator<DefaultOrganizationUnit> iterator1 = organizationUnits.iterator();
                    while (iterator1.hasNext()) {
                        DefaultOrganizationUnit organizationUnit1 = iterator1.next();
                        if (organizationUnit1.getParent() != null) {
                            iterator1.remove();
                        } else if(organizationUnit1.getParent() == null && organizationUnit1.isPassive()) {
                            iterator1.remove();
                        }
                    }
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception("Organization Unit With Name : " + organizationUnitResource.getName() + "Exists", HttpStatus.CONFLICT);
            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> getOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = organizationService.findOne(tenantId, new AggregateId(organizationUid));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnits/unstructured", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> getOrganizationUnitsUnstructured(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = organizationService.findOne(tenantId, new AggregateId(organizationUid));
            Set<DefaultOrganizationUnit> units = organization.getOrganizationUnits();
            if(units != null && units.size() >0) {
                Iterator<DefaultOrganizationUnit> iterator = units.iterator();
                while(iterator.hasNext()) {
                    DefaultOrganizationUnit unit = iterator.next();
                    if(unit.isPassive()) {
                        iterator.remove();
                    }
                }
                Set<DefaultOrganizationUnitResource> organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResources(units, DefaultOrganizationUnitResource.class);
                if (organization != null) {
                    return new ResponseEntity<Set<DefaultOrganizationUnitResource>>(organizationResource, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/departments", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addDepartmentToOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @Valid @RequestBody DefaultDepartmentResources departments) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultDepartment> defaultDepartments = assemblerResolver.resolveEntityAssembler(DefaultDepartment.class, DefaultDepartmentResource.class).toEntities(departments.getDepartments(), DefaultDepartment.class);
            DefaultOrganization organization = null;
            try {
                organization = organizationService.addDepartmentToOrganization(tenantId, new AggregateId(organizationAggregateId), defaultDepartments);
                removeDeletedOrganizationUnits(organization);
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.CONFLICT);
            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/departments/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> updateDepartmentOfOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @Valid @RequestBody DefaultDepartmentResources departments) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultDepartment> defaultDepartments = assemblerResolver.resolveEntityAssembler(DefaultDepartment.class, DefaultDepartmentResource.class).toEntities(departments.getDepartments(), DefaultDepartment.class);
            DefaultOrganization organization = null;
            try {
                organization = organizationService.updateDepartmentOfOrganization(tenantId, new AggregateId(organizationAggregateId), defaultDepartments);
                removeDeletedOrganizationUnits(organization);
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.CONFLICT);
            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/departments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultDepartmentResource>> getDepartmentsOfOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultDepartment> departments = organizationService.getDepartmentsOfOrganization(tenantId, new AggregateId(organizationAggregateId));
            if (departments != null && departments.size() > 0) {
                Set<DefaultDepartmentResource> departmentResources = assemblerResolver.resolveResourceAssembler(DefaultDepartmentResource.class, DefaultDepartment.class).toResources(departments, DefaultDepartmentResource.class);
                return new ResponseEntity<Set<DefaultDepartmentResource>>(departmentResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/departments", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addDepartmentToOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @PathVariable("organizationUnitUid") String organizationUnitEntityId, @RequestBody HashSet<String> departments) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = null;
            try {
                organization = organizationService.addDepartmentToOrganizationUnit(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId), departments);
                removeDeletedOrganizationUnits(organization);
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.CONFLICT);
            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/departments/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> updateDepartmentOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @PathVariable("organizationUnitUid") String organizationUnitEntityId, @RequestBody HashSet<String> departments) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = null;
            try {
                organization = organizationService.updateDepartmentOfOrganizationUnit(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId), departments);
                removeDeletedOrganizationUnits(organization);
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.CONFLICT);
            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/departments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultDepartmentResource>> getDepartmentsOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @PathVariable("organizationUnitUid") String organizationUnitEntityId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultDepartment> departments = organizationService.getDepartmentsOfOrganizationUnit(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId));
            if (departments != null && departments.size() > 0) {
                Set<DefaultDepartmentResource> departmentResources = assemblerResolver.resolveResourceAssembler(DefaultDepartmentResource.class, DefaultDepartment.class).toResources(departments, DefaultDepartmentResource.class);
                return new ResponseEntity<Set<DefaultDepartmentResource>>(departmentResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/ids/names", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationNameByIds> getOrganizationNameDetailsByUIDS(HttpServletRequest request, @RequestBody DefaultOrganizationNameByIds resource) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            if(resource != null) {
                DefaultOrganizationNameByIds idNames = organizationService.getOrganizationNameDetailsByUIDS(tenantId, resource);
                if (idNames != null) {
                    return new ResponseEntity<DefaultOrganizationNameByIds>(idNames, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/position/hierarchy", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DefaultPositionResource>> restructurePositionHierarchy(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @RequestBody List<DefaultPositionResource> positionsHierarchy) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultPosition> positions = null;
            try {
                positions = assemblerResolver.resolveEntityAssembler(DefaultPosition.class, DefaultPositionResource.class).toEntities(positionsHierarchy, DefaultPosition.class);
                DefaultOrganization organization = organizationService.restructurePositionHierarchy(tenantId, new AggregateId(organizationUid), positions);
                if (organization.getPositions() != null && organization.getPositions().size() > 0) {
                    removeDeletedPositions(organization);
                    positions = organization.getPositions();
                    Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                    List<DefaultPositionResource> resourceList = new ArrayList<DefaultPositionResource>(positionResources);
                    Collections.sort(resourceList);
                    return new ResponseEntity<List<DefaultPositionResource>>(resourceList, HttpStatus.ACCEPTED);
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/position/hierarchy", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DefaultPositionResource>> restructurePositionHierarchy(HttpServletRequest request, @RequestBody DefaultPositionStructureResource positionStructure) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            try {
                DefaultOrganization organization = organizationService.restructurePositionHierarchy(tenantId, new AggregateId(positionStructure.getOrganizationUid()), new EntityId(positionStructure.getParentUid()), new EntityId(positionStructure.getUid()));
                if(organization != null) {
                    removeDeletedPositions(organization);
                    Set<DefaultPosition> positions = organization.getPositions();
                    if (positions != null && positions.size() > 0) {
                        Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                        List<DefaultPositionResource> resourceList = new ArrayList<DefaultPositionResource>(positionResources);
                        Collections.sort(resourceList);
                        return new ResponseEntity<List<DefaultPositionResource>>(resourceList, HttpStatus.OK);
                    } else {
                        return new ResponseEntity(HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationResource>> getOrganizations() {
        List<DefaultOrganization> organizations = organizationService.findAll();
        if (organizations != null && organizations.size() > 0) {
            Iterator<DefaultOrganization> iterator = organizations.iterator();
            while(iterator.hasNext()) {
                DefaultOrganization organization = iterator.next();
                removeDeletedOrganizationUnits(organization);
            }
            Set<DefaultOrganizationResource> resources = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResources(organizations, DefaultOrganizationResource.class);
            return new ResponseEntity<Set<DefaultOrganizationResource>>(resources, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/positions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DefaultPositionResource>> getPositions(HttpServletRequest request, @PathVariable("organizationUID") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = organizationService.findOne(tenantId, new AggregateId(aggregateId));
            if(organization != null) {
                removeDeletedPositions(organization);
                Set<DefaultPosition> positions = organization.getPositions();
                if (positions != null && positions.size() > 0) {
                    Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                    List<DefaultPositionResource> resourceList = new ArrayList<DefaultPositionResource>(positionResources);
                    Collections.sort(resourceList);
                    return new ResponseEntity<List<DefaultPositionResource>>(resourceList, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnits/positions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationUnitPositions> getOrganizationUnitPositions(HttpServletRequest request, @PathVariable("organizationUID") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            HashMap<String, List<String>> positionResources = new HashMap<>();
            Map<String, Set<DefaultPosition>> positions = organizationService.getOrganizationUnitPositions(tenantId, new AggregateId(aggregateId));
            if (positions != null && positions.size() > 0) {
                positions.entrySet().stream().forEach(p -> {
                    List<String> positionsUid = new ArrayList<String>();
                    p.getValue().forEach(position -> {
                        positionsUid.add(position.getEntityId().getId());
                    });
                    positionResources.put(p.getKey(), positionsUid);
                });
                DefaultOrganizationUnitPositions organizationUnitPositions = new DefaultOrganizationUnitPositions();
                organizationUnitPositions.setOrgUnitPositions(positionResources);
                return new ResponseEntity<DefaultOrganizationUnitPositions>(organizationUnitPositions, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnit/positions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationUnitPositions> getOrganizationUnitsPositions(HttpServletRequest request, @PathVariable("organizationUID") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            HashMap<String, List<Map<String, String>>> organizationUnitPositions = new HashMap<String, List<Map<String, String>>>();
            Set<DefaultOrganizationUnit> organizationUnits = organizationService.getOrganizationUnits(tenantId, new AggregateId(aggregateId));
            if (organizationUnits != null && organizationUnits.size() > 0) {
                organizationUnits.stream().forEach(o -> {
                    Set<DefaultPosition> positions = o.getPositions();
                    String parentUid = "0";
                    if(positions != null && positions.size() > 0) {
                        List<Map<String, String>> positionList = new ArrayList<Map<String, String>>();
                        positions.stream().forEach(pos -> {
                            Map<String, String> map = new HashMap<>();
                            map.put("uid", pos.getEntityId().getId());
                            map.put("name", pos.getName());
                            map.put("code", pos.getCode());
                            map.put("positionType", pos.getPositionType().getAbbreviation());
                            positionList.add(map);
                        });
                        parentUid = o.getParent() != null ? o.getParent().getEntityId().getId() : "0";
                        organizationUnitPositions.put("uid:"+o.getEntityId().getId()+";name:"+o.getName()+";parent:"+parentUid, positionList);
                    } else {
                        parentUid = o.getParent() != null ? o.getParent().getEntityId().getId() : "0";
                        List<Map<String, String>> positionList = new ArrayList<Map<String, String>>();
                        organizationUnitPositions.put("uid:"+o.getEntityId().getId()+";name:"+o.getName()+";parent:"+parentUid, positionList);
                    }
                });
                DefaultOrganizationUnitPositions organizationUnitPositions1 = new DefaultOrganizationUnitPositions();
                organizationUnitPositions1.setOrganizationUnitPositions(organizationUnitPositions);
                return new ResponseEntity<DefaultOrganizationUnitPositions>(organizationUnitPositions1, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnits/search/{searchTerm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> searchOrganizationUnits(HttpServletRequest request, @PathVariable("organizationUID") String organizationAggregateId, @PathVariable("searchTerm") String searchTerm) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            searchTerm = "(.*)" + searchTerm.toLowerCase() + "(.*)";
            Set<DefaultOrganizationUnit> organizationUnits = organizationService.searchOrganizationUnits(tenantId, new AggregateId(organizationAggregateId), searchTerm);
            if (organizationUnits != null && organizationUnits.size() > 0) {
                Set<DefaultOrganizationUnitResource> organizationUnitResources = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResources(organizationUnits, DefaultOrganizationUnitResource.class);
                return new ResponseEntity<Set<DefaultOrganizationUnitResource>>(organizationUnitResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/positions/search/{searchTerm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPositionResource>> searchPositions(HttpServletRequest request, @PathVariable("organizationUID") String organizationAggregateId, @PathVariable("searchTerm") String searchTerm) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            searchTerm = "(.*)" + searchTerm.toLowerCase() + "(.*)";
            Set<DefaultPosition> positions = organizationService.searchPositions(tenantId, new AggregateId(organizationAggregateId), searchTerm);
            if (positions != null && positions.size() > 0) {
                Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                return new ResponseEntity<Set<DefaultPositionResource>>(positionResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/position", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addPosition(HttpServletRequest request, @Valid @RequestBody DefaultPositionResource positionResource, @PathVariable("organizationUID") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultPosition position = assemblerResolver.resolveEntityAssembler(DefaultPosition.class, DefaultPositionResource.class).toEntity(positionResource, DefaultPosition.class);
            DefaultOrganization organization = null;
            try {
                organization = organizationService.addPosition(tenantId, new AggregateId(aggregateId), position);
            } catch (DataIntegrityViolationException dive) {
                if(dive.getRootCause().getMessage().contains("Duplicate entry")) {
                    throw new X1Exception("Duplicate Entry '" + positionResource.getCode() + "' for Code", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch(Exception e) {
                throw new X1Exception(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/position/{parentPositionUid}/below", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addPositionBelow(HttpServletRequest request, @RequestBody DefaultPositionResource positionResource, @PathVariable("parentPositionUid") String parentPositionEntityId, @PathVariable("organizationUid") String organizationUid) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultPosition position = assemblerResolver.resolveEntityAssembler(DefaultPosition.class, DefaultPositionResource.class).toEntity(positionResource, DefaultPosition.class);
            DefaultOrganization organization = organizationService.addPositionBelow(tenantId, position, new EntityId(parentPositionEntityId), new AggregateId(organizationUid));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/position/{positionUID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPositionResource> getPosition(HttpServletRequest request, @PathVariable("organizationUID") String aggregateId, @PathVariable("positionUID") String entityId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultPosition position = organizationService.getPosition(tenantId, new AggregateId(aggregateId), new EntityId(entityId));
            if (position != null) {
                DefaultPositionResource positionResource = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResource(position, DefaultPositionResource.class);
                return new ResponseEntity<DefaultPositionResource>(positionResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/position/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionType[]> getPositionTypes(HttpServletRequest request) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            PositionType positionTypes[] = organizationService.getPositionTypes(tenantId);
            if (positionTypes != null) {
                return new ResponseEntity<PositionType[]>(positionTypes, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnit/{organizationUnitUID}/position", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addPositionToOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUID") AggregateId organizationAggregateId, @PathVariable("organizationUnitUID") EntityId organizationUnitEntityId, @RequestBody List<String> positions) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = organizationService.addPositionToOrganizationUnit(tenantId, organizationAggregateId, organizationUnitEntityId, new HashSet<>(positions));
            if (organization != null) {
                DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnits/{organizationUnitUID}/position", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> updatePositionsOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUID") AggregateId organizationAggregateId, @PathVariable("organizationUnitUID") EntityId organizationUnitEntityId, @RequestBody List<String> positions) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = organizationService.updatePositionsOfOrganizationUnit(tenantId, organizationAggregateId, organizationUnitEntityId, new HashSet<>(positions));
            if (organization != null) {
                DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnits/{organizationUnitUID}/position/remove", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> removePositionsOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUID") AggregateId organizationAggregateId, @PathVariable("organizationUnitUID") EntityId organizationUnitEntityId, @RequestBody List<String> positions) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = organizationService.removePositionsOfOrganizationUnit(tenantId, organizationAggregateId, organizationUnitEntityId, new HashSet<>(positions));
            if (organization != null) {
                DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnit/{organizationUnitUID}/positions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPositionResource>> getPositionsOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUID") AggregateId organizationAggregateId, @PathVariable("organizationUnitUID") EntityId organizationUnitEntityId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultPosition> positions = organizationService.getPositionsOfOrganizationUnit(tenantId, organizationAggregateId, organizationUnitEntityId);
            if (positions != null && positions.size() > 0) {
                Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                return new ResponseEntity<Set<DefaultPositionResource>>(positionResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUID}/position/{positionUID}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> updatePosition(HttpServletRequest request, @Valid @RequestBody DefaultPositionResource positionResource, @PathVariable("organizationUID") String aggregateId, @PathVariable("positionUID") String entityId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultPosition position = assemblerResolver.resolveEntityAssembler(DefaultPosition.class, DefaultPositionResource.class).toEntity(positionResource, DefaultPosition.class);
            DefaultOrganization organization = null;
            try {
                organization = organizationService.updatePosition(tenantId, new AggregateId(aggregateId), position);
            } catch (DataIntegrityViolationException dive) {
                if(dive.getRootCause().getMessage().contains("Duplicate entry")) {
                    throw new X1Exception("Duplicate Entry '" + positionResource.getCode() + "' for Code", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                throw new X1Exception(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            return new ResponseEntity<Boolean>(organizationService.delete(tenantId, new AggregateId(organizationUid)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnit/{organizationUnitUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @PathVariable("organizationUnitUid") String organizationUnitEntityId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            organizationService.deleteOrganizationUnit(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId));
            return new ResponseEntity<Boolean>(organizationService.organizationUnitStatus(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }


    @RequestMapping(value = "/organization/{organizationUid}/basecurrency", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addBaseCurrency(HttpServletRequest request, @Valid @RequestBody DefaultCurrencyResource baseCurrency, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultCurrency defaultCurrency = assemblerResolver.resolveEntityAssembler(DefaultCurrency.class, DefaultCurrencyResource.class).toEntity(baseCurrency, DefaultCurrency.class);
            DefaultOrganization organization = organizationService.addBaseCurrency(tenantId, defaultCurrency, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/organization/{organizationUid}/timezone", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addTimezone(HttpServletRequest request, @Valid @RequestBody DefaultTimezoneResource timezone, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultTimezone defaultTimezone = assemblerResolver.resolveEntityAssembler(DefaultTimezone.class, DefaultTimezoneResource.class).toEntity(timezone, DefaultTimezone.class);
            DefaultOrganization organization = organizationService.addTimezone(tenantId, defaultTimezone, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/organizationunit/{parentOrganizationUnitUid}/below", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnitBelow(HttpServletRequest request, @Valid @RequestBody DefaultOrganizationUnitResource organizationUnit, @PathVariable("parentOrganizationUnitUid") String parentOrganizationUnitUid, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganizationUnit organizationUnit1 = assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class).toEntity(organizationUnit, DefaultOrganizationUnit.class);
            DefaultOrganization organization = organizationService.addOrganizationUnitBelow(tenantId, organizationUnit1, new EntityId(parentOrganizationUnitUid), new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/organizationUnit/restructure", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> restructureOrganizationUnit(HttpServletRequest request, @RequestBody DefaultOrganizationUnitRestructureResource restructureResource) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = organizationService.restructureOrganizationUnit(tenantId, new AggregateId(restructureResource.getOrganizationUid()), new EntityId(restructureResource.getOrganizationUnitUid()), restructureResource.getParentUid() != null ? new EntityId(restructureResource.getParentUid()) : null);
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/contactperson", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addContactPerson(HttpServletRequest request, @Valid @RequestBody DefaultPersonResource person, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultPerson defaultPerson = assemblerResolver.resolveEntityAssembler(DefaultPerson.class, DefaultPersonResource.class).toEntity(person, DefaultPerson.class);
            DefaultOrganization organization = organizationService.addContactPerson(tenantId, defaultPerson, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/organization/{organizationUid}/contactperson/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactPerson(HttpServletRequest request, @Valid @RequestBody DefaultPersonResource person, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultPerson defaultPerson = assemblerResolver.resolveEntityAssembler(DefaultPerson.class, DefaultPersonResource.class).toEntity(person, DefaultPerson.class);
            DefaultOrganization organization = organizationService.setDefaultContactPerson(tenantId, defaultPerson, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/contactnumber", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addContactNumber(HttpServletRequest request, @Valid @RequestBody DefaultPhoneResource phone, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultPhone defaultPhone = assemblerResolver.resolveEntityAssembler(DefaultPhone.class, DefaultPhoneResource.class).toEntity(phone, DefaultPhone.class);
            DefaultOrganization organization = organizationService.addContactNumber(tenantId, defaultPhone, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/contactnumber/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactNumber(HttpServletRequest request, @Valid @RequestBody DefaultPhoneResource phone, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultPhone defaultPhone = assemblerResolver.resolveEntityAssembler(DefaultPhone.class, DefaultPhoneResource.class).toEntity(phone, DefaultPhone.class);
            DefaultOrganization organization = organizationService.setDefaultContactNumber(tenantId, defaultPhone, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/organization/{organizationUid}/email", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addEmail(HttpServletRequest request, @Valid @RequestBody DefaultEmailResource email, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultEmail defaultEmail = assemblerResolver.resolveEntityAssembler(DefaultEmail.class, DefaultEmailResource.class).toEntity(email, DefaultEmail.class);
            DefaultOrganization organization = organizationService.addEmail(tenantId, defaultEmail, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/email/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultEmail(HttpServletRequest request, @Valid @RequestBody DefaultEmailResource email, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultEmail defaultEmail = assemblerResolver.resolveEntityAssembler(DefaultEmail.class, DefaultEmailResource.class).toEntity(email, DefaultEmail.class);
            DefaultOrganization organization = organizationService.setDefaultEmail(tenantId, defaultEmail, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/headoffice", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setHeadOffice(HttpServletRequest request, @Valid @RequestBody DefaultAddressResource address, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
            DefaultOrganization organization = organizationService.setHeadOffice(tenantId, defaultAddress, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/organization/{organizationUid}/headoffice/to", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> moveHeadOfficeTo(HttpServletRequest request, @Valid @RequestBody DefaultAddressResource address, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
            DefaultOrganization organization = organizationService.moveHeadOfficeTo(tenantId, defaultAddress, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/headoffice/address", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultAddressResource> getHeadOfficeAddress(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultAddress address = organizationService.getHeadOfficeAddress(tenantId, new AggregateId(aggregateId));
            if (address != null) {
                DefaultAddressResource addressResource = assemblerResolver.resolveResourceAssembler(DefaultAddressResource.class, DefaultAddress.class).toResource(address, DefaultAddressResource.class);
                return new ResponseEntity<DefaultAddressResource>(addressResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/currencies/base", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultCurrencyResource>> getBaseCurrencies(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultCurrency> currencies = organizationService.getBaseCurrencies(tenantId, new AggregateId(aggregateId));
            if (currencies != null && currencies.size() > 0) {
                Set<DefaultCurrencyResource> currencyResources = assemblerResolver.resolveResourceAssembler(DefaultCurrencyResource.class, DefaultCurrency.class).toResources(currencies, DefaultCurrencyResource.class);
                return new ResponseEntity<Set<DefaultCurrencyResource>>(currencyResources, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/organization/{organizationUid}/organizationunits", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> getOrganizationUnits(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultOrganizationUnit> organizationUnits = organizationService.getOrganizationUnits(tenantId, new AggregateId(aggregateId));
            if (organizationUnits != null && organizationUnits.size() > 0) {
                Set<DefaultOrganizationUnitResource> organizationUnitResources = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResources(organizationUnits, DefaultOrganizationUnitResource.class);
                return new ResponseEntity<Set<DefaultOrganizationUnitResource>>(organizationUnitResources, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/contactnumbers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPhoneResource>> getContactNumbers(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultPhone> phones = organizationService.getContactNumbers(tenantId, new AggregateId(aggregateId));
            if (phones != null && phones.size() > 0) {
                Set<DefaultPhoneResource> phoneResources = assemblerResolver.resolveResourceAssembler(DefaultPhoneResource.class, DefaultPhone.class).toResources(phones, DefaultPhoneResource.class);
                return new ResponseEntity<Set<DefaultPhoneResource>>(phoneResources, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/organization/{organizationUid}/emails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultEmailResource>> getEmails(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            Set<DefaultEmail> emails = organizationService.getEmails(tenantId, new AggregateId(aggregateId));
            if (emails != null && emails.size() > 0) {
                Set<DefaultEmailResource> emailResources = assemblerResolver.resolveResourceAssembler(DefaultEmailResource.class, DefaultEmail.class).toResources(emails, DefaultEmailResource.class);
                return new ResponseEntity<Set<DefaultEmailResource>>(emailResources, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/organizationUnits/restructure", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> restructureOrganizationUnits(HttpServletRequest request, @RequestBody DefaultOrganizationResource organizationResource) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = null;
            try {
                organization = assemblerResolver.resolveEntityAssembler(DefaultOrganization.class, DefaultOrganizationResource.class).toEntity(organizationResource, DefaultOrganization.class);
                organization = organizationService.restructureOrganizationUnits(tenantId, organization.getAggregateId(), organization.getOrganizationUnits());
                if (organization != null) {
                    organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                    return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.ACCEPTED);
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/organization/{organizationUid}/positions/restructure", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> restructureOrganizationUnitsPositions(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @RequestBody DefaultPositionRestructureResource positionStructure) {
        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultOrganization organization = null;
            try {
                organization = organizationService.restructureOrganizationUnitsPositions(tenantId, new AggregateId(organizationUid), positionStructure.getPositionStructure());
                if (organization != null) {
                    DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                    return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.ACCEPTED);
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private DefaultOrganization removeDeletedOrganizationUnits(DefaultOrganization organization) {
        Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
        if(organizationUnits != null && organizationUnits.size() > 0) {
            Iterator<DefaultOrganizationUnit> iterator1 = organizationUnits.iterator();
            while(iterator1.hasNext()) {
                DefaultOrganizationUnit organizationUnit = iterator1.next();
                if(organizationUnit.isPassive()) {
                    iterator1.remove();
                } else if(!organizationUnit.isPassive() && organizationUnit.getParent() != null) {
                    iterator1.remove();
                }
            }
            Iterator<DefaultOrganizationUnit> iterator = organizationUnits.iterator();
            while(iterator.hasNext()) {
                DefaultOrganizationUnit organizationUnit = iterator.next();
                Set<DefaultOrganizationUnit> children = organizationUnit.getChildren();
                if(children != null && children.size() > 0) {
                    Iterator<DefaultOrganizationUnit> childIterator = children.iterator();
                    while(childIterator.hasNext()) {
                        DefaultOrganizationUnit childOrganizationUnit = childIterator.next();
                        if(childOrganizationUnit.isPassive()) {
                            childIterator.remove();
                        } else {
                            removeDeletedOrganizationUnits(childOrganizationUnit);
                        }
                    }
                }
            }
        }
        return organization;
    }

    private void removeDeletedOrganizationUnits(DefaultOrganizationUnit organizationUnit) {
        Set<DefaultOrganizationUnit> children = organizationUnit.getChildren();
        if(children != null && children.size() > 0) {
            Iterator<DefaultOrganizationUnit> childIterator = children.iterator();
            while (childIterator.hasNext()) {
                DefaultOrganizationUnit childOrganizationUnit = childIterator.next();
                if (childOrganizationUnit.isPassive()) {
                    childIterator.remove();
                } else {
                    removeDeletedOrganizationUnits(childOrganizationUnit);
                }
            }
        }
    }

    private DefaultOrganization removeDeletedPositions(DefaultOrganization organization) {
        Set<DefaultPosition> positions = organization.getPositions();
        if(positions != null && positions.size() > 0) {
            Iterator<DefaultPosition> iterator1 = positions.iterator();
            while(iterator1.hasNext()) {
                DefaultPosition position = iterator1.next();
                if(position.isPassive()) {
                    iterator1.remove();
                } else if(!position.isPassive() && position.getParent() != null) {
                    iterator1.remove();
                }
            }
            Iterator<DefaultPosition> iterator = positions.iterator();
            while(iterator.hasNext()) {
                DefaultPosition position = iterator.next();
                Set<DefaultPosition> children = position.getChildren();
                if(children != null && children.size() > 0) {
                    Iterator<DefaultPosition> childIterator = children.iterator();
                    while(childIterator.hasNext()) {
                        DefaultPosition childPosition = childIterator.next();
                        if(childPosition.isPassive()) {
                            childIterator.remove();
                        } else {
                            removeDeletedPositions(childPosition);
                        }
                    }
                }
            }
        }
        return organization;
    }

    private void removeDeletedPositions(DefaultPosition position) {
        Set<DefaultPosition> children = position.getChildren();
        if(children != null && children.size() > 0) {
            Iterator<DefaultPosition> childIterator = children.iterator();
            while (childIterator.hasNext()) {
                DefaultPosition childPosition = childIterator.next();
                if (childPosition.isPassive()) {
                    childIterator.remove();
                } else {
                    removeDeletedPositions(childPosition);
                }
            }
        }
    }
}
