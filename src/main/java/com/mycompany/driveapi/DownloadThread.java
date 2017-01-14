//package com.mycompany.driveapi;
//
//import ApiSmovies.Smovies;
//import UI.UploadManager;
//import UI.UploadingTableModel;
//import controller.DBController;
//import model.Movie;
//import org.apache.commons.io.FileSystemUtils;
//import org.apache.commons.io.FilenameUtils;
//import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;
//
//import javax.swing.*;
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//
//import static UI.UploadManager.progress;
//
///**
// * Created by super on 1/11/2017.
// */
//public class DownloadThread extends Thread {
//    public DriveManager driveManager;
//    public File[] files;
//    public UploadingTableModel uploadingTableModel;
//    public JTable uploadingTable;
//    public JTextArea uploadingStatus;
//    public UploadManager uploadManager;
//
//    public DownloadThread(UploadManager uploadManager) {
//        this.uploadManager=uploadManager;
//    }
//
//    @Override
//    public void run() {
//        for (File file : files) {
//            System.out.println(file.getName());
//            try {
//                uploadingStatus.append("created new Thread : Preparing upload file " + file.getName());
//                com.google.api.services.drive.model.File upload = driveManager.upload(file, uploadManager);
//                uploadingStatus.append("upload " + file.getName() + " complete");
//                uploadingStatus.append("writing to db");
//                DBController db = new DBController();
//                db.updateUpload(FilenameUtils.removeExtension(file.getName()), upload.getId());
//                uploadingStatus.append("new thread calling smovies api ");
//            } catch (IOException | SQLException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        UploadManager.flag = false;
////        jTextArea.append(progress);
//    }
//}
