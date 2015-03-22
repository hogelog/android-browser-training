package org.hogel.naroubrowser.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.hogel.naroubrowser.BrowserApplication;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.consts.UrlConst;
import org.hogel.naroubrowser.db.dao.VisitedUrlDao;
import org.hogel.naroubrowser.services.AnalyticsService;

import javax.inject.Inject;

public class MainWebView extends WebView {

    public interface Callback {
        void onPageStarted();

        void onProgressChanged(int newProgress);

        void onPageFinished();

        void onScrollX(int X);

        void onScrollY(int y);
    }

    @Inject
    AnalyticsService analyticsService;

    @Inject
    VisitedUrlDao visitedUrlDao;

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

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (callback == null) {
            return;
        }
        if (l != oldl) {
            callback.onScrollX(l - oldl);
        }
        if (t != oldt) {
            callback.onScrollY(t - oldt);
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    private void setup(Context context) {
        BrowserApplication.component(context).inject(this);
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        setWebViewClient(new Client());
        setWebChromeClient(new ChromeClient());
    }

    private class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            analyticsService.trackViewUrl(url);

            if (UrlConst.PATTERN_URL_INSIDE.matcher(url).find()) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getContext().startActivity(intent);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (callback != null) {
                callback.onPageStarted();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (callback != null) {
                callback.onPageFinished();
            }

            if (!visitedUrlDao.isExist(url)) {
                visitedUrlDao.create(url, getTitle());
            }
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (callback != null) {
                callback.onProgressChanged(newProgress);
            }
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(getContext())
                .setTitle(url)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                })
                .create()
                .show();

            return true;
        }
    }
}
