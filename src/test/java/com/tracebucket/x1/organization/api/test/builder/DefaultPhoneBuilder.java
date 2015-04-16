package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.PhoneType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultPhoneBuilder {
    private Long number;
    private Integer extension;
    private PhoneType phoneType;
    private boolean defaultPhone;

    private DefaultPhoneBuilder(){ }

    public static DefaultPhoneBuilder aPhoneBuilder(){
        return new DefaultPhoneBuilder();
    }

    public DefaultPhoneBuilder withNumber(Long number){
        this.number = number;
        return this;
    }

    public DefaultPhoneBuilder withExtension(Integer extension){
        this.extension = extension;
        return this;
    }

    public DefaultPhoneBuilder withPhoneType(PhoneType phoneType){
        this.phoneType = phoneType;
        return this;
    }

    public DefaultPhoneBuilder withDefaultPhonee(boolean defaultPhone){
        this.defaultPhone = defaultPhone;
        return this;
    }

    public DefaultPhone build() {
        DefaultPhone phone = new DefaultPhone();
        phone.setNumber(number);
        phone.setExtension(extension);
        phone.setPhoneType(phoneType);
        phone.setDefaultPhone(defaultPhone);
        return phone;
    }
}
