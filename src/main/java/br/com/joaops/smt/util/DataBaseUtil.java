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
package br.com.joaops.smt.util;

import br.com.joaops.smt.dto.SystemDatabaseDto;
import br.com.joaops.smt.dto.SystemUserDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Paulo
 */
public class DataBaseUtil {
    
    private static String driver = "org.postgresql.Driver";
    private static String user = "postgres";
    private static String senha = "postgres";
    private static String url = "jdbc:postgresql://localhost:5432/master";
    
    public static List<String> getListDatabaseFromMaster() { //retorna os nomes dos BDs
        List<String> lista = new ArrayList<>();
        Connection connection;
        try {
            Class.forName(driver);
            connection = (Connection) DriverManager.getConnection(url, user, senha);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM public.system_database;");
            while (resultSet.next()) {
                lista.add(resultSet.getString("name"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.print("[ERRO getListDatabaseFromMaster()] "+e);
        }
        return lista;
    }
    
    public static void createDatabase(SystemDatabaseDto databaseDto) {
        Connection connection;
        try {
            Class.forName(driver);
            connection = (Connection) DriverManager.getConnection(url, user, senha);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE "+databaseDto.getName());
            //criar as sequências e as tabelas no banco de dados
            createSequencesOnDatabase(databaseDto);
            createTablesOnDatabase(databaseDto);
            insertInitialValues(databaseDto);
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.println("[ERRO createDatabase()] "+e);
        }
    }
    
    public static Boolean DatabaseExist(String database) {
        Boolean exist;
        Connection connection;
        try {
            Class.forName(driver);
            connection = (Connection) DriverManager.getConnection(url, user, senha);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname = '"+database+"';");
            exist = resultSet.next();
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.println("[ERRO DatabaseExist()] "+e);
            exist = Boolean.FALSE;
        }
        return exist;
    }
    
    private static void createSequencesOnDatabase(SystemDatabaseDto databaseDto) {
        try {
            Class.forName(driver);
            Connection connection = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+databaseDto.getName(), databaseDto.getUsername(), databaseDto.getPassword());
            Statement statement = connection.createStatement();
            statement.execute("CREATE SEQUENCE public.seq_system_database INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");
            statement.execute("ALTER TABLE public.seq_system_database OWNER TO postgres;");
            statement.execute("CREATE SEQUENCE public.seq_system_module INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");
            statement.execute("ALTER TABLE public.seq_system_module OWNER TO postgres;");
            statement.execute("CREATE SEQUENCE public.seq_system_user INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");
            statement.execute("ALTER TABLE public.seq_system_user OWNER TO postgres;");
            statement.execute("CREATE SEQUENCE public.seq_empresa INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");
            statement.execute("ALTER TABLE public.seq_empresa OWNER TO postgres;");
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.println("[ERRO createSequencesOnDatabase()] "+e);
        }
    }
    
    private static void createTablesOnDatabase(SystemDatabaseDto databaseDto) {
        try {
            Class.forName(driver);
            Connection connection = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+databaseDto.getName(), databaseDto.getUsername(), databaseDto.getPassword());
            Statement statement = connection.createStatement();
            //Tabela empresa
            statement.execute("CREATE TABLE public.empresa(id_empresa bigint NOT NULL, razao_social character varying(255) NOT NULL, CONSTRAINT empresa_pkey PRIMARY KEY (id_empresa)) WITH (OIDS=FALSE);");
            statement.execute("ALTER TABLE public.empresa OWNER TO postgres;");
            statement.execute("CREATE INDEX idx_id_empresa ON public.empresa USING btree(id_empresa);");
            //Tabela system_Module
            statement.execute("CREATE TABLE public.system_module (id_system_module bigint NOT NULL, system_module_category character varying(255) NOT NULL, system_module_name character varying(255) NOT NULL, CONSTRAINT system_module_pkey PRIMARY KEY (id_system_module))WITH (OIDS=FALSE);");
            statement.execute("ALTER TABLE public.system_module OWNER TO postgres;");
            statement.execute("CREATE INDEX idx_id_system_module ON public.system_module USING btree(id_system_module);");
            //Tabela system_database
            statement.execute("CREATE TABLE public.system_database(id_system_database bigint NOT NULL, name character varying(255) NOT NULL, password character varying(255), server character varying(255), username character varying(255), version character varying(255), CONSTRAINT system_database_pkey PRIMARY KEY (id_system_database)) WITH (OIDS=FALSE);");
            statement.execute("ALTER TABLE public.system_database OWNER TO postgres;");
            statement.execute("CREATE INDEX idx_id_system_database ON public.system_database USING btree(id_system_database);");
            statement.execute("CREATE INDEX idx_name ON public.system_database USING btree(name COLLATE pg_catalog.\"default\");");
            //Tabela system_user
            statement.execute("CREATE TABLE public.system_user(id_system_user bigint NOT NULL, account_can_expire boolean NOT NULL, account_expiration date, credential_can_expire boolean NOT NULL, credential_expiration date, email character varying(100) NOT NULL, enabled boolean NOT NULL, first_name character varying(100) NOT NULL, last_name character varying(100) NOT NULL, locked boolean NOT NULL, middle_name character varying(100), password character varying(256) NOT NULL, systemdatabase_id_system_database bigint, CONSTRAINT system_user_pkey PRIMARY KEY (id_system_user), CONSTRAINT fk_dnodi6ae95crgrweb3shlg2st FOREIGN KEY (systemdatabase_id_system_database) REFERENCES public.system_database (id_system_database) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION, CONSTRAINT uk_3ypdb9457wfdya51dfk3ul642 UNIQUE (email)) WITH (OIDS=FALSE);");
            statement.execute("ALTER TABLE public.system_user OWNER TO postgres;");
            statement.execute("CREATE INDEX idx_email ON public.system_user USING btree (email COLLATE pg_catalog.\"default\");");
            statement.execute("CREATE INDEX idx_id_system_user ON public.system_user USING btree (id_system_user);");
            //Tabela system_user_permission
            statement.execute("CREATE TABLE public.system_user_permission(add boolean NOT NULL, delete boolean NOT NULL, edit boolean NOT NULL, read boolean NOT NULL, id_system_module bigint NOT NULL, id_system_user bigint NOT NULL, CONSTRAINT system_user_permission_pkey PRIMARY KEY (id_system_module, id_system_user), CONSTRAINT fk_ha9kiacrc4m1ct9lbw1gchkf1 FOREIGN KEY (id_system_module) REFERENCES public.system_module (id_system_module) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION, CONSTRAINT fk_kok5aeoth3eq7cj7hbtrupjc4 FOREIGN KEY (id_system_user) REFERENCES public.system_user (id_system_user) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION )WITH (OIDS=FALSE);");
            statement.execute("ALTER TABLE public.system_user_permission OWNER TO postgres;");
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.println("[ERRO createTablesOnDatabase()] "+e);
        }
    }
    
    private static void insertInitialValues(SystemDatabaseDto databaseDto) {
        try {
            Class.forName(driver);
            Connection connection = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+databaseDto.getName(), databaseDto.getUsername(), databaseDto.getPassword());
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO public.empresa(id_empresa, razao_social) VALUES (nextval('seq_empresa'), '"+databaseDto.getName()+" Sistemas');");
            statement.execute("INSERT INTO public.system_module(id_system_module, system_module_name, system_module_category) VALUES (nextval('seq_system_module'), 'MODULE', 'SYSTEM');");
            statement.execute("INSERT INTO public.system_module(id_system_module, system_module_name, system_module_category) VALUES (nextval('seq_system_module'), 'USER', 'SYSTEM');");
            statement.execute("INSERT INTO public.system_module(id_system_module, system_module_name, system_module_category) VALUES (nextval('seq_system_module'), 'PERMISSION', 'SYSTEM');");
            statement.execute("INSERT INTO public.system_module(id_system_module, system_module_name, system_module_category) VALUES (nextval('seq_system_module'), 'DATABASE', 'SYSTEM');");
            //Buscar o Database
            statement.execute("INSERT INTO public.system_database(id_system_database, name, password, server, username, version) VALUES (nextval('seq_system_database'), '"+databaseDto.getName()+"', '"+databaseDto.getPassword()+"', '"+databaseDto.getServer()+"', '"+databaseDto.getUsername()+"', '"+databaseDto.getVersion()+"');");
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.println("[ERRO insertInitialValues()] "+e);
        }
    }
    
