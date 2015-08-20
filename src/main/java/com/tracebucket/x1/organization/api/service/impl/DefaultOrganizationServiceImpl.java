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
 */
@Service
@Transactional
public class DefaultOrganizationServiceImpl implements DefaultOrganizationService {
    private static Logger log = LoggerFactory.getLogger(DefaultOrganizationServiceImpl.class);

    @Autowired
    private Mapper mapper;

    @Autowired
    private DefaultOrganizationRepository organizationRepository;

    @Override
    public DefaultOrganization save(DefaultOrganization organization) {
        Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
        if(organizationUnits != null && organizationUnits.size() > 0) {
            organizationUnits.stream().forEach(organizationUnit -> organizationUnit.setOrganization(organization));
        }
        return organizationRepository.save(organization);
    }

    @Override
    public DefaultOrganization findOne(String tenantId, AggregateId aggregateId) {
        if(tenantId.equals(aggregateId.getAggregateId())) {
            organizationRepository.flush();
            return organizationRepository.findOne(aggregateId);
        }
        return null;
    }

    @Override
    public DefaultOrganization findOne(String tenantId) {
        return organizationRepository.findOne(new AggregateId(tenantId));
    }

    @Override
    public boolean delete(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organizationRepository.delete(organization.getAggregateId());
                return organizationRepository.findOne(organizationAggregateId) == null ? true : false;
            }
            return false;
        }
        return false;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization deleteOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.deleteOrganizationUnit(organizationUnitEntityId);
                return organization;
            }
        }
        return null;
    }

    @Override
    public boolean organizationUnitStatus(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                if(organizationUnits != null) {
                    DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                            .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                            .findFirst()
                            .orElse(null);
                    if(fetchedOrganizationUnit != null) {
                        return fetchedOrganizationUnit.isPassive();
                    }
                }
            }
        }
        return false;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addBaseCurrency(String tenantId, DefaultCurrency baseCurrency, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.addBaseCurrency(baseCurrency);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addTimezone(String tenantId, DefaultTimezone timezone, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.addTimezone(timezone);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addOrganizationUnit(String tenantId, DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.addOrganizationUnit(organizationUnit);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updateOrganizationUnit(String tenantId, DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.updateOrganizationUnit(organizationUnit, mapper);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addOrganizationUnitBelow(String tenantId, DefaultOrganizationUnit organizationUnit, EntityId parentOrganizationUnitEntityId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            final DefaultOrganizationUnit parentOrganizationUnit = organization.getOrganizationUnits()
                    .stream()
                    .filter(t -> t.getEntityId().equals(parentOrganizationUnitEntityId))
                    .findFirst()
                    .orElse(null);
            if (organization != null) {
                organization.addOrganizationUnitBelow(organizationUnit, parentOrganizationUnit);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization restructureOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, EntityId parentOrganizationUnitEntityId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if(organization != null) {
                if(parentOrganizationUnitEntityId != null) {
                    final DefaultOrganizationUnit parentOrganizationUnit = organization.getOrganizationUnits()
                            .stream()
                            .filter(t -> t.getEntityId().equals(parentOrganizationUnitEntityId))
                            .findFirst()
                            .orElse(null);
                    final DefaultOrganizationUnit organizationUnit = organization.getOrganizationUnits()
                            .stream()
                            .filter(t -> t.getEntityId().equals(organizationUnitEntityId))
                            .findFirst()
                            .orElse(null);
                    if (parentOrganizationUnit != null && organizationUnit != null) {
                        organization.addOrganizationUnitBelow(organizationUnit, parentOrganizationUnit);
                        return organization;
                    }
                } else if(parentOrganizationUnitEntityId == null) {
                    organization.markOrganizationUnitAsRoot(organizationUnitEntityId);
                    return organization;
                }
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addContactPerson(String tenantId, DefaultPerson contactPerson, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.addContactPerson(contactPerson);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultContactPerson(String tenantId, DefaultPerson defaultContactPerson, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.setDefaultContactPerson(defaultContactPerson);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addContactNumber(String tenantId, DefaultPhone phone, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.addContactNumber(phone);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultContactNumber(String tenantId, DefaultPhone defaultContactNumber, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.setDefaultContactNumber(defaultContactNumber);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addEmail(String tenantId, DefaultEmail email, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.addEmail(email);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultEmail(String tenantId, DefaultEmail defaultEmail, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.setDefaultEmail(defaultEmail);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setHeadOffice(String tenantId, DefaultAddress headOfficeAddress, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.setHeadOffice(headOfficeAddress);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization moveHeadOfficeTo(String tenantId, DefaultAddress newHeadOfficeAddress, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.moveHeadOfficeTo(newHeadOfficeAddress);
                return organization;
            }
        }
        return null;
    }

    @Override
    public DefaultAddress getHeadOfficeAddress(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                return organization.getHeadOfficeAddress();
            }
        }
        return null;
    }

    @Override
    public Set<DefaultCurrency> getBaseCurrencies(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                return organization.getBaseCurrencies();
            }
        }
        return null;
    }

    @Override
    public Set<DefaultOrganizationUnit> getOrganizationUnits(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            Set<DefaultOrganizationUnit> organizationUnits = new HashSet<DefaultOrganizationUnit>();
            if (organization != null) {
                Set<DefaultOrganizationUnit> defaultOrganizationUnits = organization.getOrganizationUnits();
                if(defaultOrganizationUnits != null) {
                    defaultOrganizationUnits.stream().forEach(orgUnit -> {
                        if(!orgUnit.isPassive()) {
                            organizationUnits.add(orgUnit);
                        }
                    });
                }
                return organizationUnits;
            }
        }
        return null;
    }

    @Override
    public Set<DefaultPhone> getContactNumbers(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                return organization.getContactNumbers();
            }
        }
        return null;
    }

    @Override
    public Set<DefaultEmail> getEmails(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                return organization.getEmails();
            }
        }
        return null;
    }

    @Override
    public List<DefaultOrganization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addPosition(String tenantId, AggregateId organizationAggregateId, DefaultPosition position) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.addPosition(position);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addPositionBelow(String tenantId, DefaultPosition position, EntityId parentPositionEntityId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            final DefaultPosition parentPosition = organization.getPositions()
                    .stream()
                    .filter(t -> t.getEntityId().equals(parentPositionEntityId))
                    .findFirst()
                    .orElse(null);
            if (parentPosition != null) {
                organization.addPositionBelow(position, parentPosition);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addPositionToOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> positions) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                Set<DefaultPosition> positions1 = organization.getPositions();
                if(positions1 != null) {
                    Set<DefaultPosition> position = new HashSet<DefaultPosition>();
                    positions1.stream().forEach(p -> {
                        if(positions.contains(p.getEntityId().getId())) {
                            position.add(p);
                        }
                    });
                    organization.addPositionToOrganizationUnit(organizationUnitEntityId, position);
                    return organization;
                }
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updatePosition(String tenantId, AggregateId organizationAggregateId, DefaultPosition position) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.updatePosition(position, mapper);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updatePositionsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> positions) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                Set<DefaultPosition> positions1 = organization.getPositions();
                if(positions1 != null) {
                    Set<DefaultPosition> position = new HashSet<DefaultPosition>();
                    positions1.stream().forEach(p -> {
                        if(positions.contains(p.getEntityId().getId())) {
                            position.add(p);
                        }
                    });
                    organization.updatePositionsOfOrganizationUnit(organizationUnitEntityId, position);
                    return organization;
                }
            }
        }
        return null;
    }

    @Override
    public Set<DefaultPosition> getPositions(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                return organization.getPositions();
            }
        }
        return null;
    }

    @Override
    public Set<DefaultPosition> getPositionsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
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

    @Override
    public DefaultPosition getPosition(String tenantId, AggregateId organizationAggregateId, EntityId positionEntityId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                return organization.getPosition(positionEntityId);
            }
        }
        return null;
    }

    @Override
    public PositionType[] getPositionTypes(String tenantId) {
        return PositionType.values();
    }

    @Override
    public Map<String, Set<DefaultPosition>> getOrganizationUnitPositions(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            Map<String, Set<DefaultPosition>> organizationUnitPositions = new HashMap<String, Set<DefaultPosition>>();
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                if(organizationUnits != null) {
                    organizationUnits.stream().forEach(organizationUnit -> {
                        if(!organizationUnit.isPassive()) {
                            organizationUnitPositions.put(organizationUnit.getEntityId().getId(), organizationUnit.getPositions());
                        }
                    });
                }
                return organizationUnitPositions;
            }
        }
        return null;
    }

    @Override
    public Set<DefaultOrganizationUnit> searchOrganizationUnits(String tenantId, AggregateId organizationAggregateId, String searchTerm) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if(organization != null) {
                Set<DefaultOrganizationUnit> organizationUnits = organization.getOrganizationUnits();
                if(organizationUnits != null) {
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

    @Override
    public Set<DefaultPosition> searchPositions(String tenantId, AggregateId organizationAggregateId, String searchTerm) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
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
                Set<DefaultPosition> positions = organization.getPositions();
                if(positions != null) {
                    Set<DefaultPosition> result = positions.stream().filter(position -> position.getName().toLowerCase().matches(searchTerm) ||
                            position.getCode().toLowerCase().matches(searchTerm)).collect(Collectors.toSet());
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization restructureOrganizationUnitsPositions(String tenantId, AggregateId organizationAggregateId, ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> positionStructure) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.restructureOrganizationUnitsPositions(positionStructure);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addDepartmentToOrganization(String tenantId, AggregateId organizationAggregateId, Set<DefaultDepartment> departments) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.addDepartmentToOrganization(departments);
                return organization;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updateDepartmentOfOrganization(String tenantId, AggregateId organizationAggregateId, Set<DefaultDepartment> departments) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.updateDepartmentOfOrganization(departments, mapper);
                return organization;
            }
        }
        return null;
    }

    @Override
    public Set<DefaultDepartment> getDepartmentsOfOrganization(String tenantId, AggregateId organizationAggregateId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                return organization.getDepartmentsOfOrganization();
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addDepartmentToOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> departments) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                Set<DefaultDepartment> departments1 = organization.getDepartmentsOfOrganization();
                if(departments1 != null) {
                    Set<DefaultDepartment> department = new HashSet<DefaultDepartment>();
                    departments1.stream().forEach(p -> {
                        if(departments.contains(p.getEntityId().getId())) {
                            department.add(p);
                        }
                    });
                    organization.addDepartmentToOrganizationUnit(organizationUnitEntityId, department);
                    return organization;
                }
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization updateDepartmentOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId, Set<String> departments) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                Set<DefaultDepartment> departments1 = organization.getDepartmentsOfOrganization();
                if(departments1 != null) {
                    Set<DefaultDepartment> department = new HashSet<DefaultDepartment>();
                    departments1.stream().forEach(p -> {
                        if(departments.contains(p.getEntityId().getId())) {
                            department.add(p);
                        }
                    });
                    organization.updateDepartmentOfOrganizationUnit(organizationUnitEntityId, department);
                    return organization;
                }
            }
        }
        return null;
    }

    @Override
    public Set<DefaultDepartment> getDepartmentsOfOrganizationUnit(String tenantId, AggregateId organizationAggregateId, EntityId organizationUnitEntityId) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                return organization.getDepartmentsOfOrganizationUnit(organizationUnitEntityId);
            }
        }
        return null;
    }

    @Override
    public DefaultOrganizationNameByIds getOrganizationNameDetailsByUIDS(String tenantId, DefaultOrganizationNameByIds resource) {
        DefaultOrganization organization = findOne(tenantId);
        if(organization != null) {
            if(resource.getOrganizations() != null && resource.getOrganizations().size() > 0) {
                if (resource.getOrganizations().containsKey(organization.getAggregateId().getAggregateId())) {
                    resource.getOrganizations().put(organization.getAggregateId().getAggregateId(), organization.getName());
                }
            }
            if(resource.getOrganizationUnits() != null && resource.getOrganizationUnits().size() > 0) {
                if(organization.getOrganizationUnits() != null && organization.getOrganizationUnits().size() > 0) {
                    resource.getOrganizationUnits().entrySet().stream().forEach(entry -> {
                        organization.getOrganizationUnits().stream().forEach(organizationUnit -> {
                            if(organizationUnit.getEntityId().getId().equals(entry.getKey())) {
                                entry.setValue(organizationUnit.getName());
                            }
                        });
                    });
                }
            }
            if(resource.getDepartments() != null && resource.getDepartments().size() > 0) {
                if(organization.getDepartmentsOfOrganization() != null && organization.getDepartmentsOfOrganization().size() > 0) {
                    resource.getDepartments().entrySet().stream().forEach(entry -> {
                        organization.getDepartmentsOfOrganization().stream().forEach(department -> {
                            if(department.getEntityId().getId().equals(entry.getKey())) {
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