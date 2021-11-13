package com.cnokes.infra_service;

import com.cnokes.framework.web.TraceFilter;
import com.cnokes.framework.web.exception.RestExceptionControllerAdvice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { RestExceptionControllerAdvice.class, TraceFilter.class })
public class TempConfig {

}
