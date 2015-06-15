package com.tracebucket.x1.organization.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingControllerAdviceBeans;
import com.tracebucket.tron.context.EnableControllerAdvisor;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sadath on 15-Jun-2015.
 */
@Configuration
@Conditional(value = NonExistingControllerAdviceBeans.class)
@EnableControllerAdvisor
public class ControllerAdviceConfiguration {
}