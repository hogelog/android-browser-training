package org.hogel.naroubrowser;

import android.app.Application;
import android.webkit.WebView;
import com.crashlytics.android.Crashlytics;
import com.google.inject.Inject;
import io.fabric.sdk.android.Fabric;
import org.hogel.naroubrowser.di.Guices;
import org.hogel.naroubrowser.services.DatabaseService;
import org.hogel.naroubrowser.services.StethoService;
import roboguice.inject.RoboInjector;

public class BrowserApplication extends Application {

    @Inject
    DatabaseService databaseService;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        StethoService.initialize(this);

        RoboInjector injector = Guices.initialize(this);
        injector.injectMembers(this);

        databaseService.migrate();
    }
}
