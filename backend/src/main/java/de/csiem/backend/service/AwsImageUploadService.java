package de.csiem.backend.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.csiem.backend.model.DetectedText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class AwsImageUploadService {

    private final AwsImageToTextService awsImageToTextService;

    Regions clientRegion = Regions.EU_CENTRAL_1;
    @Value("${aws.access.key}")
    private String accessKey;
    @Value("${aws.secret.key}")
    private String secretKey;
    @Value("${aws.bucket.name}")
    private String bucketName;

    public AwsImageUploadService(AwsImageToTextService awsImageToTextService) {
        this.awsImageToTextService = awsImageToTextService;
    }


    public DetectedText uploadImage(MultipartFile file) throws IOException {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

            ObjectMetadata meta = new ObjectMetadata();
            PutObjectRequest request = new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), meta);
            s3Client.putObject(request);

        } catch (SdkClientException e) {
            e.printStackTrace();
        }
        String imageUrl = getPreSignedUrl(file.getOriginalFilename());
        Optional<DetectedText> textFromImage = awsImageToTextService.getTextFromImage(file.getOriginalFilename(),imageUrl);
        return textFromImage.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No text detected"));
    }

    private String getPreSignedUrl(String fileName){

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            Date expiration = getExpirationDate();
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, fileName)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Date getExpirationDate() {
        Date expiration = new Date();
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
