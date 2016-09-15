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
package br.com.joaops.smt.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author João
 */
public class SystemUserDto implements Serializable {
    
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Boolean accountCanExpire;
    private Date accountExpiration;
    private Boolean locked;
    private Boolean credentialCanExpire;
    private Date credentialExpiration;
    private Boolean enabled;
    private List<SystemUserPermissionDto> systemUserPermission;
    private SystemDatabaseDto systemDatabase;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean getAccountCanExpire() {
        return accountCanExpire;
    }
    
    public void setAccountCanExpire(Boolean accountCanExpire) {
        this.accountCanExpire = accountCanExpire;
    }
    
    public Date getAccountExpiration() {
        return accountExpiration;
    }
    
    public void setAccountExpiration(Date accountExpiration) {
        this.accountExpiration = accountExpiration;
    }
    
    public Boolean getLocked() {
        return locked;
    }
    
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
    
    public Boolean getCredentialCanExpire() {
        return credentialCanExpire;
    }
    
    public void setCredentialCanExpire(Boolean credentialCanExpire) {
        this.credentialCanExpire = credentialCanExpire;
    }
    
    public Date getCredentialExpiration() {
        return credentialExpiration;
    }
    
    public void setCredentialExpiration(Date credentialExpiration) {
        this.credentialExpiration = credentialExpiration;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public List<SystemUserPermissionDto> getSystemUserPermission() {
        return systemUserPermission;
    }
    
    public void setSystemUserPermission(List<SystemUserPermissionDto> systemUserPermission) {
        this.systemUserPermission = systemUserPermission;
    }
    
    public SystemDatabaseDto getSystemDatabase() {
        return systemDatabase;
    }
    
    public void setSystemDatabase(SystemDatabaseDto systemDatabase) {
        this.systemDatabase = systemDatabase;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.firstName);
        hash = 71 * hash + Objects.hashCode(this.middleName);
        hash = 71 * hash + Objects.hashCode(this.lastName);
        hash = 71 * hash + Objects.hashCode(this.email);
        hash = 71 * hash + Objects.hashCode(this.password);
        hash = 71 * hash + Objects.hashCode(this.accountCanExpire);
        hash = 71 * hash + Objects.hashCode(this.accountExpiration);
        hash = 71 * hash + Objects.hashCode(this.locked);
        hash = 71 * hash + Objects.hashCode(this.credentialCanExpire);
        hash = 71 * hash + Objects.hashCode(this.credentialExpiration);
        hash = 71 * hash + Objects.hashCode(this.enabled);
        hash = 71 * hash + Objects.hashCode(this.systemUserPermission);
        hash = 71 * hash + Objects.hashCode(this.systemDatabase);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemUserDto other = (SystemUserDto) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.middleName, other.middleName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.accountCanExpire, other.accountCanExpire)) {
            return false;
        }
        if (!Objects.equals(this.accountExpiration, other.accountExpiration)) {
            return false;
        }
        if (!Objects.equals(this.locked, other.locked)) {
            return false;
        }
        if (!Objects.equals(this.credentialCanExpire, other.credentialCanExpire)) {
            return false;
        }
        if (!Objects.equals(this.credentialExpiration, other.credentialExpiration)) {
            return false;
        }
        if (!Objects.equals(this.enabled, other.enabled)) {
            return false;
        }
        if (!Objects.equals(this.systemUserPermission, other.systemUserPermission)) {
            return false;
        }
        if (!Objects.equals(this.systemDatabase, other.systemDatabase)) {
            return false;
        }
        return true;
    }
    
}