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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    public static void createDatabase(String database) {
        Connection connection;
        try {
            Class.forName(driver);
            connection = (Connection) DriverManager.getConnection(url, user, senha);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE "+database);
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
    
}