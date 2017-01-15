/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Viet Bac
 */
public class Connect {

    private Connection conn;

    public Connect() {
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://heymanga.database.windows.net;databaseName=smoviesauto";
        conn = DriverManager.getConnection(connectionURL, "heymanga ", "abc1231!");
        return conn;
    }
}
