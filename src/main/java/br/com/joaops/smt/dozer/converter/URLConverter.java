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
package br.com.joaops.smt.dozer.converter;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 *
 * @author João
 */
public class URLConverter implements CustomConverter {
    
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        java.net.URL source;
        java.net.URL destination = null;        
        if (sourceClass.isInstance(java.net.URL.class)) {
            source = (java.net.URL) sourceFieldValue;
            if (source == null) {
                return null;
            }
            if (destinationClass.isInstance(java.net.URL.class)) {
                if (existingDestinationFieldValue == null) {
                    try {
                        destination = new java.net.URL(source.toString());
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(URLConverter.class.getName()).log(Level.SEVERE, null, ex);
                        throw new MappingException("URLConverter MalformedURLException: " + ex.getLocalizedMessage());
                    }
                } else {
                    throw new MappingException("URLConverter cannot use existing destination field value");
                }
            }
        }
        return destination;
    }
    
}