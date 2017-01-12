package com.mycompany.driveapi;

import UI.UploadManager;
import UI.UploadingTableModel;
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
    public JTextArea jTextArea;
    public UploadManager uploadManager;

    public DownloadThread(UploadManager uploadManager) {
        this.uploadManager=uploadManager;
    }

    @Override
    public void run() {
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
            try {
                System.out.println();
                driveManager.upload(files[i],uploadManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadingTableModel.addRow(new String[]{
                    "" + i, files[i].getName(), progress
            });
        }
        uploadingTable.setModel(uploadingTableModel);
        uploadingTable.getColumnModel().getColumn(0).setMaxWidth(50);
//        jTextArea.append(progress);
    }
}
