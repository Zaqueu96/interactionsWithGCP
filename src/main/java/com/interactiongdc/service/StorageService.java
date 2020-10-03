package com.interactiongdc.service;

import static com.google.cloud.storage.BlobInfo.newBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.interactiongdc.model.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    final String bucketName = "interaction_gcp";
    // private StorageOptions storageOptions = StorageOptions.getDefaultInstance().
    private Storage storage = StorageOptions.getDefaultInstance().getService();

    public File upload(final MultipartFile file) throws IOException {
        Date time = new Date();
        final File resultFile = new File();
        final byte[] bytes = file.getBytes();
        final Blob blob = this.storage.create(
                newBuilder(this.bucketName, time.getTime() + "." + this.getExtension(file.getOriginalFilename()))
                        .build(),
                bytes);
        resultFile.setFileName(file.getOriginalFilename());
        resultFile.setKeyBucket(blob.getBlobId().getName());
        resultFile.setContentType(blob.getContentDisposition());
        resultFile.setExtension(this.getExtension(file.getOriginalFilename()));
        return resultFile;
    }

    public String getFileContent(File file) {
        Blob blob = storage.get(bucketName, file.getKeyBucket());
        return new String(blob.getContent());
    }

    public boolean deleteFile(File file) {
        Blob blob = storage.get(bucketName, file.getKeyBucket());
        return storage.delete(blob.getBlobId());
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
