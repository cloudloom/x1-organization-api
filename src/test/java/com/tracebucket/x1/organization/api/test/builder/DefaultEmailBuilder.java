package com.tracebucket.x1.organization.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultEmailBuilder {
    private String email;
    private EmailType emailType;

    private DefaultEmailBuilder(){ }

    public static DefaultEmailBuilder anEmailBuilder(){
        return new DefaultEmailBuilder();
    }

    public DefaultEmailBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public DefaultEmailBuilder withEmailType(EmailType emailType){
        this.emailType = emailType;
        return this;
    }

    public DefaultEmail build(){
        DefaultEmail email = new DefaultEmail();
        email.setEmail(this.email);
        email.setEmailType(emailType);
        return email;
    }

}
