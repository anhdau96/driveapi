package com.mycompany.driveapi;

import ApiSmovies.Smovies;
import Config.ConfigService;
import UI.UploadFile;
import controller.DBController;
import crawl.CrawlUI;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;

/**
 * Created by super on 1/14/2017.
 */
public class UploadThread extends Thread {

    public CrawlUI uploadFrame;
    public DriveManager driveManager;
    File file;

    public UploadThread(File f, CrawlUI uploadFrame) {
        this.file = f;
        this.uploadFrame = uploadFrame;
    }

    @Override
    public void run() {
        try {
            System.out.println("Upload");
            driveManager = new DriveManager();
            uploadFrame.updateStatus("created new Thread : Preparing upload file " + file.getName() + "\n");
            com.google.api.services.drive.model.File upload = driveManager.upload(file, uploadFrame);
            uploadFrame.updateStatus("upload " + file.getName() + " complete\n");
            uploadFrame.updateStatus("writing to db \n");
            DBController db = new DBController();
            db.updateUpload(FilenameUtils.removeExtension(file.getName()), upload.getId());
            uploadFrame.updateStatus("new thread calling smovies api \n");
            Movie checkAddMovie = db.checkAddMovie(FilenameUtils.removeExtension(file.getName()));
            if (checkAddMovie != null) {
                String createMovie = Smovies.getInstance().createMovie(checkAddMovie.name, checkAddMovie.year, checkAddMovie.ggId, checkAddMovie.quality);
                System.out.println(createMovie);
                if (Integer.parseInt(createMovie) == 1) {
                    File f = new File(ConfigService.getInstance().get("savePath") + "\\" + file.getName());
                    f.delete();
                }
            } else {
                System.out.println("123");
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(UploadThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
