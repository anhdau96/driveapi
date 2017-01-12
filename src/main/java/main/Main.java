/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import crawl.Crawl;
import crawl.CrawlUI;
import database.InitDatabase;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        try {
            c.Crawl();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
