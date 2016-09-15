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
import java.util.Objects;

/**
 *
 * @author João
 */
public class SystemUserPermissionFormDto implements Serializable {
    
    private Long idUser;
    private Long idModule;
    private Boolean read;
    private Boolean add;
    private Boolean edit;
    private Boolean delete;
    
    public Long getIdUser() {
        return idUser;
    }
    
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    
    public Long getIdModule() {
        return idModule;
    }
    
    public void setIdModule(Long idModule) {
        this.idModule = idModule;
    }
    
    public Boolean getRead() {
        return read;
    }
    
    public void setRead(Boolean read) {
        this.read = read;
    }
    
    public Boolean getAdd() {
        return add;
    }
    
    public void setAdd(Boolean add) {
        this.add = add;
    }
    
    public Boolean getEdit() {
        return edit;
    }
    
    public void setEdit(Boolean edit) {
        this.edit = edit;
    }
    
    public Boolean getDelete() {
        return delete;
    }
    
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idUser);
        hash = 41 * hash + Objects.hashCode(this.idModule);
        hash = 41 * hash + Objects.hashCode(this.read);
        hash = 41 * hash + Objects.hashCode(this.add);
        hash = 41 * hash + Objects.hashCode(this.edit);
        hash = 41 * hash + Objects.hashCode(this.delete);
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
        final SystemUserPermissionFormDto other = (SystemUserPermissionFormDto) obj;
        if (!Objects.equals(this.idUser, other.idUser)) {
            return false;
        }
        if (!Objects.equals(this.idModule, other.idModule)) {
            return false;
        }
        if (!Objects.equals(this.read, other.read)) {
            return false;
        }
        if (!Objects.equals(this.add, other.add)) {
            return false;
        }
        if (!Objects.equals(this.edit, other.edit)) {
            return false;
        }
        if (!Objects.equals(this.delete, other.delete)) {
            return false;
        }
        return true;
    }
    
}