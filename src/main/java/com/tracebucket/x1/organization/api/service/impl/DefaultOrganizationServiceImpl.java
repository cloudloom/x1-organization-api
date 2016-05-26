package com.tracebucket.x1.organization.api.service.impl;

import com.tracebucket.tron.ddd.annotation.PersistChanges;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.*;
import com.tracebucket.x1.organization.api.repository.jpa.DefaultOrganizationRepository;
import com.tracebucket.x1.organization.api.rest.resource.DefaultOrganizationNameByIds;
import com.tracebucket.x1.organization.api.service.DefaultOrganizationService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Vishwajit on 15-04-2015.
 * DefaultOrganizationService Implementation
 */
@Service
@Transactional
public class DefaultOrganizationServiceImpl implements DefaultOrganizationService {
    private static Logger log = LoggerFactory.getLogger(DefaultOrganizationServiceImpl.class);

    @Autowired
    private Mapper mapper;

    @Autowired
    private DefaultOrganizationRepository organizationRepository;

    /**
     * Save
     * @param organization
     * @return DefaultOrganization
     */
    @Override
    public DefaultOrganization save(DefaultOrganization organization) {
        //get all organizationUnits
        Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
        //if organizationUnits found
        if(organizationUnits != null && organizationUnits.size() > 0) {
            //streama and forEach organizationUnit set its organization as 'organization'
            organizationUnits.stream().forEach(organizationUnit -> organizationUnit.setOrganization(organization));
        }
        //save organization
        return organizationRepository.save(organization);
    }

    /**
     * Find One
     * @param tenantId
     * @param aggregateId
     * @return DefaultOrganization
     */
    @Override
    public DefaultOrganization findOne(String tenantId, AggregateId aggregateId) {
        //if tenantId equals organizations uid, else return null
        if(tenantId.equals(aggregateId.getAggregateId())) {
            //flush all unsaved changes
            organizationRepository.flush();
            //return found organization
            return organizationRepository.findOne(aggregateId);
        }
        return null;
    }

    /**
     * Find One
     * @param tenantId
     * @return DefaultOrganization
     */
    @Override
    public DefaultOrganization findOne(String tenantId) {
        //return found organization
        return organizationRepository.findOne(new AggregateId(tenantId));
    }

