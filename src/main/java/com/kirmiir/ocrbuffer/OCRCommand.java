package com.kirmiir.ocrbuffer;

import com.kirmiir.ocrbuffer.adapters.IOCRAdapter;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class OCRCommand {
    private final IOCRAdapter adapter;
    private final BufferedImage image;
    private final Consumer<String> consumer;

    public OCRCommand(IOCRAdapter adapter, BufferedImage image, Consumer<String> consumer) {
        this.adapter = adapter;
        this.image = image;
        this.consumer = consumer;
    }

    public void Execute() throws Exception {
        String text = adapter.getTextFromImage(image);
        consumer.accept(text);
    }
}