    public static void insertInitialValues(SystemDatabaseDto databaseDto, SystemUserDto systemUserDto) { //Relaciona o SystemUser com o SystemDatabase no Banco de Dados do usuário
        try {
            Class.forName(driver);
            Connection connection = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+databaseDto.getName(), databaseDto.getUsername(), databaseDto.getPassword());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_system_database FROM public.system_database WHERE name = '"+databaseDto.getName()+"' AND password = '"+databaseDto.getPassword()+"' AND server = '"+databaseDto.getServer()+"' AND username = '"+databaseDto.getUsername()+"' AND version = '"+databaseDto.getVersion()+"';");
            Long id = 1L;
            if (resultSet.next()) {
                id = Long.parseLong(resultSet.getString("id_system_database"));
            }
            statement.execute("INSERT INTO public.system_user(id_system_user, account_can_expire, account_expiration, credential_can_expire, credential_expiration, email, enabled, first_name, last_name, locked, middle_name, password, systemdatabase_id_system_database) VALUES (nextval('seq_system_user'), false, null, false, null, '"+systemUserDto.getEmail()+"', true, '"+systemUserDto.getFirstName()+"', '"+systemUserDto.getLastName()+"', false, '"+systemUserDto.getMiddleName()+"', '"+systemUserDto.getPassword()+"', "+id+");");
            statement.close();
            connection.close();
        } catch (Exception e){
            System.err.println("[ERRO insertInitialValues()] "+e);
        }
    }
    
}