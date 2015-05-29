package com.tracebucket.x1.organization.api.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by ffl on 28-05-2015.
 */
public class NonExistingJpaBeans implements Condition {
	private static final String JPA_CONFIGURATION_BEAN_NAME = "JPAConfiguration";

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return !context.getBeanFactory().containsBean(JPA_CONFIGURATION_BEAN_NAME);
	}
}