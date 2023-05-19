package com.example.springdemo;

// import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.*;

public class springdemo {
    public static void main(String[] args) {

        System.setProperty("aws.accessKeyId", "AKIAYYURNDQ7VUU64HXM");
        System.setProperty("aws.secretAccessKey", "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");

        CloudFrontClient cloudFrontClient = CloudFrontClient.builder()
                .region(Region.AWS_GLOBAL)  // 지역을 적절히 변경하세요.
                .build();

        String distributionId = "E3T4RPE8RP0CGI";
        String pathPattern = "/media/test/20230503/*";

        

        CreateInvalidationRequest invalidationRequest = CreateInvalidationRequest.builder()
                .distributionId(distributionId)
                .invalidationBatch(InvalidationBatch.builder()
                        .paths(Paths.builder()
                                .items(pathPattern)
                                .quantity(1)
                                .build())
                        .callerReference(String.valueOf(System.currentTimeMillis()))
                        .build())
                .build();

        CreateInvalidationResponse invalidationResponse = cloudFrontClient.createInvalidation(invalidationRequest);

        System.out.println("Invalidation created with ID: " + invalidationResponse.invalidation().id());
    }
}