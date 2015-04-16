package com.tracebucket.x1.organization.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Vishwajit on 16-04-2015.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.tracebucket.x1.organization.api.config"})
public class DefaultOrganizationStarter {
    public static void main(String[] args) {
        SpringApplication.run(DefaultOrganizationStarter.class, args);
    }
}
