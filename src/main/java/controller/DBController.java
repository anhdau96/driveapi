/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Movie;

/**
 *
 * @author Viet Bac
 */
public class DBController {

    public void insertMovies(String name, String year, String url, String file) throws ClassNotFoundException, SQLException {
        Connection conn = Connect.getConnection();
        PreparedStatement pt = conn.prepareStatement("INSERT INTO MOVIES (NAME,YEAR,URL,FILE,UPLOAD,DOWNLOAD) VALUES (?,?,?,?,?,?)");
        pt.setString(1, name);
        pt.setString(2, year);
        pt.setString(3, url);
        pt.setString(4, file);
        pt.setInt(5, 0);
        pt.setInt(6, 0);
        pt.execute();
        conn.close();
    }

    public void insertEps(int movieId, int ep, String url, String file) throws ClassNotFoundException, SQLException {
        Connection conn = Connect.getConnection();
        PreparedStatement pt = conn.prepareStatement("INSERT INTO MOVIES (MOVIEID,EP,URL,FILE,UPLOAD,DOWNLOAD) VALUES (?,?,?,?,?,?)");
        pt.setInt(1, movieId);
        pt.setInt(2, ep);
        pt.setString(3, url);
        pt.setString(4, file);
        pt.setInt(5, 0);
        pt.setInt(6, 0);
        pt.execute();
        conn.close();
    }

    public void updateDownload(int id, String type) throws ClassNotFoundException, SQLException {
        Connection conn = Connect.getConnection();
        PreparedStatement pt;
        if (type.compareTo("movie") == 0) {
            pt = conn.prepareStatement("UPDATE MOVIES SET DOWNLOAD = ? WHERE ID = ?");
            pt.setInt(1, 1);
            pt.setInt(2, id);
        } else {
            pt = conn.prepareStatement("UPDATE EPS SET DOWNLOAD = ? WHERE ID = ?");
            pt.setInt(1, 1);
            pt.setInt(2, id);
        }
        pt.execute();
        conn.close();
    }
    
    public void updateUpload(int id, String type) throws ClassNotFoundException, SQLException {
        Connection conn = Connect.getConnection();
        PreparedStatement pt;
        if (type.compareTo("movie") == 0) {
            pt = conn.prepareStatement("UPDATE MOVIES SET UPLOAD = ? WHERE ID = ?");
            pt.setInt(1, 1);
            pt.setInt(2, id);
        } else {
            pt = conn.prepareStatement("UPDATE EPS SET UPLOAD = ? WHERE ID = ?");
            pt.setInt(1, 1);
            pt.setInt(2, id);
        }
        pt.execute();
        conn.close();
    }
    
    public List<Movie> getAllMovie() throws ClassNotFoundException, SQLException{
        Connection conn = Connect.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM MOVIES";
        ResultSet rs = stmt.executeQuery(sql);
        List<Movie> lstMovie = new ArrayList<>();
        while (rs.next()) {            
            lstMovie.add(new Movie())
        }
    }
}
