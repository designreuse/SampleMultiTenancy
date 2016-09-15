/*
 * Copyright (C) 2016 João
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.joaops.smt;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author João
 */
public class SmtApplicationInitializer implements WebApplicationInitializer {
    
    private static final String APPLICATION_NAME = "Sample Multi Tenancy";
    private static final String CONFIG_LOCATION = "br.com.joaops.smt.configuration";
    private static final String MAPPING_URL = "/*";
    
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        WebApplicationContext context = getContext();
        sc.addListener(new ContextLoaderListener(context));
        sc.addFilter("CharacterEncodingFilter", getCharacterEncodingFilter()).addMappingForUrlPatterns(null, true, MAPPING_URL);
        sc.addFilter("RequestContextFilter", getRequestContextFilter()).addMappingForUrlPatterns(null, true, MAPPING_URL);
        sc.addFilter("OpenEntityManagerInViewFilter", getOpenEntityManagerInViewFilter()).addMappingForUrlPatterns(null, true, MAPPING_URL);
        sc.addFilter("securityFilter", getDelegatingFilterProxy()).addMappingForUrlPatterns(null, true, MAPPING_URL);
        ServletRegistration.Dynamic dispatcher = sc.addServlet("LoversBookServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.setAsyncSupported(Boolean.TRUE);
        dispatcher.addMapping(MAPPING_URL);
    }
    
    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setDisplayName(APPLICATION_NAME);
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }
    
    private CharacterEncodingFilter getCharacterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
    
    private RequestContextFilter getRequestContextFilter() {
        RequestContextFilter requestContextFilter = new RequestContextFilter();
        return requestContextFilter;
    }
    
    private OpenEntityManagerInViewFilter getOpenEntityManagerInViewFilter() {
        OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
        openEntityManagerInViewFilter.setEntityManagerFactoryBeanName("entityManagerFactory");
        return openEntityManagerInViewFilter;
    }
    
    private DelegatingFilterProxy getDelegatingFilterProxy() {
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("springSecurityFilterChain");
        return delegatingFilterProxy;
    }
    
}