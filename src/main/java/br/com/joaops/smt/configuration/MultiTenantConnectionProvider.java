/*
 * Copyright (C) 2016 João Paulo
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

import java.util.Arrays;
import java.util.HashMap;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

/**
 *
 * @author João Paulo
 */
public class MultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {
    
    private HashMap<String, ConnectionProvider> connProviderMap = new HashMap<>();
    
    public MultiTenantConnectionProvider() {
        for (String providerName : Arrays.asList(new String[]{"master", "tenant1", "tenant2"})) {
            System.out.println("Creating connection pools: " + providerName);
            connProviderMap.put(providerName, new MyConnectionProviderImpl(providerName));
        }
    }
    
    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connProviderMap.get("master");
    }
    
    @Override
    protected ConnectionProvider selectConnectionProvider(String str) {
        return connProviderMap.get(str) != null ? connProviderMap.get(str) : new MyConnectionProviderImpl("master");
    }
    
}