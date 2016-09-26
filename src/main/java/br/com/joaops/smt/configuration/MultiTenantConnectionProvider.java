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

import br.com.joaops.smt.dto.SystemDatabaseDto;
import br.com.joaops.smt.service.SystemDatabaseService;
import br.com.joaops.smt.util.DataBaseUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author João Paulo
 */
public class MultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {
    
    private static HashMap<String, ConnectionProvider> connProviderMap = new HashMap<>();
    
    public MultiTenantConnectionProvider() {
        List<String> databases = DataBaseUtil.getListDatabaseFromMaster(); //Pegar a Lista de BDs a partir do BD master
        for (String providerName : databases) {
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
    
    public static HashMap<String, ConnectionProvider> getConnProviderMap() {
        return connProviderMap;
    }
    
}