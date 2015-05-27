package com.tracebucket.x1.organization.api.config;

import com.tracebucket.tron.context.EnableAutoAssemblerResolution;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vishwajit on 16-04-2015.
 */
@Configuration(value = "x1OrganizationAssemblerConfig")
@EnableAutoAssemblerResolution(basePackages = {"com.tracebucket.x1.organization.api.rest.assembler"})
public class AssemblerConfiguration {
}