package org.hogel.naroubrowser;

import android.app.Application;
import com.splunk.mint.Mint;

public class BrowserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Mint.initAndStartSession(this, BuildConfig.MINT_API_KEY);
        if (BuildConfig.DEBUG) {
            Mint.enableDebug();
            Mint.flush();
        }
    }
}
