package com.tracebucket.x1.organization.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingServiceBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by vishwa on 24-11-2014.
 */
@Configuration
@Conditional(value = NonExistingServiceBeans.class)
@ComponentScan(basePackages = {"com.tracebucket.x1.**.api.service.impl"}, scopedProxy = ScopedProxyMode.INTERFACES)
@EnableTransactionManagement(proxyTargetClass = true)
public class ServiceConfiguration {
}