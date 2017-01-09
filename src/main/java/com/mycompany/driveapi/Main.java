/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.driveapi;

import java.io.IOException;

/**
 *
 * @author Viet Bac
 */
public class Main {
    public static void main(String[] args) throws IOException {
        DriveManager dm=new DriveManager();
        dm.upload();
    }
}
