package com.kirmiir.ocrbuffer.ocrproviders;

import java.awt.image.BufferedImage;

/**
 * Created by Kirmiir on 16.08.2020.
 */
public interface IOCRProvider {
    String GetTextFromImage(byte[] array) throws Exception;
    String GetTextFromImage(BufferedImage image) throws Exception;
}
