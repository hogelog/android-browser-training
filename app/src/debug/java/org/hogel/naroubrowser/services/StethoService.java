package org.hogel.naroubrowser.services;

import com.facebook.stetho.Stetho;
import org.hogel.naroubrowser.BrowserApplication;

public class StethoService {
    public static void initialize(BrowserApplication app) {
        Stetho.initializeWithDefaults(app);
    }
}
