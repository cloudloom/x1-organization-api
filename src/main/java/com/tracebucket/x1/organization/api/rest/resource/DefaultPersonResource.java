package com.tracebucket.x1.organization.api.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.dictionary.api.domain.PersonType;
import com.tracebucket.x1.dictionary.api.domain.PhoneType;

import java.util.*;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultPersonResource extends BaseResource {
    private String firstName;
    private String lastName;
    private Date birthDay;
    private Gender gender;
    private String image;
    private Map<String, EmailType> emails = new HashMap<String, EmailType>(0);
    private Map<String, PhoneType> phones = new HashMap<String, PhoneType>(0);
    private Set<PersonType> personTypes = new HashSet<PersonType>();
    private boolean defaultContactPerson;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, EmailType> getEmails() {
        return emails;
    }

    public void setEmails(Map<String, EmailType> emails) {
        this.emails = emails;
    }

    public Map<String, PhoneType> getPhones() {
        return phones;
    }

    public void setPhones(Map<String, PhoneType> phones) {
        this.phones = phones;
    }

    public Set<PersonType> getPersonTypes() {
        return personTypes;
    }

    public void setPersonTypes(Set<PersonType> personTypes) {
        this.personTypes = personTypes;
    }

    public boolean isDefaultContactPerson() {
        return defaultContactPerson;
    }

    public void setDefaultContactPerson(boolean defaultContactPerson) {
        this.defaultContactPerson = defaultContactPerson;
    }
}