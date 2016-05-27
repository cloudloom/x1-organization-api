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

    /**
     * Create Organization If Valid DefaultOrganizationResource Is Sent
     * @param organizationResource
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> createOrganization(@Valid @RequestBody DefaultOrganizationResource organizationResource) {
        //Map DefaultOrganizationResource To DefaultOrganization Entity
        DefaultOrganization organization = assemblerResolver.resolveEntityAssembler(DefaultOrganization.class, DefaultOrganizationResource.class).toEntity(organizationResource, DefaultOrganization.class);
        try {
            //save organization
            organization = organizationService.save(organization);
        } catch (DataIntegrityViolationException dive) {
            throw new X1Exception("Organization With Name : " + organizationResource.getName() + " Might Have Already Been Taken.", HttpStatus.CONFLICT);
        }
        //if organization is saved successfully
        if (organization != null) {
            //Map DefaultOrganization Entity To DefaultOrganizationResource
            organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
            //return DefaultOrganziationResource
            return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add OrganizationUnit
     * @param request
     * @param organizationUid
     * @param organizationUnitResource
     * @return
     */
    @RequestMapping(value = "/organization/{organizationUid}/organizationunit", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationUnitResource> addOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @Valid @RequestBody DefaultOrganizationUnitResource organizationUnitResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultOrganizationResource To DefaultOrganization Entity
            DefaultOrganizationUnit organizationUnit = assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class).toEntity(organizationUnitResource, DefaultOrganizationUnit.class);
            DefaultOrganization organization = null;
            try {
                //add OrganizationUnit To Organization
                organization = organizationService.addOrganizationUnit(tenantId, organizationUnit, new AggregateId(organizationUid));
                //cross validation : get DefaultOrganizationUnitResource By Name
                DefaultOrganizationUnit organizationUnit1 = organizationService.getOrganizationUnitByName(tenantId, new AggregateId(organizationUid), organizationUnit.getName());
                //if organizationUnit is fetched by name, else raise an exception
                if(organizationUnit1 != null) {
                    //Map DefaultOrganization Entity To DefaultOrganizationResource
                    DefaultOrganizationUnitResource organizationUnitResource1 = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResource(organizationUnit1, DefaultOrganizationUnitResource.class);
                    return new ResponseEntity<DefaultOrganizationUnitResource>(organizationUnitResource1, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_MODIFIED);
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception("Organization Unit With Name : " + organizationUnit.getName() + " Might Have Already Been Taken.", HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Update Organization Unit
     * @param request
     * @param organizationUid
     * @param organizationUnitResource
     * @return DefaultOrganizationUnitResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/organizationunit/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationUnitResource> updateOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @Valid @RequestBody DefaultOrganizationUnitResource organizationUnitResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultOrganizationUnitResource to DefaultOrganizationUnit Entity
            DefaultOrganizationUnit organizationUnit = assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class).toEntity(organizationUnitResource, DefaultOrganizationUnit.class);
            DefaultOrganization organization = null;
            try {
                //update organizationUnit
                organization = organizationService.updateOrganizationUnit(tenantId, organizationUnit, new AggregateId(organizationUid));
                //cross validation : get DefaultOrganizationUnitResource By Name
                DefaultOrganizationUnit organizationUnit1 = organizationService.getOrganizationUnitByName(tenantId, new AggregateId(organizationUid), organizationUnit.getName());
                //if organizationUnit is fetched by name, else raise an exception
                if(organizationUnit1 != null) {
                    //Map DefaultOrganization Entity To DefaultOrganizationResource
                    DefaultOrganizationUnitResource organizationUnitResource1 = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResource(organizationUnit1, DefaultOrganizationUnitResource.class);
                    return new ResponseEntity<DefaultOrganizationUnitResource>(organizationUnitResource1, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_MODIFIED);
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception("Organization Unit With Name : " + organizationUnit.getName() + " Might Have Already Been Taken.", HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get OrganizationUnit By Uid
     * @param request
     * @param organizationUid
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> getOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //find organization unit by uid
            DefaultOrganization organization = organizationService.findOne(tenantId, new AggregateId(organizationUid));
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                //Map DefaultOrganization Entity To DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get OrganizationUnits Unstructured (Get All Organization Units With No Parent Child Relationship)
     * @param request
     * @param organizationUid
     * @return Set<DefaultOrganizationUnitResource>
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnits/unstructured", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> getOrganizationUnitsUnstructured(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null
        if (tenantId != null) {
            //fetch organization by uid
            DefaultOrganization organization = organizationService.findOne(tenantId, new AggregateId(organizationUid));
            //get organizations organizationUnits
            Set<DefaultOrganizationUnit> units = organization.getOrganizationUnits();
            //if organizationUnits is not null and size > 0
            if(units != null && units.size() >0) {
                //organizationUnits iterator
                Iterator<DefaultOrganizationUnit> iterator = units.iterator();
                while(iterator.hasNext()) {
                    DefaultOrganizationUnit unit = iterator.next();
                    //check if organizationUnit is deleted, if yes then remove it
                    if(unit.isPassive()) {
                        iterator.remove();
                    }
                }
                //Map DefaultOrganization Entities To DefaultOrganizationResources
                Set<DefaultOrganizationUnitResource> organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResources(units, DefaultOrganizationUnitResource.class);
                //if organization is not null return, else raise an exception
                if (organization != null) {
                    return new ResponseEntity<Set<DefaultOrganizationUnitResource>>(organizationResource, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        //If reached here, there was some problem with the request, send http status 400
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    /**
     * Add Departments To Organization
     * @param request
     * @param organizationAggregateId
     * @param departments
     * @return Set<DefaultDepartmentResource>
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/departments", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultDepartmentResource>> addDepartmentToOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @Valid @RequestBody DefaultDepartmentResources departments) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultDepartmentResources to DefaultDepartment Entities
            Set<DefaultDepartment> defaultDepartments = assemblerResolver.resolveEntityAssembler(DefaultDepartment.class, DefaultDepartmentResource.class).toEntities(departments.getDepartments(), DefaultDepartment.class);
            DefaultOrganization organization = null;
            try {
                //add department to organization
                organization = organizationService.addDepartmentToOrganization(tenantId, new AggregateId(organizationAggregateId), defaultDepartments);
                //list of departmentNames
                List<String> departmentNames = new ArrayList<String>();
                HashSet<DefaultDepartmentResource> defaultDepartmentResources = departments.getDepartments();
                //stream defaultDepartmentResources and for each get department name and add it to departmentNames
                defaultDepartmentResources.stream().forEach(defaultDepartmentResource -> departmentNames.add(defaultDepartmentResource.getName()));
                //getOrganizationsDepartments By Name
                Set<DefaultDepartment> fetchedDepartments = organizationService.getOrganizationDepartmentsByName(tenantId, new AggregateId(organizationAggregateId), departmentNames);
                //if fetchedDepartments is not null and size > 0, else raise an exception
                if(fetchedDepartments != null && fetchedDepartments.size() > 0) {
                    //Map DefaultDepartment Entities To DefaultDepartmentResources
                    Set<DefaultDepartmentResource> departmentResources = assemblerResolver.resolveResourceAssembler(DefaultDepartmentResource.class, DefaultDepartment.class).toResources(fetchedDepartments, DefaultDepartmentResource.class);
                    //return DepartmentResources
                    return new ResponseEntity<Set<DefaultDepartmentResource>>(departmentResources, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_MODIFIED);
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception("Department Name Is Unique, Check If Duplicate Department Name Is Specified.", HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Update Departments Of Organization
     * @param request
     * @param organizationAggregateId
     * @param departments
     * @return Set<DefaultDepartmentResource>
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/departments/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultDepartmentResource>> updateDepartmentOfOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @Valid @RequestBody DefaultDepartmentResources departments) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null
        if (tenantId != null) {
            //Map DefaultDepartmentResources to DefaultDepartment Entities
            Set<DefaultDepartment> defaultDepartments = assemblerResolver.resolveEntityAssembler(DefaultDepartment.class, DefaultDepartmentResource.class).toEntities(departments.getDepartments(), DefaultDepartment.class);
            DefaultOrganization organization = null;
            try {
                //update Departments of Organization
                organization = organizationService.updateDepartmentOfOrganization(tenantId, new AggregateId(organizationAggregateId), defaultDepartments);
                //departmentNames
                List<String> departmentNames = new ArrayList<String>();
                //all incoming departments
                HashSet<DefaultDepartmentResource> defaultDepartmentResources = departments.getDepartments();
                //stream and forEach incoming department put their names in departmentNames
                defaultDepartmentResources.stream().forEach(defaultDepartmentResource -> departmentNames.add(defaultDepartmentResource.getName()));
                //fetch departmentsByName
                Set<DefaultDepartment> fetchedDepartments = organizationService.getOrganizationDepartmentsByName(tenantId, new AggregateId(organizationAggregateId), departmentNames);
                //if fetchedDepartments is not null and fetchedDepartments size > 0
                if(fetchedDepartments != null && fetchedDepartments.size() > 0) {
                    //Map DefaultDepartment Entities to DefaultDepartmentResources
                    Set<DefaultDepartmentResource> departmentResources = assemblerResolver.resolveResourceAssembler(DefaultDepartmentResource.class, DefaultDepartment.class).toResources(fetchedDepartments, DefaultDepartmentResource.class);
                    //return all updated departments
                    return new ResponseEntity<Set<DefaultDepartmentResource>>(departmentResources, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_MODIFIED);
                }
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception("Department Name Is Unique, Check If Duplicate Department Name Is Specified.", HttpStatus.CONFLICT);            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Departments Of Organziation
     * @param request
     * @param organizationAggregateId
     * @return
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/departments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultDepartmentResource>> getDepartmentsOfOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get departments of organization by uid
            Set<DefaultDepartment> departments = organizationService.getDepartmentsOfOrganization(tenantId, new AggregateId(organizationAggregateId));
            //if departments is not null and size > 0
            if (departments != null && departments.size() > 0) {
                //Map DefaultDepartment Entities To DefaultDepartment Resources
                Set<DefaultDepartmentResource> departmentResources = assemblerResolver.resolveResourceAssembler(DefaultDepartmentResource.class, DefaultDepartment.class).toResources(departments, DefaultDepartmentResource.class);
                //return all fetched departments
                return new ResponseEntity<Set<DefaultDepartmentResource>>(departmentResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Department To OrganizationUnit
     * @param request
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param departments
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/departments", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addDepartmentToOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @PathVariable("organizationUnitUid") String organizationUnitEntityId, @RequestBody HashSet<String> departments) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            DefaultOrganization organization = null;
            try {
                //add department to organization
                organization = organizationService.addDepartmentToOrganizationUnit(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId), departments);
                //remove deleted organization units
                removeDeletedOrganizationUnits(organization);
            } catch (DataIntegrityViolationException dive) {
                //department name is unique, if duplicate found throw an exception
                throw new X1Exception("Department Name Is Unique, Check If Duplicate Department Name Is Specified.", HttpStatus.CONFLICT);            }
            DefaultOrganizationResource organizationResource = null;
            //if organization not null
            if (organization != null) {
                //Map DefaultOrganization Entity To DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return DefaultOrganizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Update Department Of OrganizationUnit
     * @param request
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param departments
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/departments/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> updateDepartmentOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @PathVariable("organizationUnitUid") String organizationUnitEntityId, @RequestBody HashSet<String> departments) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            DefaultOrganization organization = null;
            try {
                //update department of organization unit
                organization = organizationService.updateDepartmentOfOrganizationUnit(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId), departments);
                //remove deleted organization units
                removeDeletedOrganizationUnits(organization);
            } catch (DataIntegrityViolationException dive) {
                //department name is unique,if found duplicate throw an exception
                throw new X1Exception("Department Name Is Unique, Check If Duplicate Department Name Is Specified.", HttpStatus.CONFLICT);            }
            DefaultOrganizationResource organizationResource = null;
            if (organization != null) {
                //Map DefaultOrganization Entity To DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get Departments Of OrganizationUnit
     * @param request
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @return Set<DefaultDepartmentResource>
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/departments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultDepartmentResource>> getDepartmentsOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @PathVariable("organizationUnitUid") String organizationUnitEntityId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null
        if (tenantId != null) {
            //get departments of organizationUnit
            Set<DefaultDepartment> departments = organizationService.getDepartmentsOfOrganizationUnit(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId));
            //if departments is not null and size > 0
            if (departments != null && departments.size() > 0) {
                //Map DefaultDepartment Entities To Default Department Resources
                Set<DefaultDepartmentResource> departmentResources = assemblerResolver.resolveResourceAssembler(DefaultDepartmentResource.class, DefaultDepartment.class).toResources(departments, DefaultDepartmentResource.class);
                //return departmentResources
                return new ResponseEntity<Set<DefaultDepartmentResource>>(departmentResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Organization Name Details By UIDS
     * Pass Either Organization UIDS | OrganizationUnit UIDS | Department UIDS | Position UIDS
     * @param request
     * @param resource
     * @return DefaultOrganizationNameByIds
     * Names Of Respective Organization UIDS | OrganizationUnit UIDS | Department UIDS | Position UIDS
     * maps key contain UIDS and values contain Names
     */
    @Override
    @RequestMapping(value = "/organization/ids/names", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationNameByIds> getOrganizationNameDetailsByUIDS(HttpServletRequest request, @RequestBody DefaultOrganizationNameByIds resource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //if incoming resource is not null
            if(resource != null) {
                //get names by uids
                DefaultOrganizationNameByIds idNames = organizationService.getOrganizationNameDetailsByUIDS(tenantId, resource);
                //if not null, else raise an exception
                if (idNames != null) {
                    //return idNames
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
                if(organization != null) {
                    removeDeletedPositions(organization);
                    positions = organization.getPositions();
                    if (positions != null && positions.size() > 0) {
                        Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                        List<DefaultPositionResource> resourceList = new ArrayList<DefaultPositionResource>(positionResources);
                        Collections.sort(resourceList);
                        return new ResponseEntity<List<DefaultPositionResource>>(resourceList, HttpStatus.OK);
                    } else {
                        return new ResponseEntity(HttpStatus.NOT_FOUND);
                    }
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
                organization = organizationService.findOne(tenantId, organization.getAggregateId());
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
            } catch (DataIntegrityViolationException dive) {
                throw new X1Exception(dive.getRootCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Positions
     * @return Set<DefaultOrganizationResource>
     */
    @RequestMapping(value = "/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationResource>> getOrganizations() {
        //get all organizations
        List<DefaultOrganization> organizations = organizationService.findAll();
        //if organizations is not null and size > 0
        if (organizations != null && organizations.size() > 0) {
            //organizations iterator
            Iterator<DefaultOrganization> iterator = organizations.iterator();
            //elements found
            while(iterator.hasNext()) {
                //get next element
                DefaultOrganization organization = iterator.next();
                //remove deleted organizationUnits
                removeDeletedOrganizationUnits(organization);
            }
            //Map DefaultOrganization Entities to DefaultOrganizationResources
            Set<DefaultOrganizationResource> resources = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResources(organizations, DefaultOrganizationResource.class);
            //return resources
            return new ResponseEntity<Set<DefaultOrganizationResource>>(resources, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * Get Positions Of Organization
     * @param request
     * @param aggregateId
     * @return List<DefaultPositionResource>
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/positions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DefaultPositionResource>> getPositions(HttpServletRequest request, @PathVariable("organizationUID") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //find organization by uid
            DefaultOrganization organization = organizationService.findOne(tenantId, new AggregateId(aggregateId));
            //if organization found, else raise an exception
            if(organization != null) {
                //remove deleted positions
                removeDeletedPositions(organization);
                //get all organization positions
                Set<DefaultPosition> positions = organization.getPositions();
                //if positions found, else raise an exception
                if (positions != null && positions.size() > 0) {
                    //Map DefaultPosition Entities to DefaultPositionResources
                    Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                    List<DefaultPositionResource> resourceList = new ArrayList<DefaultPositionResource>(positionResources);
                    //sort positions by name
                    Collections.sort(resourceList);
                    //return sorted positions
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

    /**
     * Get OrganizationUnit Positions
     * @param request
     * @param aggregateId
     * @return DefaultOrganizationUnitPositions
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnits/positions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationUnitPositions> getOrganizationUnitPositions(HttpServletRequest request, @PathVariable("organizationUID") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //track organizationUIDS and their Position UIDS
            //Map Key : organizationUIDS, Value : positionUIDS
            HashMap<String, List<String>> positionResources = new HashMap<>();
            //get organizationUnitPositions
            //Map Key : organizationUnit Uid, Value : organizationUnits Positions
            Map<String, Set<DefaultPosition>> positions = organizationService.getOrganizationUnitPositions(tenantId, new AggregateId(aggregateId));
            //if positions found, else raise an exception
            if (positions != null && positions.size() > 0) {
                //stream, forEach position
                positions.entrySet().stream().forEach(p -> {
                    //track all position Uids
                    List<String> positionsUid = new ArrayList<String>();
                    p.getValue().forEach(position -> {
                        positionsUid.add(position.getEntityId().getId());
                    });
                    //put organizationUnit UIDS and its positions UIDS in positionResources
                    positionResources.put(p.getKey(), positionsUid);
                });
                DefaultOrganizationUnitPositions organizationUnitPositions = new DefaultOrganizationUnitPositions();
                organizationUnitPositions.setOrgUnitPositions(positionResources);
                //return organizationUnitPositions
                return new ResponseEntity<DefaultOrganizationUnitPositions>(organizationUnitPositions, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Organization Units Positions
     * @param request
     * @param aggregateId
     * @return DefaultOrganizationUnitPositions
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnit/positions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationUnitPositions> getOrganizationUnitsPositions(HttpServletRequest request, @PathVariable("organizationUID") String aggregateId) {
        //tenantID
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //map key:organizationUnit Details Ex: uid:12345;name:orgUnit1";parent:1234556UID
            //map value: List Of Position Details Of The OrganizationUnit
            HashMap<String, List<Map<String, String>>> organizationUnitPositions = new HashMap<String, List<Map<String, String>>>();
            //get organizationUnits
            Set<DefaultOrganizationUnit> organizationUnits = organizationService.getOrganizationUnits(tenantId, new AggregateId(aggregateId));
            //if organiztionUnits found, else raise an exception
            if (organizationUnits != null && organizationUnits.size() > 0) {
                //stream and forEach organizationUnit
                organizationUnits.stream().forEach(o -> {
                    //get positions
                    Set<DefaultPosition> positions = o.getPositions();
                    //track organizationUnits parentUid
                    String parentUid = "0";
                    //if positions found
                    if(positions != null && positions.size() > 0) {
                        //variable to track position details
                        List<Map<String, String>> positionList = new ArrayList<Map<String, String>>();
                        //stream and forEach position
                        positions.stream().forEach(pos -> {
                            //map to track position detail
                            Map<String, String> map = new HashMap<>();
                            map.put("uid", pos.getEntityId().getId());
                            map.put("name", pos.getName());
                            map.put("code", pos.getCode());
                            map.put("positionType", pos.getPositionType().getAbbreviation());
                            //add position detail map to positionList
                            positionList.add(map);
                        });
                        //set organizationUnitsUid
                        //if organizationUnit has a parent then set the parentUID, else set the parent UID as Zero
                        parentUid = o.getParent() != null ? o.getParent().getEntityId().getId() : "0";
                        //put key : organizationUnit Details, value : List Of Position details
                        organizationUnitPositions.put("uid:"+o.getEntityId().getId()+";name:"+o.getName()+";parent:"+parentUid, positionList);
                    }
                    //if no positions found
                    else {
                        //set organizationUnitsUid
                        //if organizationUnit has a parent then set the parentUID, else set the parent UID as Zero
                        parentUid = o.getParent() != null ? o.getParent().getEntityId().getId() : "0";
                        List<Map<String, String>> positionList = new ArrayList<Map<String, String>>();
                        //put key : organizationUnit Details, value : Empty List Of Position details
                        organizationUnitPositions.put("uid:"+o.getEntityId().getId()+";name:"+o.getName()+";parent:"+parentUid, positionList);
                    }
                });
                DefaultOrganizationUnitPositions organizationUnitPositions1 = new DefaultOrganizationUnitPositions();
                organizationUnitPositions1.setOrganizationUnitPositions(organizationUnitPositions);
                //return organizationUnitPositions1
                return new ResponseEntity<DefaultOrganizationUnitPositions>(organizationUnitPositions1, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Search Organization Units By Search Term
     * @param request
     * @param organizationAggregateId
     * @param searchTerm
     * @return Set<DefaultOrganizationUnitResource>
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnits/search/{searchTerm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> searchOrganizationUnits(HttpServletRequest request, @PathVariable("organizationUID") String organizationAggregateId, @PathVariable("searchTerm") String searchTerm) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //lowercase searchTerm, append and prepend with (.*)
            searchTerm = "(.*)" + searchTerm.toLowerCase() + "(.*)";
            //search organization units
            Set<DefaultOrganizationUnit> organizationUnits = organizationService.searchOrganizationUnits(tenantId, new AggregateId(organizationAggregateId), searchTerm);
            //if organizationUnits found
            if (organizationUnits != null && organizationUnits.size() > 0) {
                //Map DefaultOrganization Entities to DefaultOrganizaitonResources
                Set<DefaultOrganizationUnitResource> organizationUnitResources = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResources(organizationUnits, DefaultOrganizationUnitResource.class);
                //return found organizationUnitResources
                return new ResponseEntity<Set<DefaultOrganizationUnitResource>>(organizationUnitResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Search Positions By Search Term
     * @param request
     * @param organizationAggregateId
     * @param searchTerm
     * @return Set<DefaultPositionResource>
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/positions/search/{searchTerm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPositionResource>> searchPositions(HttpServletRequest request, @PathVariable("organizationUID") String organizationAggregateId, @PathVariable("searchTerm") String searchTerm) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //lowercase searchTerm, append and prepend with (.*)
            searchTerm = "(.*)" + searchTerm.toLowerCase() + "(.*)";
            //searchPositions
            Set<DefaultPosition> positions = organizationService.searchPositions(tenantId, new AggregateId(organizationAggregateId), searchTerm);
            //if positions found
            if (positions != null && positions.size() > 0) {
                //Map DefaultPosition Entites To DefaultPositionResource
                Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                //return found positionResources
                return new ResponseEntity<Set<DefaultPositionResource>>(positionResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Position To Organization
     * @param request
     * @param positionResource
     * @param aggregateId
     * @return DefaultPositionResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/position", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPositionResource> addPosition(HttpServletRequest request, @Valid @RequestBody DefaultPositionResource positionResource, @PathVariable("organizationUID") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPositionResource to DefaultPosition Entity
            DefaultPosition position = assemblerResolver.resolveEntityAssembler(DefaultPosition.class, DefaultPositionResource.class).toEntity(positionResource, DefaultPosition.class);
            DefaultOrganization organization = null;
            try {
                //add position
                organization = organizationService.addPosition(tenantId, new AggregateId(aggregateId), position);
                //fetch position by name
                DefaultPosition defaultPosition = organizationService.getPositionByName(tenantId, new AggregateId(aggregateId), position.getName());
                //if position found, if not found send http status 304
                if(defaultPosition != null) {
                    //Map DefaultPosition Entity To DefaultPositionResource
                    DefaultPositionResource defaultPositionResource = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResource(defaultPosition, DefaultPositionResource.class);
                    //return defaultPositionResource
                    return new ResponseEntity<DefaultPositionResource>(defaultPositionResource, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_MODIFIED);
                }
            } catch (DataIntegrityViolationException dive) {
                //Position Name Is Unique, if duplicate name is given throw an exception
                throw new X1Exception("Position Name Is Unique, Check If Duplicate Position Name Is Specified.", HttpStatus.CONFLICT);
            } catch(Exception e) {
                throw new X1Exception(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Child Position To Parent Position
     * @param request
     * @param positionResource
     * @param parentPositionEntityId
     * @param organizationUid
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/position/{parentPositionUid}/below", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addPositionBelow(HttpServletRequest request, @RequestBody DefaultPositionResource positionResource, @PathVariable("parentPositionUid") String parentPositionEntityId, @PathVariable("organizationUid") String organizationUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPositionResource to DefaultPosition Entity
            DefaultPosition position = assemblerResolver.resolveEntityAssembler(DefaultPosition.class, DefaultPositionResource.class).toEntity(positionResource, DefaultPosition.class);
            //add child position to parent position
            DefaultOrganization organization = organizationService.addPositionBelow(tenantId, position, new EntityId(parentPositionEntityId), new AggregateId(organizationUid));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity To DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get Position Of Organization
     * @param request
     * @param aggregateId
     * @param entityId
     * @return DefaultPositionResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/position/{positionUID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPositionResource> getPosition(HttpServletRequest request, @PathVariable("organizationUID") String aggregateId, @PathVariable("positionUID") String entityId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //fetch position by organization uid and position uid
            DefaultPosition position = organizationService.getPosition(tenantId, new AggregateId(aggregateId), new EntityId(entityId));
            //if position found
            if (position != null) {
                //Map DefaultPosition Entity to DefaultPosition Resource
                DefaultPositionResource positionResource = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResource(position, DefaultPositionResource.class);
                //return found positionResource
                return new ResponseEntity<DefaultPositionResource>(positionResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get All PositionTypes
     * @param request
     * @return
     */
    @Override
    @RequestMapping(value = "/organization/position/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionType[]> getPositionTypes(HttpServletRequest request) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get all position types
            PositionType positionTypes[] = organizationService.getPositionTypes(tenantId);
            //if positionTypes found
            if (positionTypes != null) {
                // return all positionTypes
                return new ResponseEntity<PositionType[]>(positionTypes, HttpStatus.OK);
            }
            //return http status not found
            else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Position(s) To Organization Unit
     * @param request
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param positions
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnit/{organizationUnitUID}/position", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addPositionToOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUID") AggregateId organizationAggregateId, @PathVariable("organizationUnitUID") EntityId organizationUnitEntityId, @RequestBody List<String> positions) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //add position(s) to organizationUnit
            DefaultOrganization organization = organizationService.addPositionToOrganizationUnit(tenantId, organizationAggregateId, organizationUnitEntityId, new HashSet<>(positions));
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Update Position(s) Of Organization Unit
     * @param request
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param positions
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnits/{organizationUnitUID}/position", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> updatePositionsOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUID") AggregateId organizationAggregateId, @PathVariable("organizationUnitUID") EntityId organizationUnitEntityId, @RequestBody List<String> positions) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //update positions of organizationUnit
            DefaultOrganization organization = organizationService.updatePositionsOfOrganizationUnit(tenantId, organizationAggregateId, organizationUnitEntityId, new HashSet<>(positions));
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Remove Position(s) Of Organization Unit
     * @param request
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param positions
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnits/{organizationUnitUID}/position/remove", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> removePositionsOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUID") AggregateId organizationAggregateId, @PathVariable("organizationUnitUID") EntityId organizationUnitEntityId, @RequestBody List<String> positions) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //remove positions of organization unit
            DefaultOrganization organization = organizationService.removePositionsOfOrganizationUnit(tenantId, organizationAggregateId, organizationUnitEntityId, new HashSet<>(positions));
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganization Resource
                DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get Positions Of Organization Unit
     * @param request
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @return Set<DefaultPositionResource>
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/organizationUnit/{organizationUnitUID}/positions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPositionResource>> getPositionsOfOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUID") AggregateId organizationAggregateId, @PathVariable("organizationUnitUID") EntityId organizationUnitEntityId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get positions of organization unit
            Set<DefaultPosition> positions = organizationService.getPositionsOfOrganizationUnit(tenantId, organizationAggregateId, organizationUnitEntityId);
            //if positions found
            if (positions != null && positions.size() > 0) {
                //Map DefaultPosition Entities to DefaultPosition Resources
                Set<DefaultPositionResource> positionResources = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResources(positions, DefaultPositionResource.class);
                //return positionResources
                return new ResponseEntity<Set<DefaultPositionResource>>(positionResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Update Position Of Organization
     * @param request
     * @param positionResource
     * @param aggregateId
     * @param entityId
     * @return DefaultPositionResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUID}/position/{positionUID}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPositionResource> updatePosition(HttpServletRequest request, @Valid @RequestBody DefaultPositionResource positionResource, @PathVariable("organizationUID") String aggregateId, @PathVariable("positionUID") String entityId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPositionResource To DefaultPosition Entity
            DefaultPosition position = assemblerResolver.resolveEntityAssembler(DefaultPosition.class, DefaultPositionResource.class).toEntity(positionResource, DefaultPosition.class);
            DefaultOrganization organization = null;
            try {
                //update position of organization unit
                organization = organizationService.updatePosition(tenantId, new AggregateId(aggregateId), position);
                //get position by name
                DefaultPosition defaultPosition = organizationService.getPositionByName(tenantId, new AggregateId(aggregateId), position.getName());
                //if position found
                if(defaultPosition != null) {
                    //Map DefaultPosition Entity to DefaultPositionResource
                    DefaultPositionResource defaultPositionResource = assemblerResolver.resolveResourceAssembler(DefaultPositionResource.class, DefaultPosition.class).toResource(defaultPosition, DefaultPositionResource.class);
                    //return defaultPositionResource
                    return new ResponseEntity<DefaultPositionResource>(defaultPositionResource, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_MODIFIED);
                }
            } catch (DataIntegrityViolationException dive) {
                //Position name is unique, if duplicate name is given, throw an exception
                throw new X1Exception("Position Name Is Unique, Check If Duplicate Position Name Is Specified.", HttpStatus.CONFLICT);
            } catch (Exception e) {
                throw new X1Exception(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Delete Organization
     * @param request
     * @param organizationUid
     * @return Boolean
     */
    @RequestMapping(value = "/organization/{organizationUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null
        if (tenantId != null) {
            //delete organization by uid, return true if deleted else return false
            return new ResponseEntity<Boolean>(organizationService.delete(tenantId, new AggregateId(organizationUid)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Delete OrganizationUnit Of Organization
     * @param request
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @return Boolean
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/organizationUnit/{organizationUnitUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationAggregateId, @PathVariable("organizationUnitUid") String organizationUnitEntityId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //delete organizationUnit of organization
            organizationService.deleteOrganizationUnit(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId));
            //return organizationUnit status
            return new ResponseEntity<Boolean>(organizationService.organizationUnitStatus(tenantId, new AggregateId(organizationAggregateId), new EntityId(organizationUnitEntityId)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Base Currency
     * @param request
     * @param baseCurrency
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/basecurrency", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addBaseCurrency(HttpServletRequest request, @Valid @RequestBody DefaultCurrencyResource baseCurrency, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultCurrencyResource to DefaultCurrency Entity
            DefaultCurrency defaultCurrency = assemblerResolver.resolveEntityAssembler(DefaultCurrency.class, DefaultCurrencyResource.class).toEntity(baseCurrency, DefaultCurrency.class);
            //add base currency
            DefaultOrganization organization = organizationService.addBaseCurrency(tenantId, defaultCurrency, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organiztion is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add Time Zone
     * @param request
     * @param timezone
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/timezone", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addTimezone(HttpServletRequest request, @Valid @RequestBody DefaultTimezoneResource timezone, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultTimezoneResource to DefaultTimezone Entity
            DefaultTimezone defaultTimezone = assemblerResolver.resolveEntityAssembler(DefaultTimezone.class, DefaultTimezoneResource.class).toEntity(timezone, DefaultTimezone.class);
            //add time zone to organization
            DefaultOrganization organization = organizationService.addTimezone(tenantId, defaultTimezone, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganizationResource to DefaultOrganization Entity
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add Child Organization Unit To Parent Organization Unit
     * @param request
     * @param organizationUnit
     * @param parentOrganizationUnitUid
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/organizationunit/{parentOrganizationUnitUid}/below", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addOrganizationUnitBelow(HttpServletRequest request, @Valid @RequestBody DefaultOrganizationUnitResource organizationUnit, @PathVariable("parentOrganizationUnitUid") String parentOrganizationUnitUid, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultOrganizationUnit Resource to DefaultOrganizationUnit Entity
            DefaultOrganizationUnit organizationUnit1 = assemblerResolver.resolveEntityAssembler(DefaultOrganizationUnit.class, DefaultOrganizationUnitResource.class).toEntity(organizationUnit, DefaultOrganizationUnit.class);
            //add child organization unit to parent organization unit
            DefaultOrganization organization = organizationService.addOrganizationUnitBelow(tenantId, organizationUnit1, new EntityId(parentOrganizationUnitUid), new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organziationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Restructure Organization Unit - Change Parent Of Organization Unit
     * @param request
     * @param restructureResource
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/organizationUnit/restructure", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> restructureOrganizationUnit(HttpServletRequest request, @RequestBody DefaultOrganizationUnitRestructureResource restructureResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //restructure organization unit
            DefaultOrganization organization = organizationService.restructureOrganizationUnit(tenantId, new AggregateId(restructureResource.getOrganizationUid()), new EntityId(restructureResource.getOrganizationUnitUid()), restructureResource.getParentUid() != null ? new EntityId(restructureResource.getParentUid()) : null);
            DefaultOrganizationResource organizationResource = null;
            //organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add Contact Person
     * @param request
     * @param person
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/contactperson", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addContactPerson(HttpServletRequest request, @Valid @RequestBody DefaultPersonResource person, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPersonResource to DefaultPerson Entity
            DefaultPerson defaultPerson = assemblerResolver.resolveEntityAssembler(DefaultPerson.class, DefaultPersonResource.class).toEntity(person, DefaultPerson.class);
            //add contactPerson
            DefaultOrganization organization = organizationService.addContactPerson(tenantId, defaultPerson, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    /**
     * Set Default Contact Person
     * @param request
     * @param person
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/contactperson/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactPerson(HttpServletRequest request, @Valid @RequestBody DefaultPersonResource person, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPersonResource to DefaultPerson Entity
            DefaultPerson defaultPerson = assemblerResolver.resolveEntityAssembler(DefaultPerson.class, DefaultPersonResource.class).toEntity(person, DefaultPerson.class);
            //set default contact person of organization
            DefaultOrganization organization = organizationService.setDefaultContactPerson(tenantId, defaultPerson, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add Contact Number
     * @param request
     * @param phone
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/contactnumber", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addContactNumber(HttpServletRequest request, @Valid @RequestBody DefaultPhoneResource phone, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPhoneResource to DefaultPhone Entity
            DefaultPhone defaultPhone = assemblerResolver.resolveEntityAssembler(DefaultPhone.class, DefaultPhoneResource.class).toEntity(phone, DefaultPhone.class);
            //add contact number of an organization
            DefaultOrganization organization = organizationService.addContactNumber(tenantId, defaultPhone, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Set Default Contact Number
     * @param request
     * @param phone
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/contactnumber/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultContactNumber(HttpServletRequest request, @Valid @RequestBody DefaultPhoneResource phone, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPhoneResource to DefaultPhone Entity
            DefaultPhone defaultPhone = assemblerResolver.resolveEntityAssembler(DefaultPhone.class, DefaultPhoneResource.class).toEntity(phone, DefaultPhone.class);
            //set default contact number of organization
            DefaultOrganization organization = organizationService.setDefaultContactNumber(tenantId, defaultPhone, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add Email
     * @param request
     * @param email
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/email", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> addEmail(HttpServletRequest request, @Valid @RequestBody DefaultEmailResource email, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultEmailResource to DefaultEmail Entity
            DefaultEmail defaultEmail = assemblerResolver.resolveEntityAssembler(DefaultEmail.class, DefaultEmailResource.class).toEntity(email, DefaultEmail.class);
            //add email of an organization
            DefaultOrganization organization = organizationService.addEmail(tenantId, defaultEmail, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Set Default Email
     * @param request
     * @param email
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/email/default", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setDefaultEmail(HttpServletRequest request, @Valid @RequestBody DefaultEmailResource email, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else throw an exception
        if (tenantId != null) {
            //Map DefaultEmailResource to DefaultEmail Entity
            DefaultEmail defaultEmail = assemblerResolver.resolveEntityAssembler(DefaultEmail.class, DefaultEmailResource.class).toEntity(email, DefaultEmail.class);
            //set DefaultEmail of an organization
            DefaultOrganization organization = organizationService.setDefaultEmail(tenantId, defaultEmail, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganizaiton Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Set Head Office
     * @param request
     * @param address
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/headoffice", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> setHeadOffice(HttpServletRequest request, @Valid @RequestBody DefaultAddressResource address, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultAddressResource to DefaultAddress Entity
            DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
            //set head office of an organization
            DefaultOrganization organization = organizationService.setHeadOffice(tenantId, defaultAddress, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganizationResource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Move/Change Head Office To
     * @param request
     * @param address
     * @param aggregateId
     * @return DefaultOrganizationResource
     */
    @RequestMapping(value = "/organization/{organizationUid}/headoffice/to", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> moveHeadOfficeTo(HttpServletRequest request, @Valid @RequestBody DefaultAddressResource address, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultAddressResource to DefaultAddress Entity
            DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
            //change/move headoffice of an organization
            DefaultOrganization organization = organizationService.moveHeadOfficeTo(tenantId, defaultAddress, new AggregateId(aggregateId));
            DefaultOrganizationResource organizationResource = null;
            //if organization is not null
            if (organization != null) {
                //Map DefaultOrganization Entity to DefaultOrganization Resource
                organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                //return organizationResource
                return new ResponseEntity<DefaultOrganizationResource>(organizationResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get Head Office Address
     * @param request
     * @param aggregateId
     * @return
     */
    @RequestMapping(value = "/organization/{organizationUid}/headoffice/address", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultAddressResource> getHeadOfficeAddress(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get head office address of an organization
            DefaultAddress address = organizationService.getHeadOfficeAddress(tenantId, new AggregateId(aggregateId));
            //if address found
            if (address != null) {
                //Map DefaultAddress Entity to DefaultAddressResource
                DefaultAddressResource addressResource = assemblerResolver.resolveResourceAssembler(DefaultAddressResource.class, DefaultAddress.class).toResource(address, DefaultAddressResource.class);
                //return addressResource
                return new ResponseEntity<DefaultAddressResource>(addressResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get Base Currencies
     * @param request
     * @param aggregateId
     * @return Set<DefaultCurrencyResource>
     */
    @RequestMapping(value = "/organization/{organizationUid}/currencies/base", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultCurrencyResource>> getBaseCurrencies(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null
        if (tenantId != null) {
            //get base currencies of organization
            Set<DefaultCurrency> currencies = organizationService.getBaseCurrencies(tenantId, new AggregateId(aggregateId));
            //if currencies found
            if (currencies != null && currencies.size() > 0) {
                //Map DefaultCurrency Entities to DefaultCurrencyResources
                Set<DefaultCurrencyResource> currencyResources = assemblerResolver.resolveResourceAssembler(DefaultCurrencyResource.class, DefaultCurrency.class).toResources(currencies, DefaultCurrencyResource.class);
                //return currencyResources
                return new ResponseEntity<Set<DefaultCurrencyResource>>(currencyResources, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get Organization Units
     * @param request
     * @param aggregateId
     * @return Set<DefaultOrganizationUnitResource>
     */
    @RequestMapping(value = "/organization/{organizationUid}/organizationunits", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultOrganizationUnitResource>> getOrganizationUnits(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get organizationUnits of organization
            Set<DefaultOrganizationUnit> organizationUnits = organizationService.getOrganizationUnits(tenantId, new AggregateId(aggregateId));
            //if organizationUnits Found
            if (organizationUnits != null && organizationUnits.size() > 0) {
                //Map DefaultOrganizationUnit Entities to DefaultOraganizationUnitResources
                Set<DefaultOrganizationUnitResource> organizationUnitResources = assemblerResolver.resolveResourceAssembler(DefaultOrganizationUnitResource.class, DefaultOrganizationUnit.class).toResources(organizationUnits, DefaultOrganizationUnitResource.class);
                //return organizationUnitResources
                return new ResponseEntity<Set<DefaultOrganizationUnitResource>>(organizationUnitResources, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get Contact Numbers
     * @param request
     * @param aggregateId
     * @return Set<DefaultPhoneResource>
     */
    @RequestMapping(value = "/organization/{organizationUid}/contactnumbers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPhoneResource>> getContactNumbers(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get contact numbers of an organization
            Set<DefaultPhone> phones = organizationService.getContactNumbers(tenantId, new AggregateId(aggregateId));
            //if contact numbers found
            if (phones != null && phones.size() > 0) {
                //Map DefaultPhone Entities to DefaultPhone Resource
                Set<DefaultPhoneResource> phoneResources = assemblerResolver.resolveResourceAssembler(DefaultPhoneResource.class, DefaultPhone.class).toResources(phones, DefaultPhoneResource.class);
                //return phoneResources
                return new ResponseEntity<Set<DefaultPhoneResource>>(phoneResources, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get Emails
     * @param request
     * @param aggregateId
     * @return Set<DefaultEmailResource>
     */
    @RequestMapping(value = "/organization/{organizationUid}/emails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultEmailResource>> getEmails(HttpServletRequest request, @PathVariable("organizationUid") String aggregateId) {
        //tenantID
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get emails of organization
            Set<DefaultEmail> emails = organizationService.getEmails(tenantId, new AggregateId(aggregateId));
            //if emails found
            if (emails != null && emails.size() > 0) {
                //Map DefaultEmail Entities to DefaultEmailResources
                Set<DefaultEmailResource> emailResources = assemblerResolver.resolveResourceAssembler(DefaultEmailResource.class, DefaultEmail.class).toResources(emails, DefaultEmailResource.class);
                //return emailResources
                return new ResponseEntity<Set<DefaultEmailResource>>(emailResources, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Restructure Organization Units
     * @param request
     * @param organizationResource
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/organizationUnits/restructure", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> restructureOrganizationUnits(HttpServletRequest request, @RequestBody DefaultOrganizationResource organizationResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            DefaultOrganization organization = null;
            try {
                //Map DefaultOrganizationResource to DefaultOrganization Entity
                organization = assemblerResolver.resolveEntityAssembler(DefaultOrganization.class, DefaultOrganizationResource.class).toEntity(organizationResource, DefaultOrganization.class);
                //restructure organization units
                organization = organizationService.restructureOrganizationUnits(tenantId, organization.getAggregateId(), organization.getOrganizationUnits());
                //if restructured
                if (organization != null) {
                    //Map DefaultOrganization Entity to DefaultOrganizationResource
                    organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                    //return organizationResource
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

    /**
     * Restructure Organization Units Positions
     * @param request
     * @param organizationUid
     * @param positionStructure
     * @return DefaultOrganizationResource
     */
    @Override
    @RequestMapping(value = "/organization/{organizationUid}/positions/restructure", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultOrganizationResource> restructureOrganizationUnitsPositions(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @RequestBody DefaultPositionRestructureResource positionStructure) {
        //tenantID
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            DefaultOrganization organization = null;
            try {
                //restructure organizationUnits Positions
                organization = organizationService.restructureOrganizationUnitsPositions(tenantId, new AggregateId(organizationUid), positionStructure.getPositionStructure());
                //if restructured
                if (organization != null) {
                    //Map DefaultOrganization Entity to DefaultOrganizationResource
                    DefaultOrganizationResource organizationResource = assemblerResolver.resolveResourceAssembler(DefaultOrganizationResource.class, DefaultOrganization.class).toResource(organization, DefaultOrganizationResource.class);
                    //return organizationResource
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
