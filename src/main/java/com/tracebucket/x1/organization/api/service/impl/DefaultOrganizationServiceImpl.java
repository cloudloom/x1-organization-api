package com.tracebucket.x1.organization.api.service.impl;

import com.tracebucket.tron.ddd.annotation.PersistChanges;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.x1.dictionary.api.domain.*;
import com.tracebucket.x1.organization.api.domain.OrganizationUnit;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.repository.jpa.DefaultOrganizationRepository;
import com.tracebucket.x1.organization.api.service.DefaultOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Created by Vishwajit on 15-04-2015.
 */
@Service
@Transactional
public class DefaultOrganizationServiceImpl implements DefaultOrganizationService {
    private static Logger log = LoggerFactory.getLogger(DefaultOrganizationServiceImpl.class);

    @Autowired
    private DefaultOrganizationRepository organizationRepository;

/*    @Autowired
    private CurrencyService currencyService;*/

    @Override
    public DefaultOrganization save(DefaultOrganization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public DefaultOrganization findOne(AggregateId aggregateId) {
        organizationRepository.flush();
        return organizationRepository.findOne(aggregateId);
    }

    @Override
    public boolean delete(AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organizationRepository.delete(organization);
            return organizationRepository.findOne(organizationAggregateId) == null ? true : false;
        }
        return false;
    }
/*
    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addBaseCurrency(Currency baseCurrency, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            Currency currency = currencyService.findOne(baseCurrency.getEntityId());
            if(currency != null) {
                organization.addBaseCurrency(currency);
                return organization;
            }
        }
        return null;
    }*/



    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addTimezone(Timezone timezone, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.addTimezone(timezone);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addOrganizationUnit(DefaultOrganizationUnit organizationUnit, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.addOrganizationUnit(organizationUnit);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit, DefaultOrganizationUnit parentOrganizationUnit, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.addOrganizationUnitBelow(organizationUnit, parentOrganizationUnit);
            return  organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addContactPerson(Person contactPerson, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.addContactPerson(contactPerson);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultContactPerson(Person defaultContactPerson, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.setDefaultContactPerson(defaultContactPerson);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addContactNumber(Phone phone, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.addContactNumber(phone);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultContactNumber(Phone defaultContactNumber, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.setDefaultContactNumber(defaultContactNumber);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization addEmail(Email email, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.addEmail(email);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setDefaultEmail(Email defaultEmail, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.setDefaultEmail(defaultEmail);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization setHeadOffice(Address headOfficeAddress, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.setHeadOffice(headOfficeAddress);
            return organization;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "organizationRepository")
    public DefaultOrganization moveHeadOfficeTo(Address newHeadOfficeAddress, AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            organization.moveHeadOfficeTo(newHeadOfficeAddress);
            return organization;
        }
        return null;
    }

    @Override
    public Address getHeadOfficeAddress(AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            return organization.getHeadOfficeAddress();
        }
        return null;
    }

/*    @Override
    public Set<Currency> getBaseCurrencies(AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            return organization.getBaseCurrencies();
        }
        return null;
    }*/

    @Override
    public Set<DefaultOrganizationUnit> getOrganizationUnits(AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            return organization.getOrganizationUnits();
        }
        return null;
    }

    @Override
    public Set<Phone> getContactNumbers(AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            return organization.getContactNumbers();
        }
        return null;
    }

    @Override
    public Set<Email> getEmails(AggregateId organizationAggregateId) {
        DefaultOrganization organization = organizationRepository.findOne(organizationAggregateId);
        if(organization != null) {
            return organization.getEmails();
        }
        return null;
    }

}
