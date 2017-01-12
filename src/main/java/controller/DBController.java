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
import model.Episode;
import model.FileUpload;
import model.Movie;

/**
 *
 * @author Viet Bac
 */
public class DBController {

    public void insertMovies(String name, String year, String url, String file, String quality) throws ClassNotFoundException, SQLException {
        Connection conn = Connect.getConnection();
        PreparedStatement pt = conn.prepareStatement("INSERT INTO MOVIES (NAME,YEAR,URL,FILE,QUALITY,UPLOAD,DOWNLOAD) VALUES (?,?,?,?,?,?,?)");
        pt.setString(1, name);
        pt.setString(2, year);
        pt.setString(3, url);
        pt.setString(4, file);
        pt.setString(5, quality);
        pt.setInt(6, 0);
        pt.setInt(7, 0);
        pt.execute();
        conn.close();
    }

    public void insertEps(int movieId, int ep, String url, String file) throws ClassNotFoundException, SQLException {
        Connection conn = Connect.getConnection();
        PreparedStatement pt = conn.prepareStatement("INSERT INTO EPS (MOVIEID,EP,URL,FILE,UPLOAD,DOWNLOAD) VALUES (?,?,?,?,?,?)");
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

    public List<Movie> getAllMovie() throws ClassNotFoundException, SQLException {
        Connection conn = Connect.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM MOVIES";
        ResultSet rs = stmt.executeQuery(sql);
        List<Movie> lstMovie = new ArrayList<>();
        while (rs.next()) {
            lstMovie.add(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getByte(7), rs.getByte(8)));
        }
        conn.close();
        return lstMovie;
    }

    public List<Episode> getAllEpisodes(int movieId) throws ClassNotFoundException, SQLException {
        Connection conn = Connect.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM EPS WHERE MOVIEID = " + movieId;
        ResultSet rs = stmt.executeQuery(sql);
        List<Episode> lstEpisodes = new ArrayList<>();
        while (rs.next()) {
            lstEpisodes.add(new Episode(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getByte(6), rs.getByte(7)));
        }
        conn.close();
        return lstEpisodes;
    }

    public int getMovieId(String name, String year) throws SQLException, ClassNotFoundException {
        Connection conn = Connect.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM MOVIES WHERE NAME = ? AND YEAR = ?");
        pst.setString(1, name);
        pst.setString(2, year);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int id = rs.getInt(1);
        conn.close();
        return id;
    }

    public List<FileUpload> getNotUpload() throws SQLException, ClassNotFoundException {
        Connection conn = Connect.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM MOVIES WHERE TYPE !='serie' AND UPLOAD = 0";
        ResultSet rs = stmt.executeQuery(sql);
        List<FileUpload> lstFile = new ArrayList<>();
        while (rs.next()) {
            lstFile.add(new FileUpload(rs.getInt(1), "movie", rs.getString(4), rs.getByte(6)));
        }
        String sql1 = "SELECT * FROM EPS WHERE UPLOAD = 0";
        ResultSet rs1 = stmt.executeQuery(sql1);
        while (rs1.next()) {
            lstFile.add(new FileUpload(rs1.getInt(1), "eps", rs.getString(5), rs.getByte(6)));
        }
        conn.close();
        return lstFile;
    }

    public FileUpload findFile(String fileName) throws SQLException, ClassNotFoundException {
        Connection conn = Connect.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM MOVIES WHERE FILE = ?");
        pst.setString(1, fileName);
        ResultSet rs = pst.executeQuery();
        boolean next = rs.next();
        if (next) {
            conn.close();
            return new FileUpload(rs.getInt(1), "movie", rs.getString(4), rs.getByte(6));
        }
        PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM MOVIES WHERE FILE = ?");
        pst1.setString(1, fileName);
        ResultSet rs1 = pst1.executeQuery();
        boolean next1 = rs1.next();
        if (next1) {
            conn.close();
            return new FileUpload(rs1.getInt(1), "eps", rs.getString(5), rs.getByte(6));
        }
        return null;
    }
}
