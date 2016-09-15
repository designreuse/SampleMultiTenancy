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

import br.com.joaops.smt.dto.SystemUserDto;
import br.com.joaops.smt.dto.SystemUserFormDto;
import br.com.joaops.smt.model.SystemUser;
import br.com.joaops.smt.repository.SystemUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author João
 */
@Service("SystemUserService")
public class SystemUserServiceImpl implements SystemUserService {
    
    @Autowired
    private SystemUserRepository repository;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    public SystemUserFormDto newSystemUser() {
        return new SystemUserFormDto();
    }
    
    @Override
    public SystemUserDto save(SystemUserFormDto sysuser) {
        SystemUser user = new SystemUser();
        user.setId(sysuser.getId());
        user.setFirstName(sysuser.getFirstName());
        user.setMiddleName(sysuser.getMiddleName());
        user.setLastName(sysuser.getLastName());
        user.setEmail(sysuser.getEmail());
        user.setPassword(encoder.encode(sysuser.getPassword()));
        user.setAccountExpiration(sysuser.getAccountExpiration());
        user.setAccountCanExpire(sysuser.getAccountCanExpire());
        user.setLocked(sysuser.getLocked());
        user.setCredentialExpiration(sysuser.getCredentialExpiration());
        user.setCredentialCanExpire(sysuser.getCredentialCanExpire());
        user.setEnabled(sysuser.getEnabled());
        user = repository.save(user);
        SystemUserDto userDto = new SystemUserDto();
        mapper.map(user, userDto);
        return userDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemUserDto getUserByEmail(String email) {
        SystemUser user = repository.findOneByEmail(email);
        SystemUserDto userDto = null;
        if (user != null) {
            userDto = new SystemUserDto();
            mapper.map(user, userDto);
        }
        return userDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemUserDto findOne(Long id) {
        SystemUser user = repository.findOne(id);
        SystemUserDto userDto = null;
        if (user != null) {
            userDto = new SystemUserDto();
            mapper.map(user, userDto);
        }
        return userDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemUserFormDto findOneSystemUserFormDto(Long id) {
        SystemUser user = repository.findOne(id);
        SystemUserFormDto userDto = null;
        if (user != null) {
            userDto = new SystemUserFormDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setMiddleName(user.getMiddleName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setConfirmEmail(user.getEmail());
            userDto.setPassword("");//como decodificar a senha?
            userDto.setConfirmPassword("");
            userDto.setAccountExpiration(user.getAccountExpiration());
            userDto.setAccountCanExpire(user.getAccountCanExpire());
            userDto.setLocked(user.getLocked());
            userDto.setCredentialExpiration(user.getCredentialExpiration());
            userDto.setCredentialCanExpire(user.getCredentialCanExpire());
            userDto.setEnabled(user.getEnabled());
        }
        return userDto;
    }
    
    @Override
    public void delete(SystemUserDto userDto) {
        try {
            SystemUser user = new SystemUser();
            mapper.map(userDto, user);
            repository.delete(user);
        } catch (Exception e) {
        }
    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<SystemUserDto> searchAllUsers(Pageable p) {
        List<SystemUserDto> usersDto = new ArrayList<>();
        Page<SystemUser> systemUsers = repository.findAll(p);
        for (SystemUser systemUser : systemUsers) {
            SystemUserDto userDto = new SystemUserDto();
            mapper.map(systemUser, userDto);
            usersDto.add(userDto);
        }
        Page<SystemUserDto> page = null;
        if (!usersDto.isEmpty()) {
            page = new PageImpl<>(usersDto, p, usersDto.size());
        }
        return page;
    }
    
}