    /**
     * Delete Organization
     * @param tenantId
     * @param organizationAggregateId
     * @return Boolean
     */
    @Override
    public boolean delete(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return false
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //find organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if found
            if (organization != null) {
                //delete organization
                organizationRepository.delete(organization.getAggregateId());
                //return true if organization is deleted else return false
                return organizationRepository.findOne(organizationAggregateId) == null ? true : false;
            }
            return false;
        }
        return false;
    }

    /**
     * Delete Organization Unit
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization deleteOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //find organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if found
            if (organization != null) {
                //delete organizationUnit
                organization.deleteOrganizationUnit(organizationUnitEntityId);
                return organization;
            }
        }
        return null;
    }

    /**
     * Get OrganizationUnit Status
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @return Boolean
     */
    @Override
    public boolean organizationUnitStatus(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId) {
        //if tenantId equals organization uid, else return false
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //find organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if found
            if (organization != null) {
                //get all organization units
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                //if organizationUnits found
                if(organizationUnits != null) {
                    //stream, filter and find matching organizationUnit
                    DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                            .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                            .findFirst()
                            .orElse(null);
                    //if matching organizationUnit Fetched
                    if(fetchedOrganizationUnit != null) {
                        //return its passive state
                        return fetchedOrganizationUnit.isPassive();
                    }
                }
            }
        }
        return false;
    }

    /**
     * Add Base Currency
     * @param tenantId
     * @param baseCurrency
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addBaseCurrency(String tenantId, DefaultCurrency baseCurrency, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //add base currency to organization
                organization.addBaseCurrency(baseCurrency);
                return organization;
            }
        }
        return null;
    }

    /**
     * Add Time Zone
     * @param tenantId
     * @param timezone
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addTimezone(String tenantId, DefaultTimezone timezone, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //add timezone to organization
                organization.addTimezone(timezone);
                return organization;
            }
        }
        return null;
    }

    /**
     * Add Organization Unit
     * @param tenantId
     * @param organizationUnit
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addOrganizationUnit(String tenantId, DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //add organizationUnit to organization
                organization.addOrganizationUnit(organizationUnit);
                return organization;
            }
        }
        return null;
    }

    /**
     * Get OrganizationUnit By Name
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitName
     * @return
     */
    @Override
    public DefaultOrganizationUnit getOrganizationUnitByName(String tenantId, AggregateId organizationAggregateId, String organizationUnitName) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //get all organizationUnits of organization
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                //if organizationUnits found for organization
                if(organizationUnits != null && organizationUnits.size() > 0) {
                    //stream, filter and find organizationUnit By Name, if found return
                    return organizationUnits.stream().filter(organizationUnit -> organizationUnit.getName().equals(organizationUnitName)).findFirst().orElse(null);
                }
            }
        }
        return null;
    }

    /**
     *
     * @param tenantId
     * @param organizationUnit
     * @param organizationAggregateId
     * @return
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updateOrganizationUnit(String tenantId, DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organiztion fetched
            if (organization != null) {
                //update organizationUnit of organization
                organization.updateOrganizationUnit(organizationUnit, mapper);
                return organization;
            }
        }
        return null;
    }

    /**
     * Add Child OrganizationUnit To Parent OrganizationUnit
     * @param tenantId
     * @param organizationUnit
     * @param parentOrganizationUnitEntityId
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addOrganizationUnitBelow(String tenantId, DefaultOrganizationUnit organizationUnit, EntityId parentOrganizationUnitEntityId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //stream, filter and find parent organizationUnit
            final DefaultOrganizationUnit parentOrganizationUnit = organization.getOrganizationUnits()
                    .stream()
                    .filter(t -> t.getEntityId().equals(parentOrganizationUnitEntityId))
                    .findFirst()
                    .orElse(null);
            //if organization fetched
            if (organization != null) {
                //add child organization unit to parent organization unit
                organization.addOrganizationUnitBelow(organizationUnit, parentOrganizationUnit);
                return organization;
            }
        }
        return null;
    }

    /**
     * Restructure Organization Unit
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param parentOrganizationUnitEntityId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization restructureOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, EntityId parentOrganizationUnitEntityId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if(organization != null) {
                //if parent organizationUnitEntityId is not null, it means that it is not a root organizationUnit
                if(parentOrganizationUnitEntityId != null) {
                    //stream, filter and find parentOrganizationUnit from organizations organiztionUnits
                    final DefaultOrganizationUnit parentOrganizationUnit = organization.getOrganizationUnits()
                            .stream()
                            .filter(t -> t.getEntityId().equals(parentOrganizationUnitEntityId))
                            .findFirst()
                            .orElse(null);
                    //stream, filter and find organizationUnit from organizations organiztionUnits
                    final DefaultOrganizationUnit organizationUnit = organization.getOrganizationUnits()
                            .stream()
                            .filter(t -> t.getEntityId().equals(organizationUnitEntityId))
                            .findFirst()
                            .orElse(null);
                    //if both parentOrganizationUnit and organizationUnit are not null
                    if (parentOrganizationUnit != null && organizationUnit != null) {
                        //add organizationUnit as child organizationUnit to parentOrganizationUnit
                        organization.addOrganizationUnitBelow(organizationUnit, parentOrganizationUnit);
                        return organization;
                    }
                }
                //if parent organizationUnitEntityId is null, it means that it is a root organizationUnit
                else if(parentOrganizationUnitEntityId == null) {
                    //mark organizationUnit as Root organizationUnit
                    organization.markOrganizationUnitAsRoot(organizationUnitEntityId);
                    return organization;
                }
            }
        }
        return null;
    }

    /**
     * Add Contact Person
     * @param tenantId
     * @param contactPerson
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addContactPerson(String tenantId, DefaultPerson contactPerson, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //add contactPerson to organization
                organization.addContactPerson(contactPerson);
                return organization;
            }
        }
        return null;
    }

    /**
     * Set DefaultContactPerson
     * @param tenantId
     * @param defaultContactPerson
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultContactPerson(String tenantId, DefaultPerson defaultContactPerson, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //set defaultContactPerson of organization
                organization.setDefaultContactPerson(defaultContactPerson);
                return organization;
            }
        }
        return null;
    }

    /**
     * Add Contact Number
     * @param tenantId
     * @param phone
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addContactNumber(String tenantId, DefaultPhone phone, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //add contactNumber to organization
                organization.addContactNumber(phone);
                return organization;
            }
        }
        return null;
    }

    /**
     * Set Default Contact Number
     * @param tenantId
     * @param defaultContactNumber
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultContactNumber(String tenantId, DefaultPhone defaultContactNumber, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //set default contact number of organization
                organization.setDefaultContactNumber(defaultContactNumber);
                return organization;
            }
        }
        return null;
    }

    /**
     * Add Email
     * @param tenantId
     * @param email
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addEmail(String tenantId, DefaultEmail email, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //add email to organization
                organization.addEmail(email);
                return organization;
            }
        }
        return null;
    }

    /**
     * Set Default Email
     * @param tenantId
     * @param defaultEmail
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultEmail(String tenantId, DefaultEmail defaultEmail, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetche
            if (organization != null) {
                //set default email of organization
                organization.setDefaultEmail(defaultEmail);
                return organization;
            }
        }
        return null;
    }

    /**
     * Set Head Office
     * @param tenantId
     * @param headOfficeAddress
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setHeadOffice(String tenantId, DefaultAddress headOfficeAddress, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //set head office of organization
                organization.setHeadOffice(headOfficeAddress);
                return organization;
            }
        }
        return null;
    }

    /**
     * Move/Change Head Office To
     * @param tenantId
     * @param newHeadOfficeAddress
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization moveHeadOfficeTo(String tenantId, DefaultAddress newHeadOfficeAddress, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //move or change head office of organization
                organization.moveHeadOfficeTo(newHeadOfficeAddress);
                return organization;
            }
        }
        return null;
    }

    /**
     * Get Head Office Address
     * @param tenantId
     * @param organizationAggregateId
     * @return DefaultAddress
     */
    @Override
    public DefaultAddress getHeadOfficeAddress(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //return head office address of organization
                return organization.getHeadOfficeAddress();
            }
        }
        return null;
    }

    /**
     * Get Base Currencies
     * @param tenantId
     * @param organizationAggregateId
     * @return Set<DefaultCurrency>
     */
    @Override
    public Set<DefaultCurrency> getBaseCurrencies(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //return base currencies of organization
                return organization.getBaseCurrencies();
            }
        }
        return null;
    }

    /**
     * Get OrganizationUnits
     * @param tenantId
     * @param organizationAggregateId
     * @return Set<DefaultOrganizationUnit>
     */
    @Override
    public Set<DefaultOrganizationUnit> getOrganizationUnits(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //track non deleted organizationUnits
            Set<DefaultOrganizationUnit> organizationUnits = new HashSet<DefaultOrganizationUnit>();
            //if organization fetched
            if (organization != null) {
                //get organizationUnits of organization
                Set<DefaultOrganizationUnit> defaultOrganizationUnits = organization.getOrganizationUnits();
                //if organizationUnits found
                if(defaultOrganizationUnits != null) {
                    //stream, forEach organizationUnit check if it is deleted
                    defaultOrganizationUnits.stream().forEach(orgUnit -> {
                        //if not deleted
                        if(!orgUnit.isPassive()) {
                            //add to non deleted organizationUnits
                            organizationUnits.add(orgUnit);
                        }
                    });
                }
                //return non deleted organizationUnits
                return organizationUnits;
            }
        }
        return null;
    }

    /**
     * Get Contact Numbers
     * @param tenantId
     * @param organizationAggregateId
     * @return Set<DefaultPhone>
     */
    @Override
    public Set<DefaultPhone> getContactNumbers(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //return organization contact numbers
                return organization.getContactNumbers();
            }
        }
        return null;
    }

    /**
     * Get Emails
     * @param tenantId
     * @param organizationAggregateId
     * @return Set<DefaultEmail>
     */
    @Override
    public Set<DefaultEmail> getEmails(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //get organization emails
                return organization.getEmails();
            }
        }
        return null;
    }

    /**
     * Find All Organizations
     * @return
     */
    @Override
    public List<DefaultOrganization> findAll() {
        return organizationRepository.findAll();
    }

    /**
     * Add Position
     * @param tenantId
     * @param organizationAggregateId
     * @param position
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addPosition(String tenantId, AggregateId organizationAggregateId, DefaultPosition position) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //add position to organization
                organization.addPosition(position);
                return organization;
            }
        }
        return null;
    }

    /**
     * Get Position By Name
     * @param tenantId
     * @param organizationAggregateId
     * @param position
     * @return DefaultPosition
     */
    @Override
    public DefaultPosition getPositionByName(String tenantId, AggregateId organizationAggregateId, String position) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //get positions of organization
                Set<DefaultPosition> positions = organization.getPositions();
                //if positions found
                if(positions != null && positions.size() > 0) {
                    //stream, filter positions by name to get position and return
                    return positions.stream().filter(pos -> pos.getName().equals(position)).findFirst().orElse(null);
                }
            }
        }
        return null;
    }

    /**
     * Add Child Position To Parent Position
     * @param tenantId
     * @param position
     * @param parentPositionEntityId
     * @param organizationAggregateId
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addPositionBelow(String tenantId, DefaultPosition position, EntityId parentPositionEntityId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //filter, stream and find parentPosition by uid
            final DefaultPosition parentPosition = organization.getPositions()
                    .stream()
                    .filter(t -> t.getEntityId().equals(parentPositionEntityId))
                    .findFirst()
                    .orElse(null);
            //if parent Position found
            if (parentPosition != null) {
                //add childPosition to parentPosition
                organization.addPositionBelow(position, parentPosition);
                return organization;
            }
        }
        return null;
    }

    /**
     * Add Position(s) To OrganizationUnit
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param positions
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addPositionToOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> positions) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //get positions of organization
                Set<DefaultPosition> positions1 = organization.getPositions();
                //if positions found
                if(positions1 != null) {
                    //cross check and track incoming positions with organization positions
                    Set<DefaultPosition> position = new HashSet<DefaultPosition>();
                    //stream and forEach position p
                    positions1.stream().forEach(p -> {
                        //check if incoming positions exist in organization positions, if exists then add
                        if(positions.contains(p.getEntityId().getId())) {
                            position.add(p);
                        }
                    });
                    //add all cross checked incoming positions to organizationUnits positions
                    organization.addPositionToOrganizationUnit(organizationUnitEntityId, position);
                    return organization;
                }
            }
        }
        return null;
    }

    /**
     * Update Position
     * @param tenantId
     * @param organizationAggregateId
     * @param position
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updatePosition(String tenantId, AggregateId organizationAggregateId, DefaultPosition position) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //update position
                organization.updatePosition(position, mapper);
                return organization;
            }
        }
        return null;
    }

    /**
     * Update Position(s) Of OrganizationUnit
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param positions
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updatePositionsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> positions) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch Organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //get positions of organization
                Set<DefaultPosition> positions1 = organization.getPositions();
                //if positions found
                if(positions1 != null) {
                    //cross check and track incoming positions with organization positions
                    Set<DefaultPosition> position = new HashSet<DefaultPosition>();
                    //stream and forEach position p
                    positions1.stream().forEach(p -> {
                        //check if incoming positions exist in organization positions, if exists then add
                        if(positions.contains(p.getEntityId().getId())) {
                            position.add(p);
                        }
                    });
                    //add all cross checked incoming positions to organizationUnits positions
                    organization.updatePositionsOfOrganizationUnit(organizationUnitEntityId, position);
                    return organization;
                }
            }
        }
        return null;
    }

    /**
     * Remove Positions
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param positions
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization removePositionsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> positions) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //get positions of organization
                Set<DefaultPosition> positions1 = organization.getPositions();
                //if positions found
                if(positions1 != null) {
                    //cross check and track incoming positions with organization positions
                    Set<DefaultPosition> position = new HashSet<DefaultPosition>();
                    //stream and forEach position p
                    positions1.stream().forEach(p -> {
                        //check if incoming positions exist in organization positions, if exists then add
                        if(positions.contains(p.getEntityId().getId())) {
                            position.add(p);
                        }
                    });
                    //remove all cross checked incoming positions from organizationUnits positions
                    organization.removePositionsOfOrganizationUnit(organizationUnitEntityId, position);
                    return organization;
                }
            }
        }
        return null;
    }

    /**
     * Get Positions
     * @param tenantId
     * @param organizationAggregateId
     * @return Set<DefaultPosition>
     */
    @Override
    public Set<DefaultPosition> getPositions(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //return organization positions
                return organization.getPositions();
            }
        }
        return null;
    }

    /**
     * Get Positions Of OrganizationUnit
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @return Set<DefaultPosition>
     */
    @Override
    public Set<DefaultPosition> getPositionsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //get positions of organizations organizationUnit
                return organization.getPositionsOfOrganizationUnit(organizationUnitEntityId);
            }
        }
        return null;
    }

    private Boolean anyMatch(Boolean found, Boolean hasChildren, DefaultOrganizationUnit search, DefaultOrganizationUnit organizationUnitResource) {
        if(search != null && organizationUnitResource != null) {
            if(!search.getEntityId().getId().equals(organizationUnitResource.getEntityId().getId())) {
                Set<DefaultOrganizationUnit> children = organizationUnitResource.getChildren();
                /*if(children != null && children.size() > 0) {
                    hasChildren = true;
                }*/
                if(children != null) {
                    for(DefaultOrganizationUnit child : children) {
                        found = anyMatch(found, hasChildren, search, child);
                    }
                }
            } else if(search.getEntityId().getId().equals(organizationUnitResource.getEntityId().getId())) {
                found = true;
                return found;
            }
        }
        return found;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization restructureOrganizationUnits(String tenantId, AggregateId organizationAggregateId, Set<DefaultOrganizationUnit> restructureOrganizationUnits) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                if(restructureOrganizationUnits != null && restructureOrganizationUnits.size() > 0) {
                    Set<DefaultOrganizationUnit> deleteOrganizationUnits = new HashSet<>();
                    Boolean found = false;
                    Boolean hasChildren = false;
                    if(organization.getOrganizationUnits() != null) {
                        for (DefaultOrganizationUnit orgUnit : organization.getOrganizationUnits()) {
                            found = false;
                            if (restructureOrganizationUnits != null) {
                                for (DefaultOrganizationUnit organizationUnit1 : restructureOrganizationUnits) {
                                    if (!orgUnit.isPassive() && !organizationUnit1.getEntityId().getId().equals(orgUnit.getEntityId().getId())) {
                                        Set<DefaultOrganizationUnit> children = organizationUnit1.getChildren();
                                        /*if(children != null && children.size() > 0) {
                                            hasChildren = true;
                                            break;
                                        }*/
                                        if(children != null) {
                                            for (DefaultOrganizationUnit child : children) {
                                                found = anyMatch(found, hasChildren, orgUnit, child);
                                            }
                                        }
                                    } else if(!orgUnit.isPassive() && organizationUnit1.getEntityId().getId().equals(orgUnit.getEntityId().getId())) {
                                        found = true;
                                        break;
                                    }
                                }
                            }
                            if(!found /*&& !hasChildren*/) {
                                deleteOrganizationUnits.add(orgUnit);
                            }
                        }
                    }
                    if(deleteOrganizationUnits.size() > 0) {
                        organization.deleteOrganizationUnits(deleteOrganizationUnits);
                    }
                    restructureOrganizationUnits.stream().forEach(organizationUnit -> {
                        Set<DefaultOrganizationUnit> children = organizationUnit.getChildren();
                        if(children != null && children.size() > 0) {
                            children.stream().forEach(child -> {
                                organization.restructureOrganizationUnits(null, organizationUnit.getEntityId().getId(), child.getEntityId().getId());
                                restructure(organization, organizationUnit, child);
                            });
                        } else {
                            organization.restructureOrganizationUnits(null, organizationUnit.getEntityId().getId(), null);
                        }
                    });
                    restructureOrganizationUnits.stream().forEach(organizationUnit -> {
                        updateOrganizationUnitPositions(organization, organizationUnit);
                        Set<DefaultOrganizationUnit> children = organizationUnit.getChildren();
                        children.stream().forEach(child -> {
                            updateOrganizationUnitPositions(organization, child);
                        });
                    });
                } else {
                    organization.deleteOrganizationUnits(organization.getOrganizationUnits());
                }
                return organization;
            }
        }
        return null;
    }

    private void updateOrganizationUnitPositions(DefaultOrganization organization, DefaultOrganizationUnit organizationUnit) {
        organization.updateOrganizationUnitPositions(organizationUnit);
        Set<DefaultOrganizationUnit> children = organizationUnit.getChildren();
        children.stream().forEach(child -> {
            updateOrganizationUnitPositions(organization, child);
        });
    }

    private void restructure(DefaultOrganization organization, DefaultOrganizationUnit parentOrganizationUnit, DefaultOrganizationUnit childOrganizationUnit) {
        Set<DefaultOrganizationUnit> children = childOrganizationUnit.getChildren();
        if(children != null && children.size() > 0) {
            children.stream().forEach(child -> {
                organization.restructureOrganizationUnits(parentOrganizationUnit.getEntityId().getId(), childOrganizationUnit.getEntityId().getId(), child.getEntityId().getId());
                restructure(organization, childOrganizationUnit, child);
            });
        } else {
            organization.restructureOrganizationUnits(parentOrganizationUnit.getEntityId().getId(), childOrganizationUnit.getEntityId().getId(), null);
        }
    }

    /**
     * Get Position
     * @param tenantId
     * @param organizationAggregateId
     * @param positionEntityId
     * @return DefaultPosition
     */
    @Override
    public DefaultPosition getPosition(String tenantId, AggregateId organizationAggregateId, EntityId positionEntityId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //return organizations position
                return organization.getPosition(positionEntityId);
            }
        }
        return null;
    }

    /**
     * Get Position Types
     * @param tenantId
     * @return PositionType[]
     */
    @Override
    public PositionType[] getPositionTypes(String tenantId) {
        return PositionType.values();
    }

    /**
     * Get OrganizationUnit Positions
     * @param tenantId
     * @param organizationAggregateId
     * @return <Map<String, Set<DefaultPosition>
     *     map key : organizationUnit uid
     *     map value : organizationUnits Positions
     */
    @Override
    public Map<String, Set<DefaultPosition>> getOrganizationUnitPositions(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //map key : organizationUnit uid, value : organizationUnits Positions
            Map<String, Set<DefaultPosition>> organizationUnitPositions = new HashMap<String, Set<DefaultPosition>>();
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //get organizationUnits of organization
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                //if organizationUnits fetched
                if(organizationUnits != null) {
                    //stream, forEach organizationUnit
                    organizationUnits.stream().forEach(organizationUnit -> {
                        //check for non deleted
                        if(!organizationUnit.isPassive()) {
                            //populate organizationUnitPositions map
                            organizationUnitPositions.put(organizationUnit.getEntityId().getId(), organizationUnit.getPositions());
                        }
                    });
                }
                //return organizationUnitPositions
                return organizationUnitPositions;
            }
        }
        return null;
    }

    /**
     * Search Organization Units
     * @param tenantId
     * @param organizationAggregateId
     * @param searchTerm
     * @return Set<DefaultOrganizationUnit>
     */
    @Override
    public Set<DefaultOrganizationUnit> searchOrganizationUnits(String tenantId, AggregateId organizationAggregateId, String searchTerm) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if(organization != null) {
                //get organizations organizationUnits
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                //if organizationUnits fetched
                if(organizationUnits != null) {
                    //stream, and forEach organizationUnit ou, match all of its properties with searchTerm and collect it to a set - result
                    Set<DefaultOrganizationUnit> result = organizationUnits.stream().filter(ou ->
                                    (ou.getName() != null && ou.getName().toLowerCase().matches(searchTerm)) ||
                                            (ou.getDescription() != null && ou.getDescription().toLowerCase().matches(searchTerm)) ||
                                            //ou.getOrganizationFunctions().contains(OrganizationFunction.valueOf(searchTerm)) ||
                                            ou.getAddresses().stream().filter(addresses -> addresses.getCity().toLowerCase().matches(searchTerm) ||
                                                            addresses.getCountry().toLowerCase().matches(searchTerm) ||
                                                            addresses.getRegion().toLowerCase().matches(searchTerm) ||
                                                            addresses.getState().toLowerCase().matches(searchTerm) ||
                                                            addresses.getDistrict().toLowerCase().matches(searchTerm) ||
                                                            addresses.getStreet().toLowerCase().matches(searchTerm) ||
                                                            addresses.getZip().toLowerCase().matches(searchTerm)
                                            ).count() > 0 ||
                                            ou.getPhones().stream()
                                                    .filter(phones -> phones.getNumber().toString().toLowerCase().matches(searchTerm))
                                                    .count() > 0 ||
                                            ou.getEmails().stream()
                                                    .filter(emails -> emails.getEmail().toLowerCase().matches(searchTerm))
                                                    .count() > 0
                    ).collect(Collectors.toSet());
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Search Positions
     * @param tenantId
     * @param organizationAggregateId
     * @param searchTerm
     * @return Set<DefaultPosition>
     */
    @Override
    public Set<DefaultPosition> searchPositions(String tenantId, AggregateId organizationAggregateId, String searchTerm) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if(organization != null) {
/*                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                if(organizationUnits != null) {
                    Set<DefaultPosition> positions = new HashSet<>();
                    organizationUnits.stream().forEach(organizationUnit -> {
                        if(organizationUnit.getName().toLowerCase().matches(searchTerm)) {
                            positions.addAll(organizationUnit.getPositions());
                        }
                        Set<DefaultAddress> addresses = organizationUnit.getAddresses();
                        if(addresses != null) {
                            if(addresses.stream().filter(address -> address.getCity().toLowerCase().matches(searchTerm) *//*||
                                            address.getCountry().toLowerCase().matches(searchTerm) ||
                                            address.getRegion().toLowerCase().matches(searchTerm) ||
                                            address.getState().toLowerCase().matches(searchTerm) ||
                                            address.getDistrict().toLowerCase().matches(searchTerm) ||
                                            address.getStreet().toLowerCase().matches(searchTerm) ||
                                            address.getZip().toLowerCase().matches(searchTerm)*//*
                            ).count() > 0) {
                                positions.addAll(organizationUnit.getPositions());
                            }
                        }
                    });
                    if(positions.size() > 0) {
                        return positions;
                    }
                }*/
                //get organization Positions
                Set<DefaultPosition> positions = organization.getPositions();
                //if positions fetched
                if(positions != null) {
                    //stream, forEach position match all position properties with searchTerm and if found collect it in a set - result
                    Set<DefaultPosition> result = positions.stream().filter(position -> position.getName().toLowerCase().matches(searchTerm) ||
                            position.getCode().toLowerCase().matches(searchTerm)).collect(Collectors.toSet());
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Restructure OrganizationUnits Positions
     * @param tenantId
     * @param organizationAggregateId
     * @param positionStructure
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization restructureOrganizationUnitsPositions(String tenantId, AggregateId organizationAggregateId, ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> positionStructure) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //restructure organizationUnitsPositions
                organization.restructureOrganizationUnitsPositions(positionStructure);
                return organization;
            }
        }
        return null;
    }

    /**
     * Add Department To Organization
     * @param tenantId
     * @param organizationAggregateId
     * @param departments
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addDepartmentToOrganization(String tenantId, AggregateId organizationAggregateId, Set<DefaultDepartment> departments) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //add departments to organization
                organization.addDepartmentToOrganization(departments);
                return organization;
            }
        }
        return null;
    }

    /**
     * Update Department Of Organization
     * @param tenantId
     * @param organizationAggregateId
     * @param departments
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updateDepartmentOfOrganization(String tenantId, AggregateId organizationAggregateId, Set<DefaultDepartment> departments) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //update department of organization
                organization.updateDepartmentOfOrganization(departments, mapper);
                return organization;
            }
        }
        return null;
    }

    /**
     * Get Organization Departments By Name
     * @param tenantId
     * @param organizationAggregateId
     * @param departments
     * @return Set<DefaultDepartment>
     */
    @Override
    public Set<DefaultDepartment> getOrganizationDepartmentsByName(String tenantId, AggregateId organizationAggregateId, List<String> departments) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //get departments of organization
                Set<DefaultDepartment> defaultDepartments = organization.getDepartmentsOfOrganization();
                //if departments fetched
                if(defaultDepartments != null && defaultDepartments.size() > 0) {
                    //if incoming department names is not null and size > 0
                    if(departments != null && departments.size() > 0) {
                        //fetchedDepartments by name
                        final Set<DefaultDepartment> fetchedDepartments = new HashSet<DefaultDepartment>();
                        //stream and forEach department
                        defaultDepartments.stream().forEach(defaultDepartment -> {
                            //check if incoming department contains the department, if else then add to fetchedDepartments
                            if(departments.contains(defaultDepartment.getName())) {
                                fetchedDepartments.add(defaultDepartment);
                            }
                        });
                        return fetchedDepartments;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get Departments Of Organization
     * @param tenantId
     * @param organizationAggregateId
     * @return Set<DefaultDepartment>
     */
    @Override
    public Set<DefaultDepartment> getDepartmentsOfOrganization(String tenantId, AggregateId organizationAggregateId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //return departments of Organization
                return organization.getDepartmentsOfOrganization();
            }
        }
        return null;
    }

    /**
     * Add Department(s) To OrganizationUnit
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param departments
     * @return
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addDepartmentToOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> departments) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //get departments of organization
                Set<DefaultDepartment> departments1 = organization.getDepartmentsOfOrganization();
                //if departments found
                if(departments1 != null) {
                    //cross check and validate incoming departments and include only those which validate
                    Set<DefaultDepartment> department = new HashSet<DefaultDepartment>();
                    //stream and forEach department p
                    departments1.stream().forEach(p -> {
                        //check if incoming department matches organizations department
                        if(departments.contains(p.getEntityId().getId())) {
                            //if matches add to department
                            department.add(p);
                        }
                    });
                    //add validated departments to organizations organizationUnit
                    organization.addDepartmentToOrganizationUnit(organizationUnitEntityId, department);
                    return organization;
                }
            }
        }
        return null;
    }

    /**
     * Update Departments Of OrganizationUnit
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @param departments
     * @return DefaultOrganization
     */
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updateDepartmentOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> departments) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization is fetched
            if (organization != null) {
                //get organizations departments
                Set<DefaultDepartment> departments1 = organization.getDepartmentsOfOrganization();
                //if departments found
                if(departments1 != null) {
                    //cross check and validate incoming departments and include only those which validate
                    Set<DefaultDepartment> department = new HashSet<DefaultDepartment>();
                    //stream and forEach department p
                    departments1.stream().forEach(p -> {
                        //check if incoming department matches organizations department
                        if(departments.contains(p.getEntityId().getId())) {
                            //if matches add to department
                            department.add(p);
                        }
                    });
                    //update validated departments of organizations organizationUnit
                    organization.updateDepartmentOfOrganizationUnit(organizationUnitEntityId, department);
                    return organization;
                }
            }
        }
        return null;
    }

    /**
     * Get Departments Of OrganizationUnit
     * @param tenantId
     * @param organizationAggregateId
     * @param organizationUnitEntityId
     * @return Set<DefaultDepartment>
     */
    @Override
    public Set<DefaultDepartment> getDepartmentsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId) {
        //if tenantId equals organization uid, else return null
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //fetch organization
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            //if organization fetched
            if (organization != null) {
                //return departments of organizations organizationUnit
                return organization.getDepartmentsOfOrganizationUnit(organizationUnitEntityId);
            }
        }
        return null;
    }

    /**
     * Get Organization Name Details By UIDS
     * Pass Either Organization UIDS | OrganizationUnit UIDS | Department UIDS | Position UIDS
     * @param tenantId
     * @param resource
     * @return DefaultOrganizationNameByIds
     * Names Of Respective Organization UIDS | OrganizationUnit UIDS | Department UIDS | Position UIDS
     * maps key contain UIDS and values contain Names
     */
    @Override
    public DefaultOrganizationNameByIds getOrganizationNameDetailsByUIDS(String tenantId, DefaultOrganizationNameByIds resource) {
        //find organization
        DefaultOrganization organization = findOne(tenantId);
        //if organization found
        if(organization != null) {
            //get organizations from resource
            if(resource.getOrganizations() != null && resource.getOrganizations().size() > 0) {
                //if organizations found in resource, check if organization Uid from resource matches fetched organizatios uid
                if (resource.getOrganizations().containsKey(organization.getAggregateId().getAggregateId())) {
                    //if matches, put organization name along with organization uid in organizations map
                    resource.getOrganizations().put(organization.getAggregateId().getAggregateId(), organization.getName());
                }
            }
            //get organizationUnits from resource
            if(resource.getOrganizationUnits() != null && resource.getOrganizationUnits().size() > 0) {
                //if organizationUnits found in resource
                if(organization.getOrganizationUnits() != null && organization.getOrganizationUnits().size() > 0) {
                    //stream, forEach Entry
                    resource.getOrganizationUnits().entrySet().stream().forEach(entry -> {
                        //stream and forEach organizations organizations units
                        organization.getOrganizationUnits().stream().forEach(organizationUnit -> {
                            //check if organizations organizationUnit uid matches resource organizationUnit uid
                            if(organizationUnit.getEntityId().getId().equals(entry.getKey())) {
                                //if matches set organizationUnit name
                                entry.setValue(organizationUnit.getName());
                            }
                        });
                    });
                }
            }
            //get departments from resource
            if(resource.getDepartments() != null && resource.getDepartments().size() > 0) {
                //if departments found
                if(organization.getDepartmentsOfOrganization() != null && organization.getDepartmentsOfOrganization().size() > 0) {
                    //stream, forEach entry of department in resource
                    resource.getDepartments().entrySet().stream().forEach(entry -> {
                        //stream and forEach organizations departments
                        organization.getDepartmentsOfOrganization().stream().forEach(department -> {
                            //check if organizations department uid matches resource department uid
                            if(department.getEntityId().getId().equals(entry.getKey())) {
                                //if matches set department name
                                entry.setValue(department.getName());
                            }
                        });
                    });
                }
            }
            if(resource.getPositions() != null && resource.getPositions().size() > 0) {
                if(organization.getPositions() != null && organization.getPositions().size() > 0) {
                    resource.getPositions().entrySet().stream().forEach(entry -> {
                        organization.getPositions().stream().forEach(position -> {
                            if(position.getEntityId().getId().equals(entry.getKey())) {
                                entry.setValue(position.getName());
                            }
                        });
                    });
                }
            }
            Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();

        }
        return resource;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization restructurePositionHierarchy(String tenantId, AggregateId organizationAggregateId, Set<DefaultPosition> positionsHierarchy) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                if(positionsHierarchy != null && positionsHierarchy.size() > 0) {
                    positionsHierarchy.stream().forEach(position -> {
                        Set<DefaultPosition> children = position.getChildren();
                        if(children != null && children.size() > 0) {
                            children.stream().forEach(child -> {
                                organization.restructurePositionHierarchy(null, position.getEntityId().getId(), child.getEntityId().getId());
                                restructurePositionHierarchy(organization, position, child);
                            });
                        } else {
                            organization.restructurePositionHierarchy(null, position.getEntityId().getId(), null);
                        }
                    });
                }
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization restructurePositionHierarchy(String tenantId, AggregateId organizationAggregateId, EntityId parentPositionEntityId, EntityId childPositionEntityId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.restructurePositionHierarchy(parentPositionEntityId, childPositionEntityId);
                return organization;
            }
        }
        return null;
    }

    private void restructurePositionHierarchy(DefaultOrganization organization, DefaultPosition parentPosition, DefaultPosition childPosition) {
        Set<DefaultPosition> children = childPosition.getChildren();
        if(children != null && children.size() > 0) {
            children.stream().forEach(child -> {
                organization.restructureOrganizationUnits(parentPosition.getEntityId().getId(), childPosition.getEntityId().getId(), child.getEntityId().getId());
                restructurePositionHierarchy(organization, childPosition, child);
            });
        } else {
            organization.restructurePositionHierarchy(parentPosition.getEntityId().getId(), childPosition.getEntityId().getId(), null);
        }
    }
}