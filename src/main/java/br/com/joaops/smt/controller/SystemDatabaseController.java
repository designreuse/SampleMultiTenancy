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

import br.com.joaops.smt.dto.SystemDatabaseDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import br.com.joaops.smt.service.SystemDatabaseService;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author João Paulo
 */
@Controller
@RequestMapping("system/database")
public class SystemDatabaseController {
    
    @Autowired
    private SystemDatabaseService systemDatabaseService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Pageable p) {
        ModelAndView mav = new ModelAndView("/system/database/index");
        mav.addObject("page", systemDatabaseService.searchAllSystemDatabase(p));
        return mav;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/system/database/add");
        mav.addObject("database", systemDatabaseService.newSystemDatabase());
        return mav;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid SystemDatabaseDto database, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView("/system/database/add");
            mav.addObject("database", database);
        } else {
            mav = new ModelAndView("redirect:/system/database/");
            systemDatabaseService.save(database);
        }
        return mav;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("redirect:/system/database/");
        systemDatabaseService.delete(id);
        return mav;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/system/database/edit");
        mav.addObject("database", systemDatabaseService.findOne(id));
        return mav;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@Valid SystemDatabaseDto database, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView("/system/database/edit");
            mav.addObject("database", database);
        } else {
            mav = new ModelAndView("redirect:/system/database/");
            systemDatabaseService.save(database);
        }
        return mav;
    }
    
}