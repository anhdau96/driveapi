package com.mycompany.driveapi;

        import Config.ConfigService;
        import UI.UploadFile;
        import UI.UploadManager;
        import com.google.api.client.googleapis.media.MediaHttpUploader;
        import com.google.api.client.http.FileContent;
        import com.google.api.services.drive.Drive;
        import com.google.api.services.drive.model.About;
        import com.google.api.services.drive.model.File;
        import com.google.api.services.drive.model.Permission;

        import javax.swing.*;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.util.ArrayList;

/**
 * Created by super on 1/9/2017.
 */
public class DriveManager {
    private Drive service;
    private String progress="nothing";

    public DriveManager() throws IOException {
        service = DriveService.getDriveService();
    }

    public About.StorageQuota getStorageInfo() throws IOException {
        About about = service.about().get().setFields("*").execute();
        return about.getStorageQuota();
    }


//    public File upload(java.io.File file) throws IOException {
//        //setup
//        File driveFile = new File();
//
//        driveFile.setMimeType(Files.probeContentType(file.toPath()));
//        driveFile.setName(file.getName());
//
//        ArrayList<String> objects = new ArrayList<>();
//        objects.add("1VhD1CrtG83avoACHqGR2SpQig67zrkDrGBe3rZC4qwQ");
//        driveFile.setParents(objects);
//
//        FileContent fileContent = new FileContent(Files.probeContentType(file.toPath()), file);
//        Drive.Files.Create create = service.files().create(driveFile, fileContent).setFields("*");
//        MediaHttpUploader mediaHttpUploader = create.getMediaHttpUploader();
//        FileUploadProgressListener uploadProgressListener=new FileUploadProgressListener();
//        System.out.println(uploadProgressListener.getProgress());
//        mediaHttpUploader.setProgressListener(uploadProgressListener);
//
//        //thực hiện upload
//        File uploadedFile = create.execute();
//        uploadedFile=getDriveFile(uploadedFile.getId());
//        System.out.println(uploadedFile.getId());
//        //Lấy về link drive để xem
//        System.out.println(uploadedFile.getWebViewLink());
//        System.out.println(uploadedFile.getMimeType());
//        //share public
//        Permission p = new Permission();
//        p.setType("anyone");
//        p.setRole("reader");
//        service.permissions().create(uploadedFile.getId(), p).execute();
//        return uploadedFile;
//    }

    public File upload(java.io.File file, UploadFile uploadManager) throws IOException {
        //setup
        File driveFile = new File();

        driveFile.setMimeType(Files.probeContentType(file.toPath()));
        driveFile.setName(file.getName());

        ArrayList<String> objects = new ArrayList<>();
        objects.add("1VhD1CrtG83avoACHqGR2SpQig67zrkDrGBe3rZC4qwQ");
        driveFile.setParents(objects);

        FileContent fileContent = new FileContent(Files.probeContentType(file.toPath()), file);
        Drive.Files.Create create = service.files().create(driveFile, fileContent).setFields("*");
        MediaHttpUploader mediaHttpUploader = create.getMediaHttpUploader();
        FileUploadProgressListener uploadProgressListener=new FileUploadProgressListener(uploadManager);
        System.out.println(uploadProgressListener.getProgress());
        mediaHttpUploader.setProgressListener(uploadProgressListener);

        //thực hiện upload
        File uploadedFile = create.execute();
        uploadedFile=getDriveFile(uploadedFile.getId());
        System.out.println(uploadedFile.getId());
        //Lấy về link drive để xem
        System.out.println(uploadedFile.getWebViewLink());
        System.out.println(uploadedFile.getMimeType());
        //share public
        Permission p = new Permission();
        p.setType("anyone");
        p.setRole("reader");
        service.permissions().create(uploadedFile.getId(), p).execute();
        return uploadedFile;
    }

    public File getDriveFile(String id) throws IOException {
        return service.files().get(id).setFields("*").execute();
    }



    public static void main(String[] args) throws IOException {
        java.io.File saveFolder=new java.io.File(ConfigService.getInstance().get("savePath"));
        System.out.println(ConfigService.getInstance().get("savePath"));
        java.io.File[] files = saveFolder.listFiles();
        for (java.io.File f:files) {
            System.out.println(f.getName());
        }
        DriveManager d = new DriveManager();
        java.io.File f=new java.io.File("C:\\Users\\super\\Desktop\\SampleVideo_1280x720_1mb.mp4");
        d.upload(f,new UploadFile(f));
//        System.out.println(d.getStorageInfo().getUsageInDrive()/1024/1024+"mb");
//        System.out.println(d.getStorageInfo().getLimit()/1024/1024+"mb");

    }
}
