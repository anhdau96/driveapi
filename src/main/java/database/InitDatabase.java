/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Viet Bac
 */
public class InitDatabase {

    public void create() throws SQLException, ClassNotFoundException {
        Connection conn = Connect.getConnection();

        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE MOVIES "
                + "(ID INT PRIMARY KEY             NOT NULL, "
                + " NAME           VARCHAR(150)    NOT NULL, "
                + " YEAR           VARCHAR(5)      NOT NULL, "
                + " URL            VARCHAR(200)    NOT NULL, "
                + " FILE           VARCHAR(150)    NOT NULL, "
                + " UPLOAD         BIT                     , "
                + " DOWNLOAD       BIT                       ";
        String sql1 = "CREATE TABLE EPS "
                + "(ID INT PRIMARY KEY             NOT NULL,"
                + " MOVIEID        INT             NOT NULL, "
                + " EP             INT             NOT NULL, "
                + " URL            VARCHAR(200)    NOT NULL, "
                + " FILE           VARCHAR(150)    NOT NULL, "
                + " UPLOAD         BIT                     , "
                + " DOWNLOAD       BIT                       ";
        String sql2 = "CREATE TABLE LOG " +
                   "(ID INT PRIMARY KEY     NOT NULL," +
                   " ERROR          TEXT    NOT NULL "; 
        stmt.executeUpdate(sql); 
        stmt.executeUpdate(sql1); 
        stmt.executeUpdate(sql2); 
        stmt.close();
        conn.close();
    }
}
