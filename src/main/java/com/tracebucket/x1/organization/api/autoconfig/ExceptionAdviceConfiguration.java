package com.tracebucket.x1.organization.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingExceptionAdviceBeans;
import com.tracebucket.tron.context.EnableExceptionAdvice;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sadath on 15-Jun-2015.
 */
@Configuration
@Conditional(value = NonExistingExceptionAdviceBeans.class)
@EnableExceptionAdvice
public class ExceptionAdviceConfiguration {
}