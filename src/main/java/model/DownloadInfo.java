/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Viet Bac
 */
public class DownloadInfo {
    public int id;
    public String link;
    public String file;
    public int eps;
    public DownloadInfo(int id, String link, String file, int eps) {
        this.id = id;
        this.link = link;
        this.file = file;
        this.eps = eps;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" + "id=" + id + ", link=" + link + ", file=" + file + ", eps=" + eps + '}';
    }
    
}
