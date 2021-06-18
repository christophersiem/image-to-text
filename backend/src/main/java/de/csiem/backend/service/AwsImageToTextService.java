package de.csiem.backend.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import de.csiem.backend.model.DetectedText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AwsImageToTextService {
    @Value("${aws.bucket.name}")
    private String bucketName;


    public Optional<DetectedText> getTextFromImage(String photo, String imageUrl) {
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        DetectTextRequest request = new DetectTextRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo)
                                .withBucket(bucketName)));

        try {
            DetectTextResult result = rekognitionClient.detectText(request);
            List<TextDetection> textDetections = result.getTextDetections();

            return textDetections.stream()
                    .filter(textDetection -> textDetection.getId().equals(0))
                    .map(textDetection -> DetectedText.builder().confidence(textDetection.getConfidence()).text(textDetection.getDetectedText()).imageUrl(imageUrl).build())
                    .findFirst();

        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        } return Optional.empty();
    }
}
