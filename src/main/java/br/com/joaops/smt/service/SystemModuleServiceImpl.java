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
package br.com.joaops.smt.service;

import br.com.joaops.smt.dto.SystemModuleDto;
import br.com.joaops.smt.model.SystemModule;
import br.com.joaops.smt.repository.SystemModuleRepository;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author João
 */
@Service("SystemModuleService")
public class SystemModuleServiceImpl implements SystemModuleService {
    
    @Autowired
    private SystemModuleRepository repository;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    public SystemModuleDto newSystemModule() {
        return new SystemModuleDto();
    }
    
    @Override
    public SystemModuleDto save(SystemModuleDto moduleDto) {
        SystemModule module = new SystemModule();
        mapper.map(moduleDto, module);
        module = repository.save(module);
        SystemModuleDto novo = new SystemModuleDto();
        if (module != null) {
            mapper.map(module, novo);
        }
        return novo;
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemModuleDto findOne(Long id) {
        SystemModule module = repository.findOne(id);
        SystemModuleDto moduleDto = new SystemModuleDto();
        mapper.map(module, moduleDto);
        return moduleDto;
    }
    
    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<SystemModuleDto> searchAllModules(Pageable p) {
        List<SystemModuleDto> moduleDtos = new ArrayList<>();
        Page<SystemModule> modules = repository.findAll(p);
        for (SystemModule module : modules) {
            SystemModuleDto moduleDto = new SystemModuleDto();
            mapper.map(module, moduleDto);
            moduleDtos.add(moduleDto);
        }
        Page<SystemModuleDto> page = null;
        if (!moduleDtos.isEmpty()) {
            page = new PageImpl<>(moduleDtos, p, moduleDtos.size());
        }
        return page;
    }
    
}