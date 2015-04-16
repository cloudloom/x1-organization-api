package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.dictionary.api.domain.PersonType;
import com.tracebucket.x1.dictionary.api.domain.PhoneType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultPersonResource;

import java.util.*;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultPersonResourceBuilder {
    private String firstName;
    private String lastName;
    private Date birthDay;
    private Gender gender;
    private String image;
    private Map<String, EmailType> emails = new HashMap<String, EmailType>();
    private Map<String, PhoneType> phones = new HashMap<String, PhoneType>();
    private Set<PersonType> personTypes = new HashSet<PersonType>();

    private DefaultPersonResourceBuilder(){

    }

    public static DefaultPersonResourceBuilder asPerson(){
        return new DefaultPersonResourceBuilder();
    }

    public DefaultPersonResourceBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public DefaultPersonResourceBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public DefaultPersonResourceBuilder withBirthDay(Date birthDay)
    {
        this.birthDay = birthDay;
        return this;
    }

    public DefaultPersonResourceBuilder withGender(Gender gender){
        this.gender = gender;
        return this;
    }

    public DefaultPersonResourceBuilder withImage(String image){
        this.image = image;
        return this;
    }

    public DefaultPersonResourceBuilder withEmails(Map<String, EmailType> emails){
        this.emails = emails;
        return this;
    }

    public DefaultPersonResourceBuilder withPhones(Map<String, PhoneType> phones){
        this.phones = phones;
        return this;
    }

    public DefaultPersonResourceBuilder withPersonTypes(Set<PersonType> personTypes){
        this.personTypes = personTypes;
        return this;
    }

    public DefaultPersonResource build(){
        DefaultPersonResource person = new DefaultPersonResource();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDay(birthDay);
        person.setGender(gender);
        person.setImage(image);
        person.setEmails(emails);
        person.setPhones(phones);
        person.setPersonTypes(personTypes);
        return person;
    }
}