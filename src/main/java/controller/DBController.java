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
import model.DownloadInfo;
import model.Episode;
import model.FileUpload;
import model.Movie;

/**
 *
 * @author Viet Bac
 */
public class DBController {

    public void insertMovies(String name, String year, String url, String file, String quality,int season) throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement check = conn.prepareStatement("SELECT * FROM MOVIES WHERE NAME = ? AND YEAR = ?");
        check.setString(1, name);
        check.setString(2, year);
        ResultSet executeQuery = check.executeQuery();
        boolean next = executeQuery.next();
        if (!next) {
            PreparedStatement pt = conn.prepareStatement("INSERT INTO MOVIES (NAME,YEAR,URL,FILENAME,QUALITY,UPLOAD,DOWNLOAD,SEASON,ADDSTATUS) VALUES (?,?,?,?,?,?,?,?,?)");
            pt.setString(1, name);
            pt.setString(2, year);
            pt.setString(3, url);
            pt.setString(4, file);
            pt.setString(5, quality);
            pt.setInt(6, 0);
            pt.setInt(7, 0);
            pt.setInt(8, season);
            pt.setInt(9, 0);
            pt.execute();
        }
        conn.close();
    }

    public void insertEps(int movieId, int ep, String url, String file) throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement check = conn.prepareStatement("SELECT * FROM EPS WHERE MOVIEID = ? AND EP = ?");
        check.setInt(1, movieId);
        check.setInt(2, ep);
        ResultSet executeQuery = check.executeQuery();
        boolean next = executeQuery.next();
        if (!next) {
            PreparedStatement pt = conn.prepareStatement("INSERT INTO EPS (MOVIEID,EP,URL,FILENAME,UPLOAD,DOWNLOAD,ADDSTATUS) VALUES (?,?,?,?,?,?,?)");
            pt.setInt(1, movieId);
            pt.setInt(2, ep);
            pt.setString(3, url);
            pt.setString(4, file);
            pt.setInt(5, 0);
            pt.setInt(6, 0);
            pt.setInt(7, 0);
            pt.execute();
        }
        conn.close();
    }

    public void updateDownload(String file) throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement pt = conn.prepareStatement("UPDATE MOVIES SET DOWNLOAD = 1 WHERE FILENAME = ?");
        pt.setString(1, file);
        pt.execute();
        PreparedStatement pt1 = conn.prepareStatement("UPDATE EPS SET DOWNLOAD = 1 WHERE FILENAME = ?");
        pt1.setString(1, file);
        pt1.execute();
        conn.close();
    }

    public void updateUpload(String file, String ggId) throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement pt = conn.prepareStatement("UPDATE MOVIES SET UPLOAD = 1, GGID =? WHERE FILENAME = ?");
        pt.setString(1, ggId);
        pt.setString(2, file);
        pt.execute();
        PreparedStatement pt1 = conn.prepareStatement("UPDATE EPS SET UPLOAD = 1, GGID =? WHERE FILENAME = ?");
        pt1.setString(1, ggId);
        pt1.setString(2, file);
        pt1.execute();
        conn.close();
    }
    
        public void updateAdd(String file,int add) throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement pt = conn.prepareStatement("UPDATE MOVIES SET ADDSTATUS = ? WHERE FILENAME = ?");
        pt.setInt(1, add);
        pt.setString(2, file);
        pt.execute();
        PreparedStatement pt1 = conn.prepareStatement("UPDATE EPS SET ADDSTATUS = ? WHERE FILENAME = ?");
        pt1.setInt(1, add);
        pt1.setString(2, file);
        pt1.execute();
        conn.close();
    }

    public List<Movie> getAllMovie() throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
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
        Connect c = new Connect();
        Connection conn = c.getConnection();
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
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM MOVIES WHERE NAME = ? AND YEAR = ?");
        pst.setString(1, name);
        pst.setString(2, year);
        ResultSet rs = pst.executeQuery();
        boolean next = rs.next();
        int id;
        if (next) {
            id = rs.getInt(1);
        } else {
            id = 0;
        }
        conn.close();
        return id;
    }

    public List<DownloadInfo> getNotDownload() throws SQLException, ClassNotFoundException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM MOVIES WHERE QUALITY !='serie' AND DOWNLOAD = 0";
        ResultSet rs = stmt.executeQuery(sql);
        List<DownloadInfo> lstFile = new ArrayList<>();
        while (rs.next()) {
            lstFile.add(new DownloadInfo(rs.getInt(1), rs.getString(4), rs.getString(5), 1));
        }
        String sql1 = "SELECT * FROM EPS WHERE DOWNLOAD = 0";
        ResultSet rs1 = stmt.executeQuery(sql1);
        while (rs1.next()) {
            lstFile.add(new DownloadInfo(rs1.getInt(1), rs1.getString(4), rs1.getString(5), rs1.getByte(3)));
        }
        conn.close();
        return lstFile;
    }

    public FileUpload findFile(String fileName) throws SQLException, ClassNotFoundException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM MOVIES WHERE FILENAME = ?");
        pst.setString(1, fileName);
        ResultSet rs = pst.executeQuery();
        boolean next = rs.next();
        if (next) {
            FileUpload fileUpload = new FileUpload(rs.getInt(1), "movie", rs.getString(5), rs.getByte(7)); ;
            conn.close();
            return fileUpload;
        }
        PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM EPS WHERE FILENAME = ?");
        pst1.setString(1, fileName);
        ResultSet rs1 = pst1.executeQuery();
        boolean next1 = rs1.next();
        if (next1) {
            FileUpload fileUpload = new FileUpload(rs1.getInt(1), "eps", rs1.getString(5), rs1.getByte(6));
            conn.close();
            return fileUpload;
        }
        return null;
    }

    public Movie checkAddMovie(String fileName) throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM MOVIES WHERE FILENAME = ? AND UPLOAD = 1 AND DOWNLOAD = 1");
        pst.setString(1, fileName);
        ResultSet rs = pst.executeQuery();
        boolean next = rs.next();
        if (next) {
            Movie movie = new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getByte(7), rs.getByte(8), rs.getString(9));
            conn.close();
            return movie;
        }
        return null;
    }

    public Episode checkAddEps(String fileName) throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement pst1 = conn.prepareStatement("SELECT * FROM EPS WHERE FILENAME = ? AND UPLOAD = 1 AND DOWNLOAD = 1");
        pst1.setString(1, fileName);
        ResultSet rs = pst1.executeQuery();
        boolean next = rs.next();
        if (next) {
            conn.close();
            return new Episode(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getByte(6), rs.getByte(7), rs.getString(8));
        }
        return null;
    }

    public Movie getMovieSeri(int movieId) throws ClassNotFoundException, SQLException {
        Connect c = new Connect();
        Connection conn = c.getConnection();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM MOVIES WHERE ID = ?");
        pst.setInt(1, movieId);
        ResultSet rs = pst.executeQuery();
        Movie m = null;
        rs.next();
        m = new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getByte(7), rs.getByte(8),rs.getString(9));
        conn.close();
        return m;
    }
    
//    public int getYear(String seriName){
//        Connect c = new Connect();
//        Connection conn = c.getConnection();
//        PreparedStatement pst = conn.prepareStatement("SELECT * FROM MOVIES WHERE NAME = ? )");
//        pst.setInt(1, movieId);
//    }
}
