package com.kirmiir.ocrbuffer.managers;
import com.kirmiir.ocrbuffer.OCRCommand;
import com.kirmiir.ocrbuffer.ocrproviders.SingleExecutor;

public class OCRManager {
    private static OCRManager _instance;
    private final SingleExecutor _executor;

    private OCRManager(){
        _executor = new SingleExecutor();
    }

    public static OCRManager GetInstance(){
        if (_instance == null){
            _instance = new OCRManager();
        }
        return _instance;
    }

    public void PerformOCR(OCRCommand command){
        _executor.Perform(command);
    }
}