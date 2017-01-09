package com.mycompany.driveapi;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * Created by super on 1/9/2017.
 */
public class FileUploadProgressListener implements MediaHttpUploaderProgressListener {

    @Override
    public void progressChanged(MediaHttpUploader mediaHttpUploader) throws IOException {
        switch (mediaHttpUploader.getUploadState()) {
            case INITIATION_STARTED:
                System.out.println("Upload Initiation has started.");
                break;
            case INITIATION_COMPLETE:
                System.out.println("Upload Initiation is Complete.");
                break;
            case MEDIA_IN_PROGRESS:
                System.out.println("Upload is In Progress: "
                        + NumberFormat.getPercentInstance().format(mediaHttpUploader.getProgress()));
                break;
            case MEDIA_COMPLETE:
                System.out.println("Upload is Complete!");
                break;
        }
    }
}