package com.mycompany.driveapi;

import UI.UploadFile;
import controller.DBController;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by super on 1/14/2017.
 */
public class UploadThread extends Thread {

    public JTextArea uploadingStatus;
    public UploadFile uploadFrame;
    public DriveManager driveManager;
    File file;

    public UploadThread(File f) {
        this.file = f;
    }

    @Override
    public void run() {
        try {
            driveManager=new DriveManager();

        uploadingStatus.append("created new Thread : Preparing upload file " + file.getName());
        com.google.api.services.drive.model.File upload = driveManager.upload(file, uploadFrame);
        uploadingStatus.append("upload " + file.getName() + " complete");
        uploadingStatus.append("writing to db");
        DBController db = new DBController();
        db.updateUpload(FilenameUtils.removeExtension(file.getName()), upload.getId());
        uploadingStatus.append("new thread calling smovies api ");
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
