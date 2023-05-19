package com.example.springdemo;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.*;

import java.util.List;

@Slf4j
public class InvalidationUtils {

    /**
     * AWS CloudFront Cache 무효화 생성
     * @param distributionId 배포 ID
     * @param cachePaths 객체 경로
     */
    public static void createInvalidation(String distributionId, List<String> cachePaths) {
        CloudFrontClient cloudFrontClient = CloudFrontClient.builder()
                .region(Region.AWS_GLOBAL)
                .build();

        String callerReference = String.valueOf(System.currentTimeMillis());
        Paths paths = Paths.builder().items(cachePaths).quantity(cachePaths.size()).build();
        InvalidationBatch invalidationBatch = InvalidationBatch.builder()
                .callerReference(callerReference)
                .paths(paths)
                .build();

        CreateInvalidationRequest invalidationRequest = CreateInvalidationRequest.builder()
                .distributionId(distributionId)
                .invalidationBatch(invalidationBatch)
                .build();

        try {
            CreateInvalidationResponse invalidationResponse = cloudFrontClient.createInvalidation(invalidationRequest);
            String invalidationId = invalidationResponse.invalidation().id();
            log.info("AWS CloudFront Invalidation request sent ID: " + invalidationId);
        } catch (CloudFrontException e) {
            log.error(e.awsErrorDetails().errorMessage());
        }

    }
}
