package com.tracebucket.x1.organization.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vishwajit on 16-04-2015.
 */
@Configuration(value = "x1OrganizationWebConfig")
@ComponentScan(basePackages = {"com.tracebucket.x1.organization.api.rest"})
public class WebConfiguration {
}