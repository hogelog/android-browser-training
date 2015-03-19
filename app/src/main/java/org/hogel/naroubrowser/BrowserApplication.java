package org.hogel.naroubrowser;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import com.github.hotchpotch.iconicfontengine.IconicFontEngine;
import com.splunk.mint.Mint;
import org.hogel.naroubrowser.di.BrowserComponent;
import org.hogel.naroubrowser.views.FontAwesomeEngine;

public class BrowserApplication extends Application {

    private BrowserComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Mint.initAndStartSession(this, BuildConfig.MINT_API_KEY);
        if (BuildConfig.DEBUG) {
            Mint.enableDebug();
            Mint.flush();
        }

        buildComponentAndInject();

        IconicFontEngine.addDefaultEngine(
            new FontAwesomeEngine(Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf"))
        );
    }

    public void buildComponentAndInject() {
        component = BrowserComponent.Initializer.init(this);
    }

    public static BrowserComponent component(Context context) {
        return ((BrowserApplication) context.getApplicationContext()).component;
    }
}
