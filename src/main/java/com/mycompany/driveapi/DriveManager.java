package com.mycompany.driveapi;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by super on 1/9/2017.
 */
public class DriveManager {

    public void upload() throws IOException {
        Drive service = DriveService.getDriveService();
        File driveFile = new File();


        driveFile.setMimeType("application/vnd.google-apps.unknown");
        driveFile.setName("text.exe");

        java.io.File f = new java.io.File("C:\\Users\\super\\Desktop\\376.33-notebook-win10-64bit-international-whql.exe");
        FileContent fileContent = new FileContent("application/vnd.google-apps.unknown", f);
        Drive.Files.Create create = service.files().create(driveFile, fileContent).setFields("id");
        MediaHttpUploader mediaHttpUploader = create.getMediaHttpUploader();
        mediaHttpUploader.setProgressListener(new FileUploadProgressListener());
        File uploadedFile =create.execute();
                System.out.println(uploadedFile.getId());

        File file = service.files().get(uploadedFile.getId()).execute();
//        List<Permission> permissions = new ArrayList<>();
        Permission p = new Permission();
        p.setType("anyone");
        p.setRole("reader");
//        permissions.add(p);
//        driveFile.setPermissions(permissions);

//        file.setPermissions(permissions);
//        File updatedFile=service.files().update(uploadedFile.getId(),file);

        service.permissions().create(file.getId(),p).setFields("id").execute();



//        System.out.println(file.setPermissions(permissions));
    }

    public static void main(String[] args) throws IOException {
        DriveManager d = new DriveManager();
        d.upload();
    }
}
