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
public class Movie {
    public int id;
    public String name;
    public String year;
    public String url;
    public String file;
    public String quality;
    public byte download;
    public byte upload;
    public String ggId;
    public String season;
    public int add;

    public Movie(int id, String name, String year, String url, String file, String quality, byte download, byte upload) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.url = url;
        this.file = file;
        this.quality = quality;
        this.download = download;
        this.upload = upload;
    }

    public Movie(int id, String name, String year, String url, String file, String quality, byte download, byte upload, String ggId) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.url = url;
        this.file = file;
        this.quality = quality;
        this.download = download;
        this.upload = upload;
        this.ggId = ggId;
    }

    public Movie(int id, String name, String year, String url, String file, String quality, byte download, byte upload, String ggId, String season) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.url = url;
        this.file = file;
        this.quality = quality;
        this.download = download;
        this.upload = upload;
        this.ggId = ggId;
        this.season = season;
    }
}
