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
    public DownloadInfo(int id, String type, String link, int eps) {
        this.id = id;
        this.link = link;
        this.file = file;
        this.eps = eps;
    }
}
