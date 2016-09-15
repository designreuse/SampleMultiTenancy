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
package br.com.joaops.smt.configuration;

import br.com.joaops.smt.dozer.converter.LocaleConverter;
import br.com.joaops.smt.dozer.converter.TimeZoneConverter;
import br.com.joaops.smt.dozer.converter.URLConverter;
import br.com.joaops.smt.dozer.converter.UUIDConverter;
import java.util.ArrayList;
import java.util.List;
import org.dozer.CustomConverter;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author João
 */
@Configuration
public class MapperConfig {
    
    @Bean
    public List<CustomConverter> getCustomConverters() {
        List<CustomConverter> converters = new ArrayList<>();
        converters.add(new LocaleConverter());
        converters.add(new TimeZoneConverter());
        converters.add(new URLConverter());
        converters.add(new UUIDConverter());
        return converters;
    }
    
    @Bean
    public DozerBeanMapperFactoryBean mapper() {
        DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        mapper.setCustomConverters(getCustomConverters());
        return mapper;
    }
    
}