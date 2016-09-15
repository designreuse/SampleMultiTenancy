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

import br.com.joaops.smt.dto.SystemModuleDto;
import br.com.joaops.smt.service.SystemModuleService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author João Paulo
 */
@Controller
@RequestMapping("system/module")
public class SystemModuleController {
    
    @Autowired
    private SystemModuleService systemModuleService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Pageable p) {
        ModelAndView mav = new ModelAndView("/system/module/index");
        mav.addObject("page", systemModuleService.searchAllModules(p));
        return mav;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/system/module/add");
        mav.addObject("module", systemModuleService.newSystemModule());
        return mav;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid SystemModuleDto module, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView("/system/module/add");
            mav.addObject("module", module);
        } else {
            mav = new ModelAndView("redirect:/system/module/");
            systemModuleService.save(module);
        }
        return mav;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("redirect:/system/module/");
        systemModuleService.delete(id);
        return mav;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/system/module/edit");
        mav.addObject("module", systemModuleService.findOne(id));
        return mav;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@Valid SystemModuleDto module, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView("/system/module/edit");
            mav.addObject("module", module);
        } else {
            mav = new ModelAndView("redirect:/system/module/");
            systemModuleService.save(module);
        }
        return mav;
    }
    
}