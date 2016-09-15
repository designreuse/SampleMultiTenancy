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

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;

/**
 *
 * @author João Paulo
 */
public class MultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    
    private ComboPooledDataSource defaulDataSource;
    
    public MultiTenantConnectionProvider() {
        System.out.println("Olá Mundo!!! - MultiTenantConnectionProvider()");
        defaulDataSource = new ComboPooledDataSource("smt");
        defaulDataSource.setJdbcUrl("jdbc:postgresql://localhost/smt");
        defaulDataSource.setUser("postgres");
        defaulDataSource.setPassword("postgres");
        defaulDataSource.setInitialPoolSize(16);
        defaulDataSource.setMaxConnectionAge(10000);
        try {
            defaulDataSource.setDriverClass("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("[ERRO MultiTenantConnectionProvider()]: "+e);
        }
    }
    
    @Override
    protected DataSource selectAnyDataSource() {
        System.out.println("Olá Mundo!!! - selectAnyDataSource()");
        return defaulDataSource;
    }
    
    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        PooledDataSource pds = C3P0Registry.pooledDataSourceByName(tenantIdentifier);
        if (pds == null) {
            ComboPooledDataSource cpds = new ComboPooledDataSource(tenantIdentifier);
            //cpds.setJdbcUrl("jdbc:postgresql://localhost;databaseName="+tenantIdentifier);
            cpds.setJdbcUrl("jdbc:postgresql://localhost/"+tenantIdentifier);
            cpds.setUser("postgres");
            cpds.setPassword("postgres");
            cpds.setInitialPoolSize(16);
            cpds.setMaxConnectionAge(10000);
            try {
                cpds.setDriverClass("org.postgresql.Driver");
            } catch (Exception e) {
                System.err.println("[ERRO MultiTenantConnectionProvider()]: "+e);
            }
            return cpds;
        }
        return pds;
    }
    
}