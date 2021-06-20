package de.csiem.backend.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import de.csiem.backend.model.DetectedText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AwsImageToTextService {
    @Value("${aws.bucket.name}")
    private String bucketName;


    public DetectedText getTextFromImage(String photo, String imageUrl) {
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        DetectTextRequest request = new DetectTextRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photo)
                                .withBucket(bucketName)));

        try {
            DetectTextResult result = rekognitionClient.detectText(request);
            List<TextDetection> textDetections = result.getTextDetections();

            String detectedTextAsString = textDetections.stream()
                    .filter(textDetection -> textDetection.getType().equals("LINE"))
                    .filter(textDetection -> textDetection.getConfidence() > 80)
                    .map(TextDetection::getDetectedText).collect(Collectors.joining(" "));


            double averageConfidence =  textDetections.stream()
                    .filter(textDetection -> textDetection.getType().equals("LINE"))
                    .map(TextDetection::getConfidence)
                    .filter(confidence -> confidence > 80).mapToDouble(d -> d).average().orElse(0.0);



            return DetectedText.builder().text(detectedTextAsString).url(imageUrl).confidence(averageConfidence).build();

        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        } return null;
    }
}
