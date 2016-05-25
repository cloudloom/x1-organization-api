package com.tracebucket.x1.organization.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.annotation.DomainMethod;
import com.tracebucket.tron.ddd.domain.BaseAggregateRoot;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.AddressType;
import com.tracebucket.x1.dictionary.api.domain.CurrencyType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.*;
import com.tracebucket.x1.organization.api.domain.Organization;
import com.tracebucket.x1.organization.api.domain.OrganizationUnit;
import org.dozer.Mapper;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Vishwajit on 13-04-2015.
 * JPA Entity For Organization
 */
@Entity
@Table(name = "ORGANIZATION")
public class DefaultOrganization extends BaseAggregateRoot implements Organization{

    @Column(name = "CODE", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String code;

    @Column(name = "NAME", nullable = false, unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.EAGER)
    private String description;

    @Column(name = "WEBSITE", unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String website;

    @Column(name = "IMAGE")
    @Basic(fetch = FetchType.EAGER)
    protected String image;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_ADDRESS", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_CURRENCY", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultCurrency> currencies = new HashSet<DefaultCurrency>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_TIMEZONE", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultTimezone> timezones = new HashSet<DefaultTimezone>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name = "ORGANIZATION_CONTACT_PERSON",
            joinColumns = { @JoinColumn(name = "ORGANIZATION__ID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "PERSON__ID", referencedColumnName = "ID", unique = true) }
    )
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPerson> contactPersons = new HashSet<DefaultPerson>(0);

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultOrganizationUnit> organizationUnits = new HashSet<DefaultOrganizationUnit>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_CONTACT_PHONE", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPhone> phones = new HashSet<DefaultPhone>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ORGANIZATION_CONTACT_EMAIL", joinColumns = @JoinColumn(name = "ORGANIZATION__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultEmail> emails = new HashSet<DefaultEmail>(0);

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORGANIZATION__ID")
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPosition> positions = new HashSet<DefaultPosition>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="ORGANIZATION__ID")
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultDepartment> departments = new HashSet<DefaultDepartment>(0);

    public DefaultOrganization() {
    }

    public DefaultOrganization(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public DefaultOrganization(String name, String code, String description, String website, String image) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.website = website;
        this.image = image;
    }


    /**
     * Add Base Currency
     * @param baseCurrency
     */
    @Override
    @DomainMethod(event = "BaseCurrencyAdded")
    public void addBaseCurrency(DefaultCurrency baseCurrency) {
        //If base currency is not null
        if(baseCurrency != null) {
            //set Currency Type As Base
            baseCurrency.setCurrencyType(CurrencyType.BASE);
        }
        //add base currency to organizations currencies
        this.currencies.add(baseCurrency);
    }

    /**
     * Add Time Zone
     * @param timezone
     */
    @Override
    @DomainMethod(event = "TimezoneAdded")
    public void addTimezone(DefaultTimezone timezone) {
        //add timezone to organizations timezones
        this.timezones.add(timezone);
    }

    /**
     * Add OrganizationUnit To Organization
     * @param organizationUnit
     */
    @Override
    @DomainMethod(event = "OrganizationUnitAdded")
    public void addOrganizationUnit(DefaultOrganizationUnit organizationUnit) {
        //if organiztionUnit is not null
        if(organizationUnit != null) {
            //set organizationUnits' organization as this
            organizationUnit.setOrganization(this);
            //add organization unit to organizations' organizationUnits
            this.organizationUnits.add(organizationUnit);
        }
    }

    /**
     * Delete OrganizationUnit
     * @param organizationUnitEntityId
     */
    @Override
    @DomainMethod(event = "OrganizationUnitDeleted")
    public void deleteOrganizationUnit(EntityId organizationUnitEntityId) {
        //Get all organizationUnits Of 'this' organization
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        //if organizationUnits is not null
        if(organizationUnits != null) {
            //find first organizationUnit whose entity id matches organizationUnitEntityId input parameter
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            //if organizationUnit is fetched
            if(fetchedOrganizationUnit != null) {
                //set its passive state to true
                fetchedOrganizationUnit.setPassive(true);
            }
        }
    }

    /**
     * Update Organization Unit
     * @param organizationUnit
     * @param mapper
     */
    @Override
    @DomainMethod(event = "OrganizationUnitUpdated")
    public void updateOrganizationUnit(DefaultOrganizationUnit organizationUnit, Mapper mapper) {
        //If organizationUnit is not null
        if(organizationUnit != null) {
            //stream organizationUnits and fetch organizationUnit to be updated
            DefaultOrganizationUnit organizationUnitFetched = organizationUnits.stream()
                    .filter(t -> t.getId().equals(organizationUnit.getId()))
                    .findFirst()
                    .orElse(null);
            //if organizationUnit is found and fetched
            if(organizationUnitFetched != null) {
                //clear all address
                organizationUnitFetched.getAddresses().clear();
                //if new addresses are not null
                if(organizationUnit.getAddresses() != null) {
                    //add all new addresses
                    organizationUnitFetched.getAddresses().addAll(organizationUnit.getAddresses());
                }
                //clear all phone nos
                organizationUnitFetched.getPhones().clear();
                //if new phone nos are not null
                if(organizationUnit.getPhones() != null) {
                    //add all new phone nos
                    organizationUnitFetched.getPhones().addAll(organizationUnit.getPhones());
                }
                //clear all email ids
                organizationUnitFetched.getEmails().clear();
                //if new email ids are not null
                if(organizationUnit.getEmails() != null) {
                    //add all new email ids
                    organizationUnitFetched.getEmails().addAll(organizationUnit.getEmails());
                }
                //update name
                organizationUnitFetched.setName(organizationUnit.getName());
                //update description
                organizationUnitFetched.setDescription(organizationUnit.getDescription());
                //mapper.map(organizationUnit, organizationUnitFetched);
                //clear all departments
                organizationUnitFetched.getDepartments().clear();
                //if new departments are not null
                if(organizationUnit.getDepartments() != null) {
                    //add all new departments
                    organizationUnitFetched.getDepartments().addAll(organizationUnit.getDepartments());
                }
                //set organizationUnits' organization to 'this' organization
                organizationUnitFetched.setOrganization(this);
            }
        }
    }

    /**
     * Add OrganizationUnit To Another Organization Unit (Sub Organization Unit)
     * @param organizationUnit
     * @param parentOrganizationUnit
     */
    @Override
    @DomainMethod(event = "OrganizationUnitBelowAdded")
    public void addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit, DefaultOrganizationUnit parentOrganizationUnit) {
        //stream and fetch parent organizationUnit
        OrganizationUnit parentOrganizationUnitFetched = organizationUnits.stream()
                .filter(t -> t.getId().equals(parentOrganizationUnit.getId()))
                .findFirst()
                .orElse(null);
        //if parentOrganizationUnitFetched is not null
        if(parentOrganizationUnitFetched != null) {
            //add child organizationUnit to parentOrganizationUnit
            parentOrganizationUnitFetched.addChild(organizationUnit);
        }
    }

    /**
     * Add Contact Person
     * @param contactPerson
     */
    @Override
    @DomainMethod(event = "ContactPersonAdded")
    public void addContactPerson(DefaultPerson contactPerson) {
        //if contactPerson is not null
        if(contactPerson != null) {
            //set contact person as not default
            contactPerson.setDefaultContactPerson(false);
            //add contactPerson to contactPersons of 'this' organization
            this.contactPersons.add(contactPerson);
        }
    }

    /**
     * Set Default Contact Person
     * @param defaultContactPerson
     */
    @Override
    @DomainMethod(event = "DefaultContactPersonSet")
    public void setDefaultContactPerson(DefaultPerson defaultContactPerson) {
        //if defaultContactPerson is not null
        if(defaultContactPerson != null) {
            //stream of contactPersons
            Stream<DefaultPerson> stream = this.contactPersons.stream();
            //stream and forEach contactPerson setDefaultContactPerson as false
            stream.forEach(t -> t.setDefaultContactPerson(false));
            //set defaultContactPerson as true
            defaultContactPerson.setDefaultContactPerson(true);
            //add defaultContactPerson to 'this' organization contactPersons
            this.contactPersons.add(defaultContactPerson);
        }
    }

    /**
     * Add Contact Number
     * @param phone
     */
    @Override
    @DomainMethod(event = "ContactNumberAdded")
    public void addContactNumber(DefaultPhone phone) {
        //if phone is not null
        if(phone != null) {
            //set phones' defaultPhone as false
            phone.setDefaultPhone(false);
            //add phone to 'this' organization phones
            this.phones.add(phone);
        }
    }

    /**
     * Set Default Contact Number
     * @param defaultContactNumber
     */
    @Override
    @DomainMethod(event = "DefaultContactNumberSet")
    public void setDefaultContactNumber(DefaultPhone defaultContactNumber) {
        //if defaultContactNumber is not null
        if(defaultContactNumber != null) {
            //stream of all contact nos
            Stream<DefaultPhone> stream = this.phones.stream();
            //stream and set default as false for all contact nos
            stream.forEach(t -> t.setDefaultPhone(false));
            //set defaultContactNumber as default
            defaultContactNumber.setDefaultPhone(true);
            //add defaultContactNumber to 'this' organizations phones
            this.phones.add(defaultContactNumber);
        }
    }

    /**
     * Mark OrganizationUnitAsRoot
     * @param organizationUnitEntityId
     */
    @Override
    @DomainMethod(event = "MarkOrganizationUnitAsRoot")
    public void markOrganizationUnitAsRoot(EntityId organizationUnitEntityId) {
        //fetch organizationUnit by entityId
        final DefaultOrganizationUnit organizationUnit = organizationUnits
                .stream()
                .filter(t -> t.getEntityId().equals(organizationUnitEntityId))
                .findFirst()
                .orElse(null);
        //if organizationUnit found
        if (organizationUnit != null) {
            //set parent as null, which means that this organizationUnit has no parent and it is the parent organizationUnit
            organizationUnit.setParent(null);
        }
    }

    /**
     * Add Email
     * @param email
     */
    @Override
    @DomainMethod(event = "EmailAdded")
    public void addEmail(DefaultEmail email) {
        //If email is not null
        if(email != null) {
            //set email as not default email
            email.setDefaultEmail(false);
            //add email to 'this' organizations emails
            this.emails.add(email);
        }
    }

    /**
     * Set Default Email
     * @param defaultEmail
     */
    @Override
    @DomainMethod(event = "DefaultEmailSet")
    public void setDefaultEmail(DefaultEmail defaultEmail) {
        //if default email is not null
        if(defaultEmail != null) {
            //stream of emails
            Stream<DefaultEmail> stream = this.emails.stream();
            //forEach email set it as not default
            stream.forEach(t -> t.setDefaultEmail(false));
            //set defaultEmail as default
            defaultEmail.setDefaultEmail(true);
            //add defaultEmail to 'this' organizations Emails
            this.emails.add(defaultEmail);
        }
    }

    /**
     * Set Head Office
     * @param headOfficeAddress
     */
    @Override
    @DomainMethod(event = "HeadOfficeSet")
    public void setHeadOffice(DefaultAddress headOfficeAddress) {
        //if headOfficeAddress is not null
        if(headOfficeAddress != null) {
            //set headOfficeAddress type as HEAD_OFFICE
            headOfficeAddress.setAddressType(AddressType.HEAD_OFFICE);
            //set headOfficeAddress as default address
            headOfficeAddress.setDefaultAddress(true);
            //add headOfficeAddress to 'this' organizations addresses
            this.addresses.add(headOfficeAddress);
        }
    }

    /**
     * Move Head Office To
     * @param newHeadOfficeAddress
     */
    @Override
    @DomainMethod(event = "HeadOfficeMovedTo")
    public void moveHeadOfficeTo(DefaultAddress newHeadOfficeAddress) {
        //newHeadOfficeAddress
        if(newHeadOfficeAddress != null) {
            //stream of 'this' organizations address
            Stream<DefaultAddress> stream = this.addresses.stream().filter(t -> t.getAddressType() == AddressType.HEAD_OFFICE);
            //forEach address set default as false
            stream.forEach(t -> t.setDefaultAddress(false));
            //set newHeadOfficeAddress type to HEAD_OFFICE
            newHeadOfficeAddress.setAddressType(AddressType.HEAD_OFFICE);
            //set newHeadOfficeAddress as default address
            newHeadOfficeAddress.setDefaultAddress(true);
            //add newHeadOfficeAddress to 'this' organizations addresses
            this.addresses.add(newHeadOfficeAddress);
        }
    }

    /**
     * Add Positions
     * @param position
     */
    @Override
    @DomainMethod(event = "AddPosition")
    public void addPosition(DefaultPosition position) {
        //if position is not null
        if(position != null) {
            //add position to 'this' organizations positions
            this.positions.add(position);
        }
    }

    /**
     * Add Child Position
     * @param position
     * @param parentPosition
     */
    @Override
    @DomainMethod(event = "AddPositionBelow")
    public void addPositionBelow(DefaultPosition position, DefaultPosition parentPosition) {
        //stream and filter 'this' organizations positions and find parentPosition
        DefaultPosition parentPositionFetched = positions.stream()
                .filter(t -> t.getId().equals(parentPosition.getEntityId().getId()))
                .findFirst()
                .orElse(null);
        //if parentPositionFetched is not null
        if(parentPositionFetched != null) {
            //add child position to parentPosition
            parentPositionFetched.addChild(position);
        }
    }

    /**
     * Update Position
     * @param position
     * @param mapper
     */
    @Override
    @DomainMethod(event = "UpdatePosition")
    public void updatePosition(DefaultPosition position, Mapper mapper) {
        //if position is not null
        if(position != null) {
            //fetch position to be updated
            DefaultPosition positionFetched = this.positions.stream()
                    .filter(t -> t.getEntityId().getId().equals(position.getEntityId().getId()))
                    .findFirst()
                    .orElse(null);
            //if position is fetched
            if(positionFetched != null) {
                //set all new values
                positionFetched.setPositionType(position.getPositionType());
                positionFetched.setName(position.getName());
                positionFetched.setCode(position.getCode());
                //mapper.map(position, positionFetched);
            }
        }
    }

    /**
     * Add Positions To Organization Unit
     * @param organizationUnitEntityId
     * @param position
     */
    @Override
    @DomainMethod(event = "AddPositionToOrganizationUnit")
    public void addPositionToOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultPosition> position) {
        //'this' organizations organizationUnits
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        //if organizationUnits is not null
        if(organizationUnits != null) {
            //fetch OrganizationUnit
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            //if fetchedOrganizationUnit is not null
            if(fetchedOrganizationUnit != null) {
                //add positions to fetchedOrganizationUnit
                fetchedOrganizationUnit.getPositions().addAll(position);
            }
        }
    }

    /**
     * Update Positions Of Organization Unit
     * @param organizationUnitEntityId
     * @param position
     */
    @Override
    @DomainMethod(event = "UpdatePositionsOfOrganizationUnit")
    public void updatePositionsOfOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultPosition> position) {
        //'this' organizations organizationUnits
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        //if organizationUnits is not null
        if(organizationUnits != null) {
            //fetch organizationUnit whose positions have to be updated
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            //if fetchedOrganiztionUnit is not null
            if(fetchedOrganizationUnit != null) {
                //get Positions Of fetchedOrganizationUnit
                Set<DefaultPosition> positions1 = fetchedOrganizationUnit.getPositions();
                //if fetchedOrganizationUnits positions is not null
                if(positions1 != null) {
                    //iterate fetchedOrganizationUnits positions
                    Iterator<DefaultPosition> iterator = positions1.iterator();
                    while(iterator.hasNext()) {
                        DefaultPosition p =iterator.next();
                            //if fetchedOrganizationUnits position is not there in incoming positions, then remove the position
                            if (position != null && !position.contains(p)) {
                                //positions1.remove(p);
                                iterator.remove();
                            }
                    }
                }
                //if incoming positions is not null
                if(position != null) {
                    //iterate incoming positions
                    Iterator<DefaultPosition> iterator = position.iterator();
                    while(iterator.hasNext()){
                        DefaultPosition p = iterator.next();
                        //if incoming position is not there in fetchedOrganizationUnits position then add to it
                        if(!positions1.contains(p)) {
                            positions1.add(p);
                        }
                    };
                }
            }

        }
    }

