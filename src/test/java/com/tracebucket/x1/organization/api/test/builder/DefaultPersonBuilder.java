package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.dictionary.api.domain.PersonType;
import com.tracebucket.x1.dictionary.api.domain.PhoneType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;

import java.util.*;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultPersonBuilder {

    private String firstName;
    private String lastName;
    private Date birthDay;
    private Gender gender;
    private String image;
    private Map<String, EmailType> emails = new HashMap<String, EmailType>();
    private Map<String, PhoneType> phones = new HashMap<String, PhoneType>();
    private Set<PersonType> personTypes = new HashSet<PersonType>();

    private DefaultPersonBuilder(){

    }

    public static DefaultPersonBuilder asPerson(){
        return new DefaultPersonBuilder();
    }

    public DefaultPersonBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public DefaultPersonBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public DefaultPersonBuilder withBirthDay(Date birthDay)
    {
        this.birthDay = birthDay;
        return this;
    }

    public DefaultPersonBuilder withGender(Gender gender){
        this.gender = gender;
        return this;
    }

    public DefaultPersonBuilder withImage(String image){
        this.image = image;
        return this;
    }

    public DefaultPersonBuilder withEmails(Map<String, EmailType> emails){
        this.emails = emails;
        return this;
    }

    public DefaultPersonBuilder withPhones(Map<String, PhoneType> phones){
        this.phones = phones;
        return this;
    }

    public DefaultPersonBuilder withPersonTypes(Set<PersonType> personTypes){
        this.personTypes = personTypes;
        return this;
    }

    public DefaultPerson build(){
        DefaultPerson person = new DefaultPerson();
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
