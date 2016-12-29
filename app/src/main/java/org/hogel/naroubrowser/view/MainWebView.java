package org.hogel.naroubrowser.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Pair;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.constant.UrlConstant;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class MainWebView extends WebView {

    private final Subject<Integer, Integer> scrollXSubject = PublishSubject.create();

    private final Subject<Integer, Integer> scrollYSubject = PublishSubject.create();

    private final Subject<Integer, Integer> progressSubject = PublishSubject.create();

    private final Subject<Pair<String, String>, Pair<String, String>> visitPageSubject = PublishSubject.create();

    private boolean pageLoading = false;

    public MainWebView(Context context) {
        super(context);
        setup();
    }

    public MainWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (pageLoading) {
            return;
        }
        scrollXSubject.onNext(l - oldl);
        scrollYSubject.onNext(t - oldt);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setup() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        setWebViewClient(new Client());
        setWebChromeClient(new ChromeClient());
    }

    private class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final String url = getUrl();

            if (UrlConstant.PATTERN_URL_INSIDE.matcher(url).find()) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getContext().startActivity(intent);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressSubject.onNext(0);
            pageLoading = true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressSubject.onNext(100);
            visitPageSubject.onNext(Pair.create(url, getTitle()));
            pageLoading = false;
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressSubject.onNext(newProgress);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(getContext())
                .setTitle(url)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
                    result.confirm();
                })
                .setNegativeButton(R.string.dialog_cancel, (dialog, which) -> {
                    result.cancel();
                })
                .create()
                .show();

            return true;
        }
    }

    public MainWebView listenProgress(Action1<Integer> action) {
        progressSubject.subscribe(action);
        return this;
    }

    public MainWebView listenVisitPage(Action1<Pair<String, String>> action) {
        visitPageSubject.subscribe(action);
        return this;
    }
}
