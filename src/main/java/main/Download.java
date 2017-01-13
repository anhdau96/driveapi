/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controller.DBController;
import download.DownloadUlti;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DownloadInfo;

/**
 *
 * @author Administrator
 */
public class Download extends Thread {

    private volatile boolean isSuspended;
    private volatile boolean isStop;

    public boolean isSuspended() {
        return isSuspended;
    }

    public void suspendThread() {
        this.isSuspended = true;
    }

    public synchronized void resumeThread() {
        this.isSuspended = false;
        notify();
    }

    public synchronized void stopThread() {
        this.isStop = true;
        notify();
    }

    @Override
    public void run() {
        while (!this.isStop) {
            DBController contr = new DBController();
            try {
                List<DownloadInfo> notDownload = contr.getNotDownload();
                for (DownloadInfo downloadInfo : notDownload) {
                    if (this.isSuspended) {
                        synchronized (this) {
                            while (this.isSuspended) {
                                wait();
                            }
                        }
                    }
                    System.out.println(downloadInfo.toString());
                    DownloadUlti.download(downloadInfo.link.concat("watching.html"), downloadInfo.file, downloadInfo.eps);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
