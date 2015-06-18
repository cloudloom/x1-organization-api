package com.tracebucket.x1.organization.api.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.EmailType;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultEmailResource extends BaseResource {
    @Size(min = 1, max = 250)
    @Email
    private String email;

    private EmailType emailType;
    private boolean defaultEmail;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public boolean isDefaultEmail() {
        return defaultEmail;
    }

    public void setDefaultEmail(boolean defaultEmail) {
        this.defaultEmail = defaultEmail;
    }
}
