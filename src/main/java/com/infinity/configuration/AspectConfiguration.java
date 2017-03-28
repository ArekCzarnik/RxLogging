package com.infinity.configuration;

import com.infinity.aspects.LoggingAspect;
import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@Configuration
public class AspectConfiguration {

    @Bean
    public LoggingAspect loggingAspect() {
        return Aspects.aspectOf(LoggingAspect.class);
    }

}
