/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ApiSmovies.Smovies;
import Config.ConfigService;
import controller.DBController;
import fileandfolder.FileAndFolder;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Episode;
import model.Movie;

/**
 *
 * @author Administrator
 */
public class AddMovie extends Thread {
    
    @Override
    public void run() {
        DBController contr = new DBController();
        while (!interrupted()) {
            List<String> allFille = FileAndFolder.getAllFille();
            for (String string : allFille) {
                Movie checkAddMovie = null;
                try {
                    checkAddMovie = contr.checkAddMovie(string);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(AddMovie.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (checkAddMovie != null) {
                    try {
                        String createMovie = Smovies.getInstance().createMovie(checkAddMovie.name, checkAddMovie.year, checkAddMovie.ggId, checkAddMovie.quality);
                        contr.updateAdd(string, Integer.parseInt(createMovie));
                        File f = new File(ConfigService.getInstance().get("savePath" + "\\" + string + ".mp4"));
                        f.delete();
                    } catch (URISyntaxException | IOException | ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(AddMovie.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
//                Episode checkAddEps = null;
//                try {
//                    checkAddEps = contr.checkAddEps(string);
//                } catch (ClassNotFoundException | SQLException ex) {
//                    Logger.getLogger(AddMovie.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                if (checkAddEps != null) {
//                    try {
//                        Movie movieSeri = contr.getMovieSeri(checkAddEps.movieId);
//                        Smovies.getInstance().createEpisode(movieSeri.name, movieSeri.season, String.valueOf(checkAddEps.ep), checkAddEps.ggId);
//                        contr.updateAdd(string, Integer.parseInt(createMovie));
//                        File f = new File(ConfigService.getInstance().get("savePath" + "\\" + string + ".mp4"));
//                        f.delete();
//                    } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
//                        Logger.getLogger(AddMovie.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    
//                }
            }
        }
    }
}
