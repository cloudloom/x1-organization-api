package com.tracebucket.x1.organization.api.service.impl;

import com.tracebucket.tron.ddd.annotation.PersistChanges;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.repository.jpa.DefaultOrganizationRepository;
import com.tracebucket.x1.organization.api.service.DefaultOrganizationService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

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

}
