package org.hogel.naroubrowser.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.splunk.mint.Mint;
import org.hogel.naroubrowser.consts.UrlConst;

public class MainWebView extends WebView {
    private @Nullable Callback callback;

    public MainWebView(Context context) {
        super(context);
        setup();
    }

    public MainWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void setup() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        setWebViewClient(new Client());
    }

    public interface Callback {
        void onLoadResource(String title);
    }

    private class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (UrlConst.PATTERN_URL_INSIDE.matcher(url).find()) {
                Mint.logEvent("go_page");
                return false;
            }
            Mint.logEvent("go_app");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getContext().startActivity(intent);
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if (callback != null) {
                callback.onLoadResource(getTitle());
            }
        }
    }
}
