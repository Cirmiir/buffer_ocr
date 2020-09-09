package com.kirmiir.ocrbuffer;

import com.kirmiir.ocrbuffer.actor.OCRActor;
import com.kirmiir.ocrbuffer.actor.eOCRRrovider;
import com.kirmiir.ocrbuffer.globalkeylistener.GlobalKeyListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrayMenu {
    private static final Logger log = Logger.getLogger( TrayMenu.class.getName() );

    public void CreateTrayMenu(OCRActor OCRActor){
        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();

            Image iconImage = new ImageIcon(Main.getFileFromResources("icon.png")).getImage();

            ActionListener exitListener = Main::exit;

            PopupMenu popup = new PopupMenu();

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(exitListener);

            MenuItem ocrItem = new MenuItem("OCR");
            ocrItem.addActionListener(OCRActor);

            Menu providerItem = new Menu("OCR Provider");

            MenuItem tesseractProvider = new MenuItem("Tesseract");
            tesseractProvider.addActionListener(e -> OCRActor.changeProvider(eOCRRrovider.Tesseract));

            MenuItem visionProvider = new MenuItem("Google vision");
            visionProvider.addActionListener(e -> OCRActor.changeProvider(eOCRRrovider.GoogleVision));

            providerItem.add(tesseractProvider);
            providerItem.add(visionProvider);

            popup.add(closeItem);
            popup.add(providerItem);
            popup.add(ocrItem);

            TrayIcon trayIcon = new TrayIcon(iconImage, "Buffer OCR", popup);
            trayIcon.setImageAutoSize(true);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                log.warning("Tray doesn't .");
            }

            try {
                Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
                logger.setLevel(Level.OFF);
                GlobalScreen.registerNativeHook();
            }
            catch (NativeHookException ex) {
                log.warning("Native hook can not be registered.");
            }

            GlobalKeyListener listener = new GlobalKeyListener();
            listener.addAction(OCRActor);

            GlobalScreen.addNativeKeyListener(listener);
        }
    }
}
