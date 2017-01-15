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
public class Episode {

    public int id;
    public int movieId;
    public int ep;
    public String url;
    public String file;
    public byte download;
    public byte upload;
    public String ggId;
    public int add;
    
    public Episode(int id, int movieId, int ep, String url, String file, byte download, byte upload) {
        this.id = id;
        this.movieId = movieId;
        this.ep = ep;
        this.url = url;
        this.file = file;
        this.download = download;
        this.upload = upload;
    }

    public Episode(int id, int movieId, int ep, String url, String file, byte download, byte upload, String ggId) {
        this.id = id;
        this.movieId = movieId;
        this.ep = ep;
        this.url = url;
        this.file = file;
        this.download = download;
        this.upload = upload;
        this.ggId = ggId;
    }
    
}
