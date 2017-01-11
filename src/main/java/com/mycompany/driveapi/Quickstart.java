/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.driveapi;

import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.util.List;

public class Quickstart {

    /**
     * Application name.
     *
     * @param args
     * @throws java.io.IOException
     */

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Drive service = DriveService.getDriveService();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
                .setFields("nextPageToken, files(id, name)")
                .execute();
        while (true) {
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                System.out.println("Files:");
                for (File file : files) {
                    System.out.printf("%s (%s) %s\n", file.getName(), file.getId(),file.getDescription());
                }
            }
            result = result.setNextPageToken(result.getNextPageToken());
            System.out.println(result.getNextPageToken());
            if (result==null) break;
        }
    }
}
