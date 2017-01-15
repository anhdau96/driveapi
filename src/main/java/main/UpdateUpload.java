/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Config.ConfigService;
import com.mycompany.driveapi.UploadThread;
import controller.DBController;
import crawl.CrawlUI;
import fileandfolder.FileAndFolder;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FileUpload;

/**
 *
 * @author Administrator
 */
public class UpdateUpload extends Thread {

    public CrawlUI c;

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateUpload.class.getName()).log(Level.SEVERE, null, ex);
            }
            DBController contr = new DBController();
            List<String> allFille = FileAndFolder.getAllFille();
            for (String string : allFille) {
                try {
                    FileUpload findFile = contr.findFile(string);
                    if (findFile.upload == 0) {
                        UploadThread up = new UploadThread(new File(ConfigService.getInstance().get("savePath") + "\\" + string + ".mp4"), c);
                        up.start();
                        up.join();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateUpload.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UpdateUpload.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UpdateUpload.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
