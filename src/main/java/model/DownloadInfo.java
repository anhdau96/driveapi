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
    public String type;
    public String file;
    public int eps;
    public DownloadInfo(int id, String type, String file, int eps) {
        this.id = id;
        this.type = type;
        this.file = file;
        this.eps = eps;
    }
}