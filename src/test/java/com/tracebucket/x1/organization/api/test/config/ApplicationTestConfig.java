package com.tracebucket.x1.organization.api.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by Vishwajit on 15-04-2015.
 */
@Configuration
public class ApplicationTestConfig {
    private static final Logger log = LoggerFactory.getLogger(ApplicationTestConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties()
    {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]{new ClassPathResource("jpa-test.properties"), new ClassPathResource("application.properties")};
        configurer.setLocations(resources);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        log.info("properties loaded.");
        return configurer;
    }

}
