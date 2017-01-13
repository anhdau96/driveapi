/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.DBController;
import fileandfolder.FileAndFolder;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class UpdateDownload extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                sleep(300000);
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateDownload.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<String> allFille = FileAndFolder.getAllFille();
            DBController contr = new DBController();
            for (String string : allFille) {
                try {
                    System.out.println("update download: "+string);
                    contr.updateDownload(string);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex){
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