    /**
     * Remove Positions Of Organization Unit
     * @param organizationUnitEntityId
     * @param position
     */
    @Override
    @DomainMethod(event = "PositionsOfOrganizationUnitRemoved")
    public void removePositionsOfOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultPosition> position) {
        //'this' organizations organizationUnits
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        //if organizationUnits is not null
        if(organizationUnits != null) {
            //fetch organization unit whose positions have to removed
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            //if fetchedOrganizationUnit is not null
            if (fetchedOrganizationUnit != null) {
                //fetchedOrganizationUnits positions
                Set<DefaultPosition> positions1 = fetchedOrganizationUnit.getPositions();
                //if fetchedOrganizationUnits positions is not null
                if(positions1 != null && positions1.size() > 0) {
                    //remove all positions by incoming positions
                    positions1.removeAll(position);
                }
            }
        }
    }

    /**
     * Get Positions Of Organization Unit
     * @param organizationUnitEntityId
     * @return
     */
    @Override
    public Set<DefaultPosition> getPositionsOfOrganizationUnit(EntityId organizationUnitEntityId) {
        //'this' organizations organizationUnits
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        //if organizationUnits is not null
        if(organizationUnits != null) {
            //fetchOrganizationUnit whose positions are to returned
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            //if fetchedOrganizationUnit is not null
            if(fetchedOrganizationUnit != null) {
                //return its positions
                return fetchedOrganizationUnit.getPositions();
            }
        }
        return null;
    }

    /**
     * Add Departments
     * @param departments
     */
    @Override
    @DomainMethod(event = "AddDepartmentToOrganization")
    public void addDepartmentToOrganization(Set<DefaultDepartment> departments) {
        //if departments is not null
        if(departments != null) {
            //add all incoming departments to 'this' organizations departments
            this.departments.addAll(departments);
        }
    }

    /**
     * Update Departments
     * @param departments
     * @param mapper
     */
    @Override
    @DomainMethod(event = "UpdateDepartmentOfOrganization")
    public void updateDepartmentOfOrganization(Set<DefaultDepartment> departments, Mapper mapper) {
        //if incoming departments is not null
        if(departments != null) {
            //stream and forEach incoming department
            departments.stream().forEach(department -> {
                //if 'this' organizations departments is not null and size is greater than zero
                if(this.departments != null && this.departments.size() > 0) {
                    //stream, filter and find department to be updated by incoming department
                    DefaultDepartment fetchedDepartment = this.departments.stream()
                            .filter(t -> t.getEntityId().getId().equals(department.getEntityId().getId()))
                            .findFirst()
                            .orElse(null);
                    //if fetchedDepartment is not null
                    if(fetchedDepartment != null) {
                        //map new values
                        mapper.map(department, fetchedDepartment);
                    }
                }
            });
        }
    }

    /**
     * Get All Departments Of An Organization
     * @return
     */
    @Override
    public Set<DefaultDepartment> getDepartmentsOfOrganization() {
        return this.departments;
    }

    /**
     * Add Departments To Organization Unit
     * @param organizationUnitEntityId
     * @param departments
     */
    @Override
    @DomainMethod(event = "AddDepartmentToOrganizationUnit")
    public void addDepartmentToOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultDepartment> departments) {
        //'this' organizations organizationUnits
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        //if orgaizationUnits is not null
        if(organizationUnits != null) {
            //stream, filter and find organizationUnit to which departments have to be added
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            //if fetchedOrganizationUnit is not null
            if(fetchedOrganizationUnit != null) {
                //if incoming departments is not null and size is greater than zero
                if(departments != null && departments.size() > 0) {
                    //add all incoming deparments to fetchedOrganizationUnit
                    fetchedOrganizationUnit.getDepartments().addAll(departments);
                }
            }
        }
    }

    /**
     * Update Deparments Of Organization Unit
     * @param organizationUnitEntityId
     * @param departments
     */
    @Override
    @DomainMethod(event = "UpdateDepartmentOfOrganizationUnit")
    public void updateDepartmentOfOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultDepartment> departments) {
        //'this' organizations organizationUnits
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        //if organizationUnits is not null
        if(organizationUnits != null) {
            //stream, fetch and find organizationUnit whose departments have to be updated
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            //if fetchedOrganizationUnit is not null
            if(fetchedOrganizationUnit != null) {
                //fetchedOrganizationUnits departments
                Set<DefaultDepartment> departments1 = fetchedOrganizationUnit.getDepartments();
                //if fetched departments is not null
                if(departments1 != null) {
                    //iterate fetched departments
                    Iterator<DefaultDepartment> iterator = departments1.iterator();
                    while(iterator.hasNext()) {
                        DefaultDepartment d =iterator.next();
                        //if incoming departments is not null and incoming departments does not contain fetched department then remove fetched department
                        if (departments != null && !departments.contains(d)) {
                            departments1.remove(d);
                        }
                    }
                }
                // if incoming deparments is not null
                if(departments != null) {
                    //iterate incoming departments
                    Iterator<DefaultDepartment> iterator = departments.iterator();
                    while(iterator.hasNext()){
                        DefaultDepartment d = iterator.next();
                        //if fetchedDepartments does not contain incoming department, then add it
                        if(!departments1.contains(d)) {
                            departments1.add(d);
                        }
                    };
                }
            }

        }
    }

    /**
     * Get Departments Of Organization Unit
     * @param organizationUnitEntityId
     * @return
     */
    @Override
    public Set<DefaultDepartment> getDepartmentsOfOrganizationUnit(EntityId organizationUnitEntityId) {
        //'this' organizations organizationUnits
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        //if organizationUnits is not null
        if(organizationUnits != null) {
            //stream, filter and fetched organizationUnit whose departments have to be returned
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            //if fetchedOrganizationUnit is not null then return its deparments
            if(fetchedOrganizationUnit != null) {
                return fetchedOrganizationUnit.getDepartments();
            }
        }
        return null;
    }

    /**
     * Get code
     * @return
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Get Name
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get Description
     * @return
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Get Website
     * @return
     */
    @Override
    public String getWebsite() {
        return website;
    }

    /**
     * Get Image
     * @return
     */
    @Override
    public String getImage() {
        return image;
    }

    /**
     * Get Head Office Address
     * @return
     */
    @Override
    public DefaultAddress getHeadOfficeAddress() {
        //stream, filter and find head office of 'this' organization, return if it is found else return null
        DefaultAddress headOfficeAddress =
                addresses.stream()
                        .filter(t -> t.getAddressType() == AddressType.HEAD_OFFICE)
                        .filter(t -> t.isDefaultAddress())
                        .findFirst()
                        .orElse(null);
        return headOfficeAddress;
    }

    /**
     * Get Base Currencies
     * @return
     */
    @Override
    public Set<DefaultCurrency> getBaseCurrencies() {
        Set<DefaultCurrency> baseCurrencies = new HashSet<DefaultCurrency>();
        //'this' organizations currencies is not null and size greater than zero
        if(currencies != null && currencies.size() > 0) {
            //iterate all currencies
            for (DefaultCurrency currency : currencies) {
                //check if currency is base currency
                if(currency.getCurrencyType().equals(CurrencyType.BASE)) {
                    //if it is base currency, add to baseCurrencies set
                    baseCurrencies.add(currency);
                }
            }
        }
        //return baseCurrencies
        return baseCurrencies;
    }

    /**
     * Get Organization Units
     * @return
     */
    @Override
    public Set<DefaultOrganizationUnit> getOrganizationUnits() {
        return this.organizationUnits;
    }

    /**
     * Get Contact Numbers
     * @return
     */
    @Override
    public Set<DefaultPhone> getContactNumbers() {
        return this.phones;
    }

    /**
     * Get Emails
     * @return
     */
    @Override
    public Set<DefaultEmail> getEmails() {
        return this.emails;
    }

    /**
     * Get Timezones
     * @return
     */
    @Override
    public Set<DefaultTimezone> getTimezones() {
        return this.timezones;
    }

    /**
     * Get Contact Persons
     * @return
     */
    @Override
    public Set<DefaultPerson> getContactPersons() {
        return contactPersons;
    }

    /**
     * Get Phones
     * @return
     */
    @Override
    public Set<DefaultPhone> getPhones() {
        return phones;
    }

    /**
     * Get Addresses
     * @return
     */
    @Override
    public Set<DefaultAddress> getAddresses() {
        return addresses;
    }

    /**
     * Get Currencies
     * @return
     */
    @Override
    public Set<DefaultCurrency> getCurrencies() {
        return currencies;
    }

    /**
     * Get Positions
     * @return
     */
    @Override
    public Set<DefaultPosition> getPositions() {
        return this.positions;
    }

    /**
     * Get Position by Id
     * @param positionEntityId
     * @return
     */
    @Override
    public DefaultPosition getPosition(EntityId positionEntityId) {
        DefaultPosition position =
                positions.stream()
                        .filter(t -> t.getEntityId().getId().equals(positionEntityId.getId()))
                        .findFirst()
                        .orElse(null);
        return position;
    }

    /**
     * Set Code
     * @param code
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Set Name
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set Description
     * @param description
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set Website
     * @param website
     */
    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Set Image
     * @param image
     */
    @Override
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Set Organization Units
     * @param organizationUnits
     */
    @Override
    public void setOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }

    /**
     * Set Addresses
     * @param addresses
     */
    @Override
    public void setAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
    }

    /**
     * Set Currencies
     * @param currencies
     */
    @Override
    public void setCurrencies(Set<DefaultCurrency> currencies) {
        this.currencies = currencies;
    }

    /**
     * Set Timezones
     * @param timezones
     */
    @Override
    public void setTimezones(Set<DefaultTimezone> timezones) {
        this.timezones = timezones;
    }

    /**
     * Set Contact Persons
     * @param contactPersons
     */
    @Override
    public void setContactPersons(Set<DefaultPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    /**
     * Set Phones
     * @param phones
     */
    @Override
    public void setPhones(Set<DefaultPhone> phones) {
        this.phones = phones;
    }

    /**
     * Set Emails
     * @param emails
     */
    @Override
    public void setEmails(Set<DefaultEmail> emails) {
        this.emails = emails;
    }

    /**
     * Update Positions Of Organization Unit
     * @param organizationUnit
     */
    @Override
    @DomainMethod(event = "UpdateOrganizationUnitPositions")
    public void updateOrganizationUnitPositions(DefaultOrganizationUnit organizationUnit) {
        //stream, filter and find 'this' organizations organization unit whose positions have to be updated
        DefaultOrganizationUnit fetchedOrganizationUnit = this.organizationUnits.stream().filter(orgUnit -> orgUnit.getEntityId().getId().equals(organizationUnit.getEntityId().getId()))
                .findFirst()
                .orElse(null);
        //if fetchedOrganizationUnit is not null
        if(fetchedOrganizationUnit != null) {
            //fetchedOrganizationUnit Positions
            Set<DefaultPosition> positions = fetchedOrganizationUnit.getPositions();
            //incoming organizationUnit Positions
            Set<DefaultPosition> newPositions = organizationUnit.getPositions();
            //stream, forEach new Position (entry2)
            newPositions.stream().forEach(entry2 -> {
                //check if there is a match in fetchedOrganizationUnit Positions
                boolean noneMatch = positions.stream().anyMatch(position -> position.getEntityId().getId().equals(entry2.getEntityId().getId()));
                //if there is no match
                if (!noneMatch) {
                    //check if the new position is there in 'this' organizations positions
                    DefaultPosition defaultPosition = this.getPositions().stream().filter(pos -> pos.getEntityId().getId().equals(entry2.getEntityId().getId())).findFirst().orElse(null);
                    //if found in 'this' organizations positions
                    if (defaultPosition != null) {
                        //add to fetchedOrganizationUnits Positions
                        positions.add(defaultPosition);
                    }
                }
            });
            //iterate fetchedOrganizationUnit Positions
            Iterator<DefaultPosition> iterator = positions.iterator();
            while (iterator.hasNext()) {
                DefaultPosition pos1 = iterator.next();
                //Check if already existing position is available in incoming new positions
                boolean noneMatch = newPositions.stream().anyMatch(entry3 -> entry3.getEntityId().getId().equals(pos1.getEntityId().getId()));
                if (!noneMatch) {
                    //if not found in incoming new positions, then remove from fetchedOrganizationUnit Positions
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    @DomainMethod(event = "RestructureOrganizationUnits")
    public void restructureOrganizationUnits(String rootOrganizationUnit, String parentOrganizationUnitUid, String childOrganizationUnitUid) {
        DefaultOrganizationUnit root = null, parent = null, child = null;
        List<DefaultOrganizationUnit> fetchedOrganizationUnits = organizationUnits.stream()
                    .filter(t -> t.getEntityId().getId().equals(parentOrganizationUnitUid)
                            || childOrganizationUnitUid != null && t.getEntityId().getId().equals(childOrganizationUnitUid)
                            || rootOrganizationUnit != null && t.getEntityId().getId().equals(rootOrganizationUnit))
                    .collect(Collectors.toList());
        if(fetchedOrganizationUnits != null && fetchedOrganizationUnits.size() > 0) {
            for(DefaultOrganizationUnit organizationUnit : fetchedOrganizationUnits) {
                if(rootOrganizationUnit != null && organizationUnit.getEntityId().getId().equals(rootOrganizationUnit)) {
                    root = organizationUnit;
                } else if(organizationUnit.getEntityId().getId().equals(parentOrganizationUnitUid)) {
                    parent = organizationUnit;
                } else if(childOrganizationUnitUid != null && organizationUnit.getEntityId().getId().equals(childOrganizationUnitUid)) {
                    child = organizationUnit;
                }
            }
        }
        if(parent != null && child != null) {
            child.setParent(parent);
            if(root != null) {
                parent.setParent(root);
            } else {
                parent.setParent(null);
            }
        } else if(parent != null && child == null) {
           // parent.setParent(null);
            if(root != null) {
                parent.setParent(root);
            } else {
                parent.setParent(null);
            }
            Set<DefaultOrganizationUnit> childOrgs = parent.getChildren();
            if(childOrgs != null) {
                childOrgs.stream().forEach(childOrg -> childOrg.setParent(null));
            }
        }
    }

    @Override
    @DomainMethod(event = "RestructurePositionHierarchy")
    public void restructurePositionHierarchy(String rootOrganizationUnit, String parentPositionUid, String childPositionUid) {
        DefaultPosition root = null, parent = null, child = null;
        List<DefaultPosition> fetchedPositions = positions.stream()
                .filter(t -> t.getEntityId().getId().equals(parentPositionUid)
                        || childPositionUid != null && t.getEntityId().getId().equals(childPositionUid)
                        || rootOrganizationUnit != null && t.getEntityId().getId().equals(rootOrganizationUnit))
                .collect(Collectors.toList());
        if(fetchedPositions != null && fetchedPositions.size() > 0) {
            for(DefaultPosition position : fetchedPositions) {
                if(rootOrganizationUnit != null && position.getEntityId().getId().equals(rootOrganizationUnit)) {
                    root = position;
                } else if(position.getEntityId().getId().equals(parentPositionUid)) {
                    parent = position;
                } else if(childPositionUid != null && position.getEntityId().getId().equals(childPositionUid)) {
                    child = position;
                }
            }
        }
        if(parent != null && child != null) {
            child.setParent(parent);
            if(root != null) {
                parent.setParent(root);
            } else {
                parent.setParent(null);
            }
        } else if(parent != null && child == null) {
            // parent.setParent(null);
            if(root != null) {
                parent.setParent(root);
            } else {
                parent.setParent(null);
            }
            Set<DefaultPosition> childPos = parent.getChildren();
            if(childPos != null) {
                childPos.stream().forEach(childOrg -> childOrg.setParent(null));
            }
        }
    }

    @Override
    @DomainMethod(event = "RestructurePositionHierarchy")
    public void restructurePositionHierarchy(EntityId parentPositionEntityId, EntityId childPositionEntityId) {
        if(parentPositionEntityId != null && childPositionEntityId != null && positions != null) {
            DefaultPosition parentPosition = positions.stream()
                    .filter(t -> t.getEntityId().getId().equals(parentPositionEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            DefaultPosition childPosition = positions.stream()
                    .filter(t -> t.getEntityId().getId().equals(childPositionEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            if(parentPosition != null && childPosition != null) {
                childPosition.setParent(parentPosition);
                parentPosition.getChildren().add(childPosition);
            } else if(parentPositionEntityId != null && parentPositionEntityId.getId().equals("0") && childPosition != null) {
                childPosition.setParent(null);
            }
        }
    }

    @Override
    @DomainMethod(event = "RestructureOrganizationUnitsPositions")
    public void restructureOrganizationUnitsPositions(ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> positionsInput) {
        if(positionsInput != null) {
            Set<DefaultOrganizationUnit> organizationUnits = this.getOrganizationUnits();
            for(HashMap<String, HashMap<String, ArrayList<String>>> positionStructure : positionsInput) {
                positionStructure.entrySet().stream().forEach(entry -> {
                    DefaultOrganizationUnit organizationUnit = organizationUnits.stream().filter(ou -> (!ou.isPassive() && ou.getEntityId().getId().equals(entry.getKey()))).findFirst().orElse(null);
                    if (organizationUnit != null) {
                        Set<DefaultPosition> positions = organizationUnit.getPositions();
                        entry.getValue().entrySet().stream().forEach(entry2 -> {
                            boolean noneMatch = positions.stream().anyMatch(position -> position.getEntityId().getId().equals(entry2.getKey()));
                            if (!noneMatch) {
                                DefaultPosition defaultPosition = this.getPositions().stream().filter(pos -> pos.getEntityId().getId().equals(entry2.getKey())).findFirst().orElse(null);
                                if (defaultPosition != null) {
                                    positions.add(defaultPosition);
                                }
                            }
                        });
                        Iterator<DefaultPosition> iterator = positions.iterator();
                        if(entry.getValue() != null && entry.getValue().size() > 0) {
                            while (iterator.hasNext()) {
                                DefaultPosition pos1 = iterator.next();
                                boolean noneMatch = entry.getValue().entrySet().stream().anyMatch(entry3 -> entry3.getKey().equals(pos1.getEntityId().getId()));
                                if (!noneMatch) {
                                    iterator.remove();
                                    break;
                                }
                            }
                        } else {
                            while(iterator.hasNext()) {
                                iterator.next();
                                iterator.remove();
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * Restructure Organization Unit Variant
     * @param organizationUnits
     */
    @Override
    @DomainMethod(event = "RestructureOrganizationUnits")
    public void restructureOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits) {
        //clear all organizations units and add all new organizationUnits, incoming organizationUnits should have their ID as null
        this.getOrganizationUnits().clear();
        this.getOrganizationUnits().addAll(organizationUnits);
    }

    /**
     * Delete Organization Units
     * @param organizationUnits
     */
    @Override
    @DomainMethod(event = "DeleteOrganizationUnits")
    public void deleteOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits) {
        //set organizationUnit passive to true (deleted) and set parent to null
        organizationUnits.stream().forEach(orgUnit -> {orgUnit.setPassive(true);orgUnit.setParent(null);});
    }
}
