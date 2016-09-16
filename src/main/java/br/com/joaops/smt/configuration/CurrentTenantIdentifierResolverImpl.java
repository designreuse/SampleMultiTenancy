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

import br.com.joaops.smt.security.SmtUserDetails;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author João Paulo
 */
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    
    @Override
    public String resolveCurrentTenantIdentifier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String database = "master"; //nome do BD
        //Não está conseguindo retornar a autenticação, é retornado null todas as vezes!
        if (authentication != null && authentication.getPrincipal() instanceof SmtUserDetails) {
            SmtUserDetails user = (SmtUserDetails) authentication.getPrincipal();
            database = user.getDatabaseName();
        }
        return database;
    }
    
    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
    
}