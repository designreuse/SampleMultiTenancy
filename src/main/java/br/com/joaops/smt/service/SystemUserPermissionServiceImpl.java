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

import br.com.joaops.smt.dto.SystemUserPermissionDto;
import br.com.joaops.smt.dto.SystemUserPermissionFormDto;
import br.com.joaops.smt.model.SystemUserPermission;
import br.com.joaops.smt.model.SystemUserPermissionId;
import br.com.joaops.smt.repository.SystemModuleRepository;
import br.com.joaops.smt.repository.SystemUserPermissionRepository;
import br.com.joaops.smt.repository.SystemUserRepository;
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
@Service("SystemUserPermissionService")
public class SystemUserPermissionServiceImpl implements SystemUserPermissionService {
    
    @Autowired
    private SystemUserPermissionRepository repository;
    
    @Autowired
    private SystemUserRepository userRepository;
    
    @Autowired
    private SystemModuleRepository moduleRepository;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    public SystemUserPermissionFormDto newSystemUserPermission() {
        return new SystemUserPermissionFormDto();
    }
    
    @Override
    public SystemUserPermissionDto save(SystemUserPermissionFormDto permissionDto) {
        SystemUserPermission permission = new SystemUserPermission();
        
        SystemUserPermissionId permissionId = new SystemUserPermissionId();
        permissionId.setSystemUser(userRepository.findOne(permissionDto.getIdUser()));
        permissionId.setSystemModule(moduleRepository.findOne(permissionDto.getIdModule()));
        
        permission.setSystemUserPermissionId(permissionId);
        permission.setRead(permissionDto.getRead());
        permission.setAdd(permissionDto.getAdd());
        permission.setEdit(permissionDto.getEdit());
        permission.setDelete(permissionDto.getDelete());
        
        permission = repository.save(permission);
        SystemUserPermissionDto novo = new SystemUserPermissionDto();
        if (permission != null) {
            mapper.map(permission, novo);
        }
        return novo;
    }
    
    @Override
    public void delete(Long idUser, Long idModule) {
        SystemUserPermissionId permissionId = new SystemUserPermissionId();
        permissionId.setSystemUser(userRepository.findOne(idUser));
        permissionId.setSystemModule(moduleRepository.findOne(idModule));
        repository.delete(permissionId);
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemUserPermissionDto findOne(Long idUser, Long idModule) {
        SystemUserPermission permission = new SystemUserPermission();
        SystemUserPermissionId permissionId = new SystemUserPermissionId();
        permissionId.setSystemUser(userRepository.findOne(idUser));
        permissionId.setSystemModule(moduleRepository.findOne(idModule));
        permission = repository.findOne(permissionId);
        SystemUserPermissionDto permissionDto = new SystemUserPermissionDto();
        mapper.map(permission, permissionDto);
        return permissionDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<SystemUserPermissionDto> searchAllUsersPermissions(Pageable p) {
        List<SystemUserPermissionDto> permissionDtos = new ArrayList<>();
        Page<SystemUserPermission> permissions = repository.findAll(p);
        for (SystemUserPermission permission : permissions) {
            SystemUserPermissionDto permissionDto = new SystemUserPermissionDto();
            mapper.map(permission, permissionDto);
            permissionDtos.add(permissionDto);
        }
        Page<SystemUserPermissionDto> page = null;
        if (!permissionDtos.isEmpty()) {
            page = new PageImpl<>(permissionDtos, p, permissionDtos.size());
        }
        return page;
    }
    
}