package com.spring.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {ApplicationConfiguration.class}; 
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ApplicationConfiguration.class}; 
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
