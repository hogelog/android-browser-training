package org.hogel.naroubrowser;

import android.app.Application;
import android.webkit.WebView;
import com.google.inject.Inject;
import com.splunk.mint.Mint;
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

        Mint.initAndStartSession(this, BuildConfig.MINT_API_KEY);
        if (BuildConfig.DEBUG) {
            Mint.enableDebug();
            Mint.flush();

            WebView.setWebContentsDebuggingEnabled(true);
        }

        StethoService.initialize(this);

        RoboInjector injector = Guices.initialize(this);
        injector.injectMembers(this);

        databaseService.migrate();
    }
}
