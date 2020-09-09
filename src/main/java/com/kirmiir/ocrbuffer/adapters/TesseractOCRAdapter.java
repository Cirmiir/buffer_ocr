package com.kirmiir.ocrbuffer.adapters;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.awt.image.BufferedImage;

public class TesseractOCRAdapter implements IOCRAdapter {

    @Override
    public String getTextFromImage(BufferedImage image) throws Exception {
        ITesseract tesseract = new Tesseract();
        return tesseract.doOCR(image);
    }
}
