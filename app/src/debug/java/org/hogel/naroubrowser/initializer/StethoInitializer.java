package org.hogel.naroubrowser.initializer;

import com.facebook.stetho.Stetho;
import org.hogel.naroubrowser.BrowserApplication;

public class StethoInitializer {
    public static void initialize(BrowserApplication app) {
        Stetho.initializeWithDefaults(app);
    }
}
