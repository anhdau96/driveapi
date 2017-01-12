/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.DBController;
import crawl.Crawl;
import crawl.CrawlUI;
import database.InitDatabase;
import download.DownloadUlti;
import fileandfolder.FileAndFolder;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DownloadInfo;

/**
 *
 * @author Viet Bac
 */
public class Main {

    public static void main(String[] args) {
        try {
            InitDatabase.create();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Crawl c = new Crawl();
        c.cUI = new CrawlUI();
        c.start();
        while (true) {
            List<String> allFille = FileAndFolder.getAllFille();
            DBController contr = new DBController();
            for (String string : allFille) {
                try {
                    contr.updateDownload(string);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                List<DownloadInfo> notDownload = contr.getNotDownload();
                for (DownloadInfo downloadInfo : notDownload) {
                    DownloadUlti.download(downloadInfo.link, downloadInfo.file, downloadInfo.eps);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
