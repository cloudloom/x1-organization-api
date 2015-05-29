package com.tracebucket.x1.organization.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingInfrastructureBeans;
import com.tracebucket.tron.context.EnableDDD;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sadath on 31-Mar-15.
 */
@Configuration
@Conditional(value = NonExistingInfrastructureBeans.class)
@EnableDDD
public class InfrastructureConfiguration {
}