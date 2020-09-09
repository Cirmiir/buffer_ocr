package com.kirmiir.ocrbuffer.actor;

import com.kirmiir.ocrbuffer.managers.ClipBoardManager;
import com.kirmiir.ocrbuffer.OCRCommand;
import com.kirmiir.ocrbuffer.managers.OCRManager;
import com.kirmiir.ocrbuffer.TrayMenu;
import com.kirmiir.ocrbuffer.adapters.AdapterFactory;
import com.kirmiir.ocrbuffer.adapters.IOCRAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class OCRActor implements IActor, ActionListener {
    private static final Logger log = Logger.getLogger( TrayMenu.class.getName() );

    private static final long MIN_DELAY_MS = 1000;
    private eOCRRrovider provider;

    private long mLastClickTime;

    public OCRActor(){
        provider = eOCRRrovider.Tesseract;
    }

    @Override
    public void execute() {
        long lastClickTime = mLastClickTime;
        long now = System.currentTimeMillis();
        mLastClickTime = now;

        if (now - lastClickTime < MIN_DELAY_MS) {
            return;
        }

        ClipBoardManager clipboardManager = ClipBoardManager.GetInstance();
        BufferedImage image = clipboardManager.getCurrentImageFromClipboard();

        if (image == null)
            return;

        IOCRAdapter adapter = AdapterFactory.getInstance().createProvider(this.provider);
        OCRManager manager = OCRManager.GetInstance();

        OCRCommand command = new OCRCommand(adapter, image, s -> {
            try {
                ClipBoardManager.GetInstance().addToClipboard(s);
            } catch (Exception ex) {
                log.severe(ex.getMessage());
            }
        });

        manager.PerformOCR(command);

    }

    @Override
    public void changeProvider(eOCRRrovider provider) {
        this.provider = provider;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }
}
