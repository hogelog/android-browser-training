package org.hogel.browser.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Pattern;

public class MainWebView extends WebView {
    private static final String DEFAULT_URL = "http://cookpad.com/";
    private static final Pattern PATTERN_URL_INSIDE = Pattern.compile("^https?://cookpad.com(?:$|/.*)");

    public MainWebView(Context context) {
        super(context);
        setup();
    }

    public MainWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        setWebViewClient(new Client());

        loadUrl(DEFAULT_URL);
    }

    private class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (PATTERN_URL_INSIDE.matcher(url).find()) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getContext().startActivity(intent);
            return true;
        }
    }
}
