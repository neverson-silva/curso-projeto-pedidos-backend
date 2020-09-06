package com.neversonsilva.cursomc.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class S3Config {
    
    @Value("${aws.acess_key_id}")
    private String awsId;

    @Value("${aws.secret_acess_key}")
    private String awsKey;

    @Value("${s3.region}")
    private String region;

    @Autowired
    private Environment environment;

    @Bean
    public AmazonS3 s3client() {
        
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(getAwsId(), getAwsKey());
        AmazonS3 client = AmazonS3ClientBuilder.standard() 
                                                .withRegion(Regions.fromName(region))
                                                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials) )
                                                .build();
        return client;
    }

    public String getAwsId() {
        if (awsId == null || awsId.equals("0")) {
            awsId = environment.getProperty("AWS_ACESS_KEY_ID");
        }
        return awsId;
    }

    public String getAwsKey() {
        if (awsKey == null || awsKey.equals("0")) {
            awsKey = environment.getProperty("AWS_SECRET_KEY");
        }
        return awsKey;
    }
}