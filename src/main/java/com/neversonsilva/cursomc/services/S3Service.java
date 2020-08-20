package com.neversonsilva.cursomc.services;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3Service {
    
    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    public void uploadFile(String localFilePath) {

        try {
            LOG.info("Iniciando upload");
            File file = new File(localFilePath);
            s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
            LOG.info("upload finalizado");
        } catch (AmazonServiceException e) {
            LOG.info("AmazonServiceException: " + e.getErrorMessage());
            LOG.info("Status code: " + e.getStatusCode());
        } catch (AmazonClientException ace) {
            LOG.info("AmazonClientException: " + ace.getMessage());
        }

    }

}