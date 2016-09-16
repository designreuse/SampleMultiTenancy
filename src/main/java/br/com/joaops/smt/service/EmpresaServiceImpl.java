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

import br.com.joaops.smt.dto.EmpresaDto;
import br.com.joaops.smt.model.Empresa;
import br.com.joaops.smt.repository.EmpresaRepository;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author João Paulo
 */
@Service("EmpresaService")
public class EmpresaServiceImpl implements EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    public EmpresaDto newEmpresa() {
        return new EmpresaDto();
    }
    
    @Override
    public Page<EmpresaDto> searchAllEmpresa(Pageable p) {
        List<EmpresaDto> list = new ArrayList<>();
        Page<Empresa> empresas = empresaRepository.findAll(p);
        for (Empresa empresa : empresas) {
            EmpresaDto dto = new EmpresaDto();
            mapper.map(empresa, dto);
            list.add(dto);
        }
        Page<EmpresaDto> page = null;
        if (!list.isEmpty()) {
            page = new PageImpl<>(list, p, list.size());
        }
        return page;
    }
    
}