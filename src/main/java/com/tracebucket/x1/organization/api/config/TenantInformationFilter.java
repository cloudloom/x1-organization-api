package com.tracebucket.x1.organization.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ffl on 15-06-2015.
 */
@Component
public class TenantInformationFilter extends OncePerRequestFilter {

	private TokenExtractor tokenExtractor = new BearerTokenExtractor();

	@Autowired
	private TokenStore tokenStore;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

			HttpServletRequest req = (HttpServletRequest) httpServletRequest;
			MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);

			// Don't allow access to a resource with no token so clear
			// the security context in case it is actually an OAuth2Authentication
			if (tokenExtractor.extract(httpServletRequest) == null) {
				SecurityContextHolder.clearContext();
			}
			else{
				Authentication authentication = tokenExtractor.extract(httpServletRequest);
				String tenantId = (String) tokenStore.readAccessToken(authentication.getPrincipal().toString()).getAdditionalInformation().get("TENANT_ID");




				mutableRequest.putHeader("tenant_id", tenantId);

			}

			filterChain.doFilter(mutableRequest, httpServletResponse);

	}
}
