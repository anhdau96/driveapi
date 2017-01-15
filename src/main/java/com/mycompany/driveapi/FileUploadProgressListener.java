package com.mycompany.driveapi;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import crawl.CrawlUI;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * Created by super on 1/9/2017.
 */
public class FileUploadProgressListener implements MediaHttpUploaderProgressListener {

    private String progress = "";
    private CrawlUI uploadManager;
    public FileUploadProgressListener(CrawlUI uploadManager) {
        this.uploadManager=uploadManager;
    }

    @Override
    public void progressChanged(MediaHttpUploader mediaHttpUploader) throws IOException {
        
        switch (mediaHttpUploader.getUploadState()) {
            case INITIATION_STARTED:
                uploadManager.updateStatus("Upload Initiation has started.\n");
                System.out.println("start");
                break;
            case INITIATION_COMPLETE:
                uploadManager.updateStatus("Upload Initiation is Complete.\n");
                break;
            case MEDIA_IN_PROGRESS:
                System.out.println("50%");
                uploadManager.updateStatus("Upload is In Progress: "
                        + NumberFormat.getPercentInstance().format(mediaHttpUploader.getProgress())+ "\n");
                break;
            case MEDIA_COMPLETE:
                uploadManager.updateStatus("Upload is Complete! \n");
                break;
        }
    }

    public String getProgress() {
        return progress;
    }
}