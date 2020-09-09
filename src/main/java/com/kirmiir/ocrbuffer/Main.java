package com.kirmiir.ocrbuffer;

import com.kirmiir.ocrbuffer.actor.OCRActor;
import com.kirmiir.ocrbuffer.globalkeylistener.GlobalKeyListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kirmiir on 16.08.2020.
 */
public class Main {
    private static final Logger log = Logger.getLogger( Main.class.getName() );

    public static String getFileFromResources(String fileName) {

        ClassLoader classLoader = Main.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return resource.getFile();
        }

    }

    public static void main(String[] args) {
        TrayMenu menu = new TrayMenu();
        OCRActor OCRActor = new OCRActor();
        menu.CreateTrayMenu(OCRActor);


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

    public static void exit(ActionEvent e) {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex){
            log.severe( ex.getMessage());
        }

        System.exit(0);
    }
}
