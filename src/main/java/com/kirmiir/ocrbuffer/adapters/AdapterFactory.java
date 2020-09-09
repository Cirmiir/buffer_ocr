package com.kirmiir.ocrbuffer.adapters;

import com.kirmiir.ocrbuffer.actor.eOCRRrovider;

public class AdapterFactory {
    private static AdapterFactory _instance;

    public static AdapterFactory getInstance(){
        if (_instance == null){
            _instance = new AdapterFactory();
        }
        return _instance;
    }

    public IOCRAdapter createProvider(eOCRRrovider provider){
        switch (provider){
            case Tesseract:
                return new TesseractOCRAdapter();
            case GoogleVision:
                return new GoogleVisionOCRAdapter();
        }

        return new TesseractOCRAdapter();
    }
}
