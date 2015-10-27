package org.hogel.naroubrowser;

import android.app.Application;
import android.webkit.WebView;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;
import org.hogel.naroubrowser.initializer.GuiceInitializer;
import org.hogel.naroubrowser.initializer.StethoInitializer;

public class BrowserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics(), new Answers());

        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        StethoInitializer.initialize(this);
        GuiceInitializer.initialize(this);
    }
}
