package com.kirmiir.ocrbuffer.ocrproviders;

import com.kirmiir.ocrbuffer.OCRCommand;
import java.util.concurrent.atomic.AtomicBoolean;

public class SingleExecutor {
    private final AtomicBoolean isRunning = new AtomicBoolean();

    public void Perform(OCRCommand command) {
        if (isRunning.get())
            return;
        isRunning.set(true);
        new Thread(() -> {
            try {
                command.Execute();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isRunning.set(false);
            }
        }).start();
    }

}
