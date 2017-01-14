/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import UI.UploadManager;
import crawl.Crawl;
import crawl.CrawlUI;
import database.InitDatabase;
import fileandfolder.FileAndFolder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Viet Bac
 */
public class Main {

    public static boolean checkStorage() {
        File file = new File("C:");
        long freeSpace = file.getFreeSpace();
//        System.out.println(freeSpace / 1024 / 1024 / 1024 + "GB");
        return (freeSpace / 1024 / 1024 / 1024) >= 6;
    }

    public static void runThread(boolean init) {
        if (init) {
            try {
                InitDatabase.create();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Crawl c = new Crawl();
        c.cUI = new CrawlUI();
        c.start();
        UpdateDownload updateDown = new UpdateDownload();
        updateDown.start();
        Download download = new Download();
        download.start();
        while (true) {
            List<String> allFille = FileAndFolder.getAllFille();
            if (allFille.size()>0 && UploadManager.flag == false) {
                try {
                    UploadManager.flag = true;
                    UploadManager.startThread();
                } catch (IOException | SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            System.out.println(checkStorage());
            System.out.println("suspended: " + download.isSuspended());
            if (!checkStorage() && !download.isSuspended()) {
                download.suspendThread();
                System.out.println("Interupt");
            }
            if (checkStorage() && download.isSuspended()) {
                download.resumeThread();
                System.out.println("Start");
            }

            try {
                Thread.sleep(30000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
