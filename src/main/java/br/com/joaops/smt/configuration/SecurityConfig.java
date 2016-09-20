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
package br.com.joaops.smt.configuration;

import br.com.joaops.smt.security.SmtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 *
 * @author João
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final String defaultSecret = "BQG^@MTMG948xCvQD^x$q?mwDkS2qz=yBkHRe_3tyGCY_?P4jMQ^!w^=j$S&2K9Q5VQV3uD5Grd*DVpc+jHzDFEUCc+X2vXPjbK4rt&GQxnbAVxqWh_N#Ew%eh4h#X9WfT$tfYN*7!rjzeEHj&DDV5R58Rv3kUrrk&qDZ3z=Y*!rQh?YRWU-8xP&mdqRD*h3S?GMEv?W&nS$LyTGv36ffGKyYeZC7Xm3CfWAXwHp^CYxgs5@Fw6Q9AHJBUTGzfC?";
    
    @Autowired
    private SmtAuthenticationProvider authenticatorProvider;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.eraseCredentials(true);
        auth.authenticationProvider(authenticatorProvider);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/login/*").permitAll()
                .antMatchers("/logout/*").permitAll()
                .antMatchers("/system/user").hasRole("SYSTEM_USER_READ")
                .antMatchers("/system/user/add").hasRole("SYSTEM_USER_ADD")
                .antMatchers("/system/user/save").hasRole("SYSTEM_USER_ADD")
                .antMatchers("/system/user/edit/*").hasRole("SYSTEM_USER_EDIT")
                .antMatchers("/system/user/update").hasRole("SYSTEM_USER_EDIT")
                .antMatchers("/system/user/delete/*").hasRole("SYSTEM_USER_DELETE")
                .antMatchers("/system/module").hasRole("SYSTEM_MODULE_READ")
                .antMatchers("/system/module/add").hasRole("SYSTEM_MODULE_ADD")
                .antMatchers("/system/module/save").hasRole("SYSTEM_MODULE_ADD")
                .antMatchers("/system/module/edit/*").hasRole("SYSTEM_MODULE_EDIT")
                .antMatchers("/system/module/update").hasRole("SYSTEM_MODULE_EDIT")
                .antMatchers("/system/module/delete/*").hasRole("SYSTEM_MODULE_DELETE")
                .antMatchers("/system/permission").hasRole("SYSTEM_PERMISSION_READ")
                .antMatchers("/system/permission/add").hasRole("SYSTEM_PERMISSION_ADD")
                .antMatchers("/system/permission/save").hasRole("SYSTEM_PERMISSION_ADD")
                .antMatchers("/system/permission/edit/*").hasRole("SYSTEM_PERMISSION_EDIT")
                .antMatchers("/system/permission/update").hasRole("SYSTEM_PERMISSION_EDIT")
                .antMatchers("/system/permission/delete/*").hasRole("SYSTEM_PERMISSION_DELETE")
                .anyRequest().authenticated();
        
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/check")
                .failureUrl("/login/error")
                .defaultSuccessUrl("/", true)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
        
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        
        http.sessionManagement()
                .maximumSessions(1).and()
                .sessionFixation()
                .newSession();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        StandardPasswordEncoder encoder = new StandardPasswordEncoder(defaultSecret);
        return encoder;
    }
    
}