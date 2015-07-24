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


    @Override
    @DomainMethod(event = "BaseCurrencyAdded")
    public void addBaseCurrency(DefaultCurrency baseCurrency) {
        if(baseCurrency != null) {
            baseCurrency.setCurrencyType(CurrencyType.BASE);
        }
        this.currencies.add(baseCurrency);
    }

    @Override
    @DomainMethod(event = "TimezoneAdded")
    public void addTimezone(DefaultTimezone timezone) {
        this.timezones.add(timezone);
    }

    @Override
    @DomainMethod(event = "OrganizationUnitAdded")
    public void addOrganizationUnit(DefaultOrganizationUnit organizationUnit) {
        if(organizationUnit != null) {
            organizationUnit.setOrganization(this);
            this.organizationUnits.add(organizationUnit);
        }
    }

    @Override
    @DomainMethod(event = "OrganizationUnitUpdated")
    public void updateOrganizationUnit(DefaultOrganizationUnit organizationUnit, Mapper mapper) {
        if(organizationUnit != null) {
            DefaultOrganizationUnit organizationUnitFetched = organizationUnits.stream()
                    .filter(t -> t.getId().equals(organizationUnit.getId()))
                    .findFirst()
                    .orElse(null);
            if(organizationUnitFetched != null) {
                organizationUnitFetched.getAddresses().clear();
                if(organizationUnit.getAddresses() != null) {
                    organizationUnitFetched.getAddresses().addAll(organizationUnit.getAddresses());
                }
                organizationUnitFetched.getPhones().clear();
                if(organizationUnit.getPhones() != null) {
                    organizationUnitFetched.getPhones().addAll(organizationUnit.getPhones());
                }
                organizationUnitFetched.getEmails().clear();
                if(organizationUnit.getEmails() != null) {
                    organizationUnitFetched.getEmails().addAll(organizationUnit.getEmails());
                }
                organizationUnitFetched.setName(organizationUnit.getName());
                organizationUnitFetched.setDescription(organizationUnit.getDescription());
                //mapper.map(organizationUnit, organizationUnitFetched);
                organizationUnitFetched.setOrganization(this);
            }
        }
    }

    @Override
    @DomainMethod(event = "OrganizationUnitBelowAdded")
    public void addOrganizationUnitBelow(DefaultOrganizationUnit organizationUnit, DefaultOrganizationUnit parentOrganizationUnit) {
        OrganizationUnit parentOrganizationUnitFetched = organizationUnits.stream()
                .filter(t -> t.getId().equals(parentOrganizationUnit.getId()))
                .findFirst()
                .orElse(null);
        if(parentOrganizationUnitFetched != null) {
            parentOrganizationUnitFetched.addChild(organizationUnit);
        }
    }

    @Override
    @DomainMethod(event = "ContactPersonAdded")
    public void addContactPerson(DefaultPerson contactPerson) {
        if(contactPerson != null) {
            contactPerson.setDefaultContactPerson(false);
            this.contactPersons.add(contactPerson);
        }
    }

    @Override
    @DomainMethod(event = "DefaultContactPersonSet")
    public void setDefaultContactPerson(DefaultPerson defaultContactPerson) {
        if(defaultContactPerson != null) {
            Stream<DefaultPerson> stream = this.contactPersons.stream();
            stream.forEach(t -> t.setDefaultContactPerson(false));
            defaultContactPerson.setDefaultContactPerson(true);
            this.contactPersons.add(defaultContactPerson);
        }
    }

    @Override
    @DomainMethod(event = "ContactNumberAdded")
    public void addContactNumber(DefaultPhone phone) {
        if(phone != null) {
            phone.setDefaultPhone(false);
            this.phones.add(phone);
        }
    }

    @Override
    @DomainMethod(event = "DefaultContactNumberSet")
    public void setDefaultContactNumber(DefaultPhone defaultContactNumber) {
        if(defaultContactNumber != null) {
            Stream<DefaultPhone> stream = this.phones.stream();
            stream.forEach(t -> t.setDefaultPhone(false));
            defaultContactNumber.setDefaultPhone(true);
            this.phones.add(defaultContactNumber);
        }
    }

    @Override
    @DomainMethod(event = "EmailAdded")
    public void addEmail(DefaultEmail email) {
        if(email != null) {
            email.setDefaultEmail(false);
            this.emails.add(email);
        }
    }

    @Override
    @DomainMethod(event = "DefaultEmailSet")
    public void setDefaultEmail(DefaultEmail defaultEmail) {
        if(defaultEmail != null) {
            Stream<DefaultEmail> stream = this.emails.stream();
            stream.forEach(t -> t.setDefaultEmail(false));
            defaultEmail.setDefaultEmail(true);
            this.emails.add(defaultEmail);
        }
    }

    @Override
    @DomainMethod(event = "HeadOfficeSet")
    public void setHeadOffice(DefaultAddress headOfficeAddress) {
        if(headOfficeAddress != null) {
            headOfficeAddress.setAddressType(AddressType.HEAD_OFFICE);
            headOfficeAddress.setDefaultAddress(true);
            this.addresses.add(headOfficeAddress);
        }
    }

    @Override
    @DomainMethod(event = "HeadOfficeMovedTo")
    public void moveHeadOfficeTo(DefaultAddress newHeadOfficeAddress) {
        if(newHeadOfficeAddress != null) {
            Stream<DefaultAddress> stream = this.addresses.stream().filter(t -> t.getAddressType() == AddressType.HEAD_OFFICE);
            stream.forEach(t -> t.setDefaultAddress(false));
            newHeadOfficeAddress.setAddressType(AddressType.HEAD_OFFICE);
            newHeadOfficeAddress.setDefaultAddress(true);
            this.addresses.add(newHeadOfficeAddress);
        }
    }

    @Override
    @DomainMethod(event = "AddPosition")
    public void addPosition(DefaultPosition position) {
        if(position != null) {
            this.positions.add(position);
        }
    }

    @Override
    @DomainMethod(event = "AddPositionBelow")
    public void addPositionBelow(DefaultPosition position, DefaultPosition parentPosition) {
        DefaultPosition parentPositionFetched = positions.stream()
                .filter(t -> t.getId().equals(parentPosition.getEntityId().getId()))
                .findFirst()
                .orElse(null);
        if(parentPositionFetched != null) {
            parentPositionFetched.addChild(position);
        }
    }

    @Override
    @DomainMethod(event = "UpdatePosition")
    public void updatePosition(DefaultPosition position, Mapper mapper) {
        if(position != null) {
            DefaultPosition positionFetched = this.positions.stream()
                    .filter(t -> t.getEntityId().getId().equals(position.getEntityId().getId()))
                    .findFirst()
                    .orElse(null);
            if(positionFetched != null) {
                mapper.map(position, positionFetched);
            }
        }
    }

    @Override
    @DomainMethod(event = "AddPositionToOrganizationUnit")
    public void addPositionToOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultPosition> position) {
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        if(organizationUnits != null) {
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            if(fetchedOrganizationUnit != null) {
                fetchedOrganizationUnit.getPositions().addAll(position);
            }
        }
    }

    @Override
    @DomainMethod(event = "UpdatePositionsOfOrganizationUnit")
    public void updatePositionsOfOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultPosition> position) {
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        if(organizationUnits != null) {
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            if(fetchedOrganizationUnit != null) {
                Set<DefaultPosition> positions1 = fetchedOrganizationUnit.getPositions();
                if(positions1 != null) {
                    Iterator<DefaultPosition> iterator = positions1.iterator();
                    while(iterator.hasNext()) {
                        DefaultPosition p =iterator.next();
                            if (position != null && !position.contains(p)) {
                                positions1.remove(p);
                            }
                    }
                }
                if(position != null) {
                    Iterator<DefaultPosition> iterator = position.iterator();
                    while(iterator.hasNext()){
                        DefaultPosition p = iterator.next();
                        if(!positions1.contains(p)) {
                            positions1.add(p);
                        }
                    };
                }
            }

        }
    }

    @Override
    public Set<DefaultPosition> getPositionsOfOrganizationUnit(EntityId organizationUnitEntityId) {
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        if(organizationUnits != null) {
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            if(fetchedOrganizationUnit != null) {
                return fetchedOrganizationUnit.getPositions();
            }
        }
        return null;
    }

    @Override
    @DomainMethod(event = "AddDepartmentToOrganization")
    public void addDepartmentToOrganization(Set<DefaultDepartment> departments) {
        if(departments != null && departments.size() > 0) {
            this.departments.addAll(departments);
        }
    }

    @Override
    @DomainMethod(event = "UpdateDepartmentOfOrganization")
    public void updateDepartmentOfOrganization(Set<DefaultDepartment> departments, Mapper mapper) {
        if(departments != null && departments.size() > 0) {
            departments.stream().forEach(department -> {
                if(this.departments != null && this.departments.size() > 0) {
                    DefaultDepartment fetchedDepartment = this.departments.stream()
                            .filter(t -> t.getEntityId().getId().equals(department.getEntityId().getId()))
                            .findFirst()
                            .orElse(null);
                    if(fetchedDepartment != null) {
                        mapper.map(department, fetchedDepartment);
                    }
                }
            });
        }
    }

    @Override
    public Set<DefaultDepartment> getDepartmentsOfOrganization() {
        return this.departments;
    }

    @Override
    @DomainMethod(event = "AddDepartmentToOrganizationUnit")
    public void addDepartmentToOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultDepartment> departments) {
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        if(organizationUnits != null) {
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            if(fetchedOrganizationUnit != null) {
                if(departments != null && departments.size() > 0) {
                    fetchedOrganizationUnit.getDepartments().addAll(departments);
                }
            }
        }
    }

    @Override
    @DomainMethod(event = "UpdateDepartmentOfOrganizationUnit")
    public void updateDepartmentOfOrganizationUnit(EntityId organizationUnitEntityId, Set<DefaultDepartment> departments) {
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        if(organizationUnits != null) {
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            if(fetchedOrganizationUnit != null) {
                Set<DefaultDepartment> departments1 = fetchedOrganizationUnit.getDepartments();
                if(departments1 != null) {
                    Iterator<DefaultDepartment> iterator = departments1.iterator();
                    while(iterator.hasNext()) {
                        DefaultDepartment d =iterator.next();
                        if (departments != null && !departments.contains(d)) {
                            departments1.remove(d);
                        }
                    }
                }
                if(departments != null) {
                    Iterator<DefaultDepartment> iterator = departments.iterator();
                    while(iterator.hasNext()){
                        DefaultDepartment d = iterator.next();
                        if(!departments1.contains(d)) {
                            departments1.add(d);
                        }
                    };
                }
            }

        }
    }

    @Override
    public Set<DefaultDepartment> getDepartmentsOfOrganizationUnit(EntityId organizationUnitEntityId) {
        Set<DefaultOrganizationUnit> organizationUnits = this.organizationUnits;
        if(organizationUnits != null) {
            DefaultOrganizationUnit fetchedOrganizationUnit = organizationUnits.stream()
                    .filter(organizationUnit -> organizationUnit.getEntityId().getId().equals(organizationUnitEntityId.getId()))
                    .findFirst()
                    .orElse(null);
            if(fetchedOrganizationUnit != null) {
                return fetchedOrganizationUnit.getDepartments();
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public DefaultAddress getHeadOfficeAddress() {
        DefaultAddress headOfficeAddress =
                addresses.stream()
                        .filter(t -> t.getAddressType() == AddressType.HEAD_OFFICE)
                        .filter(t -> t.isDefaultAddress())
                        .findFirst()
                        .orElse(null);
        return headOfficeAddress;
    }

    @Override
    public Set<DefaultCurrency> getBaseCurrencies() {
        Set<DefaultCurrency> baseCurrencies = new HashSet<DefaultCurrency>();
        if(currencies != null && currencies.size() > 0) {
            for (DefaultCurrency currency : currencies) {
                if(currency.getCurrencyType().equals(CurrencyType.BASE)) {
                    baseCurrencies.add(currency);
                }
            }
        }
        return baseCurrencies;
    }

    @Override
    public Set<DefaultOrganizationUnit> getOrganizationUnits() {
        return this.organizationUnits;
    }

    @Override
    public Set<DefaultPhone> getContactNumbers() {
        return this.phones;
    }

    @Override
    public Set<DefaultEmail> getEmails() {
        return this.emails;
    }

    @Override
    public Set<DefaultTimezone> getTimezones() {
        return this.timezones;
    }

    @Override
    public Set<DefaultPerson> getContactPersons() {
        return contactPersons;
    }

    @Override
    public Set<DefaultPhone> getPhones() {
        return phones;
    }

    @Override
    public Set<DefaultAddress> getAddresses() {
        return addresses;
    }

    @Override
    public Set<DefaultCurrency> getCurrencies() {
        return currencies;
    }

    @Override
    public Set<DefaultPosition> getPositions() {
        return this.positions;
    }

    @Override
    public DefaultPosition getPosition(EntityId positionEntityId) {
        DefaultPosition position =
                positions.stream()
                        .filter(t -> t.getEntityId().getId().equals(positionEntityId.getId()))
                        .findFirst()
                        .orElse(null);
        return position;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void setOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }

    @Override
    public void setAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void setCurrencies(Set<DefaultCurrency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public void setTimezones(Set<DefaultTimezone> timezones) {
        this.timezones = timezones;
    }

    @Override
    public void setContactPersons(Set<DefaultPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    @Override
    public void setPhones(Set<DefaultPhone> phones) {
        this.phones = phones;
    }

    @Override
    public void setEmails(Set<DefaultEmail> emails) {
        this.emails = emails;
    }

    @Override
    @DomainMethod(event = "UpdateOrganizationUnitPositions")
    public void updateOrganizationUnitPositions(DefaultOrganizationUnit organizationUnit) {
        DefaultOrganizationUnit fetchedOrganizationUnit = this.organizationUnits.stream().filter(orgUnit -> orgUnit.getEntityId().getId().equals(organizationUnit.getEntityId().getId()))
                .findFirst()
                .orElse(null);
        if(fetchedOrganizationUnit != null) {
            Set<DefaultPosition> positions = fetchedOrganizationUnit.getPositions();
            Set<DefaultPosition> newPositions = organizationUnit.getPositions();
            newPositions.stream().forEach(entry2 -> {
                boolean noneMatch = positions.stream().anyMatch(position -> position.getEntityId().getId().equals(entry2.getEntityId().getId()));
                if (!noneMatch) {
                    DefaultPosition defaultPosition = this.getPositions().stream().filter(pos -> pos.getEntityId().getId().equals(entry2.getEntityId().getId())).findFirst().orElse(null);
                    if (defaultPosition != null) {
                        positions.add(defaultPosition);
                    }
                }
            });
            Iterator<DefaultPosition> iterator = positions.iterator();
            while (iterator.hasNext()) {
                DefaultPosition pos1 = iterator.next();
                boolean noneMatch = newPositions.stream().anyMatch(entry3 -> entry3.getEntityId().getId().equals(pos1.getEntityId().getId()));
                if (!noneMatch) {
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

    @Override
    @DomainMethod(event = "RestructureOrganizationUnits")
    public void restructureOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits) {
        this.getOrganizationUnits().clear();
        this.getOrganizationUnits().addAll(organizationUnits);
    }

    @Override
    @DomainMethod(event = "DeleteOrganizationUnits")
    public void deleteOrganizationUnits(Set<DefaultOrganizationUnit> organizationUnits) {
        organizationUnits.stream().forEach(orgUnit -> {orgUnit.setPassive(true);orgUnit.setParent(null);});
    }
}
