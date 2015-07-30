package com.tracebucket.x1.organization.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingI18NBeans;
import com.tracebucket.tron.context.EnableI18N;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sadath on 07-Jul-2015.
 */
@Configuration
@Conditional(value = NonExistingI18NBeans.class)
@EnableI18N
public class I18NConfiguration {
}
