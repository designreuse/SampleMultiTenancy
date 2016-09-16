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

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author João Paulo
 */
public class MyConnectionProviderImpl implements ConnectionProvider {
    
    private DriverManagerDataSource dataSource;
    
    public MyConnectionProviderImpl(String database) {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost/"+database);
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    @Override
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
    
    @Override
    public boolean supportsAggressiveRelease() {
        return Boolean.FALSE;
    }
    
    @Override
    public boolean isUnwrappableAs(Class type) {
        return Boolean.FALSE;
    }
    
    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
    
}