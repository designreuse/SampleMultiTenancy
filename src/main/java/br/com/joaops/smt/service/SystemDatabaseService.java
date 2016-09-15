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
package br.com.joaops.smt.service;

import br.com.joaops.smt.dto.SystemDatabaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author João Paulo
 */
public interface SystemDatabaseService {
    
    public SystemDatabaseDto newSystemDatabase();
    public SystemDatabaseDto save(SystemDatabaseDto systemDatabaseDto);
    public SystemDatabaseDto getUserByName(String name);
    public SystemDatabaseDto findOne(Long id);
    public void delete(Long id);
    public void delete(SystemDatabaseDto systemDatabaseDto);
    public Page<SystemDatabaseDto> searchAllSystemDatabase(Pageable p);
    
}