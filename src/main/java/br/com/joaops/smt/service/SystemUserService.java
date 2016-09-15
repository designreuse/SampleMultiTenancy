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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author João
 */
public interface SystemUserService {
    
    public SystemUserFormDto newSystemUser();
    public SystemUserDto save(SystemUserFormDto sysuser);
    public SystemUserDto getUserByEmail(String email);
    public SystemUserDto findOne(Long id);
    public SystemUserFormDto findOneSystemUserFormDto(Long id);
    public void delete(SystemUserDto userDto);
    public Page<SystemUserDto> searchAllUsers(Pageable p);
    
}