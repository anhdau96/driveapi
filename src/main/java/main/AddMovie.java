/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ApiSmovies.Smovies;
import controller.DBController;
import fileandfolder.FileAndFolder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;

/**
 *
 * @author Administrator
 */
public class AddMovie extends Thread{

    @Override
    public void run() {
        DBController contr = new DBController();
        while(!interrupted()){
            List<String> allFille = FileAndFolder.getAllFille();
            for (String string : allFille) {
                Movie checkAddMovie = contr.checkAddMovie(string);
                if (checkAddMovie !=null){
                    try {
                        Smovies.getInstance().createMovie(checkAddMovie.name, checkAddMovie.year, checkAddMovie.ggId, checkAddMovie.quality);
                    } catch (URISyntaxException | IOException ex) {
                        Logger.getLogger(AddMovie.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
}
