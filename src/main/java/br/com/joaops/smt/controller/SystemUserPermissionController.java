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
package br.com.joaops.smt.controller;

import br.com.joaops.smt.dto.SystemUserPermissionFormDto;
import br.com.joaops.smt.service.SystemModuleService;
import br.com.joaops.smt.service.SystemUserPermissionService;
import br.com.joaops.smt.service.SystemUserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author João Paulo
 */
@Controller
@RequestMapping("system/permission")
public class SystemUserPermissionController {
    
    @Autowired
    private SystemUserPermissionService systemUserPermissionService;
    
    @Autowired
    private SystemUserService systemUserService;
    
    @Autowired
    private SystemModuleService systemModuleService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Pageable p) {
        ModelAndView mav = new ModelAndView("/system/permission/index");
        mav.addObject("page", systemUserPermissionService.searchAllUsersPermissions(p));
        return mav;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response, Pageable p) {
        ModelAndView mav = new ModelAndView("/system/permission/add");
        mav.addObject("permission", systemUserPermissionService.newSystemUserPermission());
        mav.addObject("modules", systemModuleService.searchAllModules(p));//p ? mais que 10?
        mav.addObject("users", systemUserService.searchAllUsers(p));
        return mav;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(SystemUserPermissionFormDto permissionDto, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("redirect:/system/permission");
        systemUserPermissionService.save(permissionDto);
        return mav;
    }
    
    @RequestMapping(value = "/delete/{idUser}/{idModule}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long idUser, @PathVariable Long idModule, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("redirect:/system/permission/");
        //SystemUserPermissionIdDto id = new SystemUserPermissionIdDto();
        //id.setSystemUser(systemUserService.findOne(idUser));
        //id.setSystemModule(systemModuleService.findOne(idModule));
        //systemUserPermissionService.delete(id);
        
        //systemUserPermissionService.delete(systemUserPermissionService.findOne(idUser, idModule));
        systemUserPermissionService.delete(idUser, idModule);
        return mav;
    }
    
}