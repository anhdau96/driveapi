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
                + "(ID INTEGER PRIMARY KEY             AUTOINCREMENT, "
                + " NAME           VARCHAR(150)    NOT NULL, "
                + " YEAR           VARCHAR(5)      NOT NULL, "
                + " URL            VARCHAR(200)    NOT NULL, "
                + " FILE           VARCHAR(150)    NOT NULL, "
                + " QUALITY        VARCHAR(6)      NULL    , "
                + " UPLOAD         BIT                     , "
                + " DOWNLOAD       BIT                       );";
        String sql1 = "CREATE TABLE EPS "
                + "(ID INTEGER PRIMARY KEY             AUTOINCREMENT,"
                + " MOVIEID        INT             NOT NULL, "
                + " EP             INT             NOT NULL, "
                + " URL            VARCHAR(200)    NOT NULL, "
                + " FILE           VARCHAR(150)    NOT NULL, "
                + " UPLOAD         BIT                     , "
                + " DOWNLOAD       BIT                     );";
        String sql2 = "CREATE TABLE LOG "
                + "(ID INTEGER PRIMARY KEY     AUTOINCREMENT,"
                + " ERROR          TEXT    NOT NULL );";
        stmt.executeUpdate(drop);
        stmt.executeUpdate(sql);
        stmt.executeUpdate(sql1);
        stmt.executeUpdate(sql2);
        stmt.close();
        conn.close();
    }
}
