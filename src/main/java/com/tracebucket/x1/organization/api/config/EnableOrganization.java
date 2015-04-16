package com.tracebucket.x1.organization.api.config;

import com.tracebucket.x1.organization.api.DefaultOrganizationStarter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Vishwajit on 16-04-2015.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DefaultOrganizationStarter.class})
public @interface EnableOrganization {
}
