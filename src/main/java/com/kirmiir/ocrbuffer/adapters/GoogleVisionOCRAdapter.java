package com.kirmiir.ocrbuffer.adapters;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kirmiir on 16.08.2020.
 */
public class GoogleVisionOCRAdapter implements IOCRAdapter {
    private static Logger log = Logger.getLogger(GoogleVisionOCRAdapter.class.getName());

    @Override
    public String getTextFromImage(BufferedImage image) throws Exception {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            ByteString imgBytes = ByteString.copyFrom(imageInByte);

            List<AnnotateImageRequest> visionRequests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature textFeature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
            AnnotateImageRequest visionRequest =
                    AnnotateImageRequest.newBuilder().addFeatures(textFeature).setImage(img).build();
            visionRequests.add(visionRequest);

            AnnotateImageResponse visionResponse;
            try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                visionResponse = client.batchAnnotateImages(visionRequests).getResponses(0);
                if (visionResponse == null || !visionResponse.hasFullTextAnnotation()) {
                    log.info("Image contains no text");
                    return null;
                }

                if (visionResponse.hasError()) {
                    log.log(Level.SEVERE, "Error in vision API call: " + visionResponse.getError().getMessage());
                    return null;
                }
            } catch (IOException e) {
                log.log(Level.SEVERE, "Error detecting text: " + e.getMessage(), e);
                return null;
            }

            return visionResponse.getFullTextAnnotation().getText();
        }
        catch (Exception ex){

            log.log(Level.SEVERE, "Error detecting text: " + ex.getMessage(), ex);
            throw ex;
        }
    }
}
