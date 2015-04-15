package com.tracebucket.x1.organization.api.config;

import com.tracebucket.tron.context.EnableDDD;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

/**
 * Created by sadath on 31-Mar-15.
 */
@Configuration
@EnableDDD
public class InfrastructureConfiguration {

}