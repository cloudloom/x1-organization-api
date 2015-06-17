package com.tracebucket.x1.organization.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingResourceConfigurationBeans;
import com.tracebucket.x1.organization.api.config.TenantInformationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * Created by ffl on 12-03-2015.
 */
@Configuration
@Conditional(value = NonExistingResourceConfigurationBeans.class)
@EnableOAuth2Resource
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private TenantInformationFilter tenantInformationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // For some reason we cant just "permitAll" OPTIONS requests which are needed for CORS support. Spring Security
                // will respond with an HTTP 401 nonetheless.
                // So we just put all other requests types under OAuth control and exclude OPTIONS.
				.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('organization-read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('organization-write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('organization-write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('organization-write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('organization-write')")
                .and()

                        // Add headers required for CORS requests.
                .headers().addHeaderWriter((request, response) -> {
            response.addHeader("Access-Control-Allow-Origin", "*");
            if (request.getMethod().equals("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            }
        });

		http.addFilterAfter(tenantInformationFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }


}
