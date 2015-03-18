package org.hogel.naroubrowser.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.hogel.naroubrowser.BrowserApplication;
import org.hogel.naroubrowser.consts.UrlConst;
import org.hogel.naroubrowser.utils.AnalyticsUtils;

import javax.inject.Inject;

public class MainWebView extends WebView {

    @Inject
    AnalyticsUtils analyticsUtils;

    private @Nullable Callback callback;

    public MainWebView(Context context) {
        super(context);
        setup(context);
    }

    public MainWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void setup(Context context) {
        BrowserApplication.component(context).inject(this);
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
            analyticsUtils.trackViewUrl(url);

            if (UrlConst.PATTERN_URL_INSIDE.matcher(url).find()) {
                return false;
            }
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
