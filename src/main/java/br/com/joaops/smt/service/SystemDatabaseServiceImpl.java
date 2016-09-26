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

import br.com.joaops.smt.configuration.MultiTenantConnectionProvider;
import br.com.joaops.smt.configuration.MyConnectionProviderImpl;
import br.com.joaops.smt.dto.SystemDatabaseDto;
import br.com.joaops.smt.model.SystemDatabase;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.joaops.smt.repository.SystemDatabaseRepository;
import br.com.joaops.smt.util.DataBaseUtil;

/**
 *
 * @author João Paulo
 */
@Service("SystemDatabaseService")
public class SystemDatabaseServiceImpl implements SystemDatabaseService {
    
    @Autowired
    private SystemDatabaseRepository systemDatabaseRepository;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    public SystemDatabaseDto newSystemDatabase() {
        return new SystemDatabaseDto();
    }
    
    @Override
    public SystemDatabaseDto save(SystemDatabaseDto systemDatabaseDto) {
        SystemDatabase systemDatabase = new SystemDatabase();
        mapper.map(systemDatabaseDto, systemDatabase);
        systemDatabase = systemDatabaseRepository.save(systemDatabase);
        SystemDatabaseDto saved = null;
        if (systemDatabase != null) {
            saved = new SystemDatabaseDto();
            mapper.map(systemDatabase, saved);
            if (!DataBaseUtil.DatabaseExist(saved.getName())) {
                DataBaseUtil.createDatabase(saved);
                MultiTenantConnectionProvider.getConnProviderMap().put(systemDatabase.getName(), new MyConnectionProviderImpl(systemDatabase.getName()));
            }
        }
        return saved;
    }
    
    @Override
    public SystemDatabaseDto getUserByName(String name) {
        SystemDatabase systemDatabase = systemDatabaseRepository.findOneByName(name);
        SystemDatabaseDto found = null;
        if (systemDatabase != null) {
            found = new SystemDatabaseDto();
            mapper.map(systemDatabase, found);
        }
        return found;
    }
    
    @Override
    public SystemDatabaseDto findOne(Long id) {
        SystemDatabase systemDatabase = systemDatabaseRepository.findOne(id);
        SystemDatabaseDto found = null;
        if (systemDatabase != null) {
            found = new SystemDatabaseDto();
            mapper.map(systemDatabase, found);
        }
        return found;
    }
    
    @Override
    public void delete(Long id) {
        systemDatabaseRepository.delete(id);
    }
    
    @Override
    public void delete(SystemDatabaseDto systemDatabaseDto) {
        SystemDatabase systemDatabase = new SystemDatabase();
        mapper.map(systemDatabaseDto, systemDatabase);
        systemDatabaseRepository.delete(systemDatabase);
    }
    
    @Override
    public List<SystemDatabaseDto> searchAllSystemDatabase() {
        List<SystemDatabaseDto> list = new ArrayList<>();
        Iterable<SystemDatabase> databases = systemDatabaseRepository.findAll();
        for (SystemDatabase database : databases) {
            SystemDatabaseDto dto = new SystemDatabaseDto();
            mapper.map(database, dto);
            list.add(dto);
        }
        return list;
    }
    
    @Override
    public Page<SystemDatabaseDto> searchAllSystemDatabase(Pageable p) {
        List<SystemDatabaseDto> list = new ArrayList<>();
        Page<SystemDatabase> systemDatabases = systemDatabaseRepository.findAll(p);
        for (SystemDatabase systemDatabase : systemDatabases) {
            SystemDatabaseDto systemDatabaseDto = new SystemDatabaseDto();
            mapper.map(systemDatabase, systemDatabaseDto);
            list.add(systemDatabaseDto);
        }
        Page<SystemDatabaseDto> page = null;
        if (!list.isEmpty()) {
            page = new PageImpl<>(list, p, list.size());
        }
        return page;
    }
    
}