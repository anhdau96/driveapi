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

    public static void create() throws SQLException, ClassNotFoundException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        Statement stmt = conn.createStatement();
        String drop = "DROP TABLE IF EXISTS MOVIES;"
                + "DROP TABLE IF EXISTS EPS;"
                + "DROP TABLE IF EXISTS LOG;";
        String sql = "CREATE TABLE MOVIES "
                + "(ID INT PRIMARY KEY             IDENTITY(1,1), "
                + " NAME           VARCHAR(150)    NOT NULL, "
                + " YEAR           VARCHAR(5)      NOT NULL, "
                + " URL            VARCHAR(200)    NOT NULL, "
                + " FILENAME           VARCHAR(150)    NOT NULL, "
                + " QUALITY        VARCHAR(6)      NULL    , "
                + " UPLOAD         BIT                     , "
                + " DOWNLOAD       BIT                     , "
                + " GGID           VARCHAR(150)    NULL,     "
                + " SEASON         INT                     , "
                + " ADDSTATUS       INT                     ,"
                + " VPS            INT                      );";
        String sql1 = "CREATE TABLE EPS "
                + "(ID INT PRIMARY KEY             IDENTITY(1,1),"
                + " MOVIEID        INT             NOT NULL, "
                + " EP             INT             NOT NULL, "
                + " URL            VARCHAR(200)    NOT NULL, "
                + " FILENAME           VARCHAR(150)    NOT NULL, "
                + " UPLOAD         BIT                     , "
                + " DOWNLOAD       BIT                     , "
                + " GGID           VARCHAR(150)    NULL, "
                + " ADDSTATUS            INT                     ,"
                + " VPS            INT                      );";;
        String sql2 = "CREATE TABLE LOG "
                + "(ID INTEGER PRIMARY KEY     IDENTITY(1,1),"
                + " ERROR          TEXT    NOT NULL );";
        stmt.executeUpdate(drop);
        stmt.executeUpdate(sql);
        stmt.executeUpdate(sql1);
        stmt.executeUpdate(sql2);
        stmt.close();
        conn.close();
    }
}
