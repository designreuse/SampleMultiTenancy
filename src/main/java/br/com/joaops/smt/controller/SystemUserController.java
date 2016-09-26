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
package br.com.joaops.smt.controller;

import br.com.joaops.smt.dto.SystemUserDto;
import br.com.joaops.smt.dto.SystemUserFormDto;
import br.com.joaops.smt.service.SystemDatabaseService;
import br.com.joaops.smt.service.SystemUserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author João
 */
@Controller
@RequestMapping(value = "system/user")
public class SystemUserController {
    
    @Autowired
    private SystemUserService systemUserService;
    
    @Autowired
    private SystemDatabaseService databaseService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Pageable p) {
        ModelAndView mav = new ModelAndView("/system/user/index");
        Page<SystemUserDto> page = systemUserService.searchAllUsers(p);
        mav.addObject("page", page);
        return mav;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/system/user/add");
        mav.addObject("sysuser", systemUserService.newSystemUser());
        mav.addObject("databases", databaseService.searchAllSystemDatabase());
        return mav;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid SystemUserFormDto sysuser, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView("/system/user/add");
            mav.addObject("sysuser", sysuser);
        } else {
            mav = new ModelAndView("redirect:/system/user/");
            systemUserService.save(sysuser);
        }
        return mav;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("redirect:/system/user/");
        systemUserService.delete(systemUserService.findOne(id));
        return mav;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/system/user/edit");
        mav.addObject("sysuser", systemUserService.findOneSystemUserFormDto(id));
        return mav;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@Valid SystemUserFormDto sysuser, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView("/system/user/edit");
            mav.addObject("sysuser", sysuser);
        } else {
            mav = new ModelAndView("redirect:/system/user/");
            systemUserService.save(sysuser);
        }
        return mav;
    }
    
}