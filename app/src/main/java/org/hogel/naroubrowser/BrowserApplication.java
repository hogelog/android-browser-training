package org.hogel.naroubrowser;

import android.app.Application;
import android.content.Context;
import android.webkit.WebView;
import com.splunk.mint.Mint;
import org.hogel.naroubrowser.di.BrowserComponent;

public class BrowserApplication extends Application {

    private BrowserComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Mint.initAndStartSession(this, BuildConfig.MINT_API_KEY);
        if (BuildConfig.DEBUG) {
            Mint.enableDebug();
            Mint.flush();

            WebView.setWebContentsDebuggingEnabled(true);
        }

        buildComponentAndInject();
        StethoService.initialize(this);
    }

    public void buildComponentAndInject() {
        component = BrowserComponent.Initializer.init(this);
    }

    public static BrowserComponent component(Context context) {
        return ((BrowserApplication) context.getApplicationContext()).component;
    }
}
