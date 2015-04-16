package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.organization.api.rest.resource.DefaultEmailResource;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultEmailResourceBuilder {
    private String email;
    private EmailType emailType;
    private boolean defaultEmail;

    private DefaultEmailResourceBuilder(){ }

    public static DefaultEmailResourceBuilder anEmailResourceBuilder(){
        return new DefaultEmailResourceBuilder();
    }

    public DefaultEmailResourceBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public DefaultEmailResourceBuilder withEmailType(EmailType emailType){
        this.emailType = emailType;
        return this;
    }

    public DefaultEmailResourceBuilder withDefaultEmail(boolean defaultEmail){
        this.defaultEmail = defaultEmail;
        return this;
    }

    public DefaultEmailResource build(){
        DefaultEmailResource email = new DefaultEmailResource();
        email.setEmail(this.email);
        email.setEmailType(emailType);
        email.setDefaultEmail(defaultEmail);
        return email;
    }
}
