package com.kirmiir.ocrbuffer.adapters;

import java.awt.image.BufferedImage;

public interface IOCRAdapter {
    String getTextFromImage(BufferedImage image) throws Exception;
}
