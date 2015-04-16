package com.tracebucket.x1.organization.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.dictionary.api.domain.PhoneType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultPersonResource;
import com.tracebucket.x1.organization.api.test.builder.DefaultPersonResourceBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultPersonResourceFixture {
    public static DefaultPersonResource standardPerson(){
        Map<String, EmailType> emails = new HashMap<String, EmailType>();
        emails.put("sadath@mmp.com", EmailType.PERSONAL);
        Map<String, PhoneType> phones = new HashMap<String, PhoneType>();
        phones.put("9898988889888", PhoneType.MOBILE);
        DefaultPersonResource person = DefaultPersonResourceBuilder.asPerson()
                .withFirstName("Syed")
                .withLastName("Sadath")
                .withGender(Gender.MALE)
                .withBirthDay(new Date())
                .withImage("Image")
                .withEmails(emails)
                .withPhones(phones)
                .build();
        return person;
    }

    public static DefaultPersonResource standardPerson2(){
        Map<String, EmailType> emails = new HashMap<String, EmailType>();
        emails.put("firoz@mmp.com", EmailType.PERSONAL);
        Map<String, PhoneType> phones = new HashMap<String, PhoneType>();
        phones.put("23232323232", PhoneType.MOBILE);
        DefaultPersonResource person = DefaultPersonResourceBuilder.asPerson()
                .withFirstName("Firoz")
                .withLastName("Fazil")
                .withGender(Gender.MALE)
                .withBirthDay(new Date())
                .withImage("Image")
                .withEmails(emails)
                .withPhones(phones)
                .build();
        return person;
    }
}