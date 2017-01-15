/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import crawl.Crawl;
import crawl.CrawlUI;
import database.InitDatabase;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Viet Bac
 */
public class Main extends Thread{
    public boolean init;
    @Override
    public void run() {
        runThread(); 
    }
    
    public static boolean checkStorage() {
        File file = new File("C:");
        long freeSpace = file.getFreeSpace();
//        System.out.println(freeSpace / 1024 / 1024 / 1024 + "GB");
        return (freeSpace / 1024 / 1024 / 1024) >=14 ;
    }

    public void runThread() {
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
        UpdateUpload upload = new UpdateUpload();
        upload.c = c.cUI;
        upload.start();
        while (true) {
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
