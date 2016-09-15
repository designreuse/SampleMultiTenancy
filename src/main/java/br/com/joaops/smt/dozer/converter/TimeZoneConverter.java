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

import org.dozer.CustomConverter;

/**
 *
 * @author João
 */
public class TimeZoneConverter implements CustomConverter {
    
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        java.util.TimeZone source;
        java.util.TimeZone destination = null;        
        if (sourceClass.isInstance(java.util.TimeZone.class)) {
            source = (java.util.TimeZone) sourceFieldValue;
            if (source == null) {
                return null;
            }
            if (destinationClass.isInstance(java.util.TimeZone.class)) {
                if (existingDestinationFieldValue == null) {
                    destination = (java.util.TimeZone) source.clone();
                } else {
                    destination = (java.util.TimeZone) existingDestinationFieldValue;
                    destination.setID(source.getID());
                }
            }
        }
        return destination;
    }
    
}