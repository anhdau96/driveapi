package com.mycompany.driveapi;

import UI.UploadManager;
import UI.UploadingTableModel;
import controller.DBController;
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import static UI.UploadManager.progress;

/**
 * Created by super on 1/11/2017.
 */
public class DownloadThread extends Thread {
    public DriveManager driveManager;
    public File[] files;
    public UploadingTableModel uploadingTableModel;
    public JTable uploadingTable;
    public JTextArea uploadingStatus;
    public UploadManager uploadManager;

    public DownloadThread(UploadManager uploadManager) {
        this.uploadManager=uploadManager;
    }

    @Override
    public void run() {
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
            try {
                uploadingStatus.append("created new Thread : Preparing upload file "+files[i].getName());
                com.google.api.services.drive.model.File upload = driveManager.upload(files[i], uploadManager);
                uploadingStatus.append("upload "+files[i].getName()+" complete");
                uploadingStatus.append("writing to db");
                DBController db=new DBController();
                uploadingStatus.append("calling smovies api ");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        jTextArea.append(progress);
    }
}
