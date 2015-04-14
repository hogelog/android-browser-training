package org.hogel.naroubrowser;

import android.app.Application;
import android.webkit.WebView;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.splunk.mint.Mint;
import org.hogel.naroubrowser.di.BrowserModule;
import org.hogel.naroubrowser.services.DatabaseService;
import roboguice.RoboGuice;

public class BrowserApplication extends Application {

    @Inject
    DatabaseService databaseService;

    private Injector injector;

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

        injector = RoboGuice.getOrCreateBaseApplicationInjector(this, Stage.PRODUCTION, new BrowserModule());
        injector.injectMembers(this);

        databaseService.migrate();
    }
}
