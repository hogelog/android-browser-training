package org.hogel.naroubrowser;

import android.app.Application;
import android.webkit.WebView;
import org.hogel.naroubrowser.initializer.GuiceInitializer;
import org.hogel.naroubrowser.initializer.StethoInitializer;

public class BrowserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        StethoInitializer.initialize(this);
        GuiceInitializer.initialize(this);
    }
}
