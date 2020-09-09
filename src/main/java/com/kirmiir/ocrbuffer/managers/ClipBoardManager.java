package com.kirmiir.ocrbuffer.managers;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Kirmiir on 16.08.2020.
 */
public class ClipBoardManager {

    private static ClipBoardManager _instance;
    private final static Logger log = Logger.getLogger(ClipBoardManager.class.getName());

    public static ClipBoardManager GetInstance(){
        if (_instance == null){
            _instance = new ClipBoardManager();
        }
        return _instance;
    }

    public void addToClipboard(String string){
        StringSelection selection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public BufferedImage getCurrentImageFromClipboard(){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            return (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
        } catch (IOException ex) {
            log.warning("There is no access to clipboard.");
        } catch (UnsupportedFlavorException ex){
            log.warning("Clipboard doesn't contain support image.");
        }
        return null;
    }
}
