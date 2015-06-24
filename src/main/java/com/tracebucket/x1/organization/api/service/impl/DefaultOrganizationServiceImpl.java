package com.tracebucket.x1.organization.api.service.impl;

import com.tracebucket.tron.ddd.annotation.PersistChanges;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.OrganizationFunction;
import com.tracebucket.x1.organization.api.domain.Position;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultPosition;
import com.tracebucket.x1.organization.api.domain.impl.jpa.PositionType;
import com.tracebucket.x1.organization.api.repository.jpa.DefaultOrganizationRepository;
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
            if (organization != null) {
                return organization.getOrganizationUnits();
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
    public DefaultOrganization addPosition(String tenantId, AggregateId organizationAggregateId, Set<DefaultPosition> position) {
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

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization restructureOrganizationUnits(String tenantId, AggregateId organizationAggregateId, Set<DefaultOrganizationUnit> restructureOrganizationUnits) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                if(restructureOrganizationUnits != null && restructureOrganizationUnits.size() > 0) {
                    restructureOrganizationUnits.stream().forEach(organizationUnit -> {
                        Set<DefaultOrganizationUnit> children = organizationUnit.getChildren();
                        if(children != null && children.size() > 0) {
                            children.parallelStream().forEach(child -> {
                                organization.restructureOrganizationUnits(null, organizationUnit.getEntityId().getId(), child.getEntityId().getId());
                                restructure(organization, organizationUnit, child);
                            });
                        }
                    });
                }
                return organization;
            }
        }
        return null;
    }

    private void restructure(DefaultOrganization organization, DefaultOrganizationUnit parentOrganizationUnit, DefaultOrganizationUnit childOrganizationUnit) {
        Set<DefaultOrganizationUnit> children = childOrganizationUnit.getChildren();
        if(children != null && children.size() > 0) {
            children.parallelStream().forEach(child -> {
                organization.restructureOrganizationUnits(parentOrganizationUnit.getEntityId().getId(), childOrganizationUnit.getEntityId().getId(), child.getEntityId().getId());
                restructure(organization, parentOrganizationUnit, child);
            });
        } else {
            return;
        }
    }

    @Override
    public DefaultOrganization restructureOrganizationUnits2(String tenantId, AggregateId organizationAggregateId, Set<DefaultOrganizationUnit> organizationUnits) {
        if (tenantId.equals(organizationAggregateId.getAggregateId())) {
            DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
            if (organization != null) {
                organization.restructureOrganizationUnits(organizationUnits);
                return organization;
            }
        }
        return null;
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
                        organizationUnitPositions.put(organizationUnit.getEntityId().getId(), organizationUnit.getPositions());
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

}
