package com.mycompany.driveapi;

import UI.UploadFile;
import UI.UploadManager;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;

import javax.swing.*;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * Created by super on 1/9/2017.
 */
public class FileUploadProgressListener implements MediaHttpUploaderProgressListener {

    private String progress = "";
    private UploadFile uploadManager;
    public FileUploadProgressListener(UploadFile uploadManager) {
    this.uploadManager=uploadManager;
    }

    @Override
    public void progressChanged(MediaHttpUploader mediaHttpUploader) throws IOException {
        switch (mediaHttpUploader.getUploadState()) {
            case INITIATION_STARTED:
                uploadManager.updateStatus("Upload Initiation has started.");
                break;
            case INITIATION_COMPLETE:
                uploadManager.updateStatus("Upload Initiation is Complete.");
                break;
            case MEDIA_IN_PROGRESS:
                uploadManager.updateStatus("Upload is In Progress: "
                        + NumberFormat.getPercentInstance().format(mediaHttpUploader.getProgress()));
                break;
            case MEDIA_COMPLETE:
                uploadManager.updateStatus("Upload is Complete!");
                break;
        }
    }

    public String getProgress() {
        return progress;
    }
}