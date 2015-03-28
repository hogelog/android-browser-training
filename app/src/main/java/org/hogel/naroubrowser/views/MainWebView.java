package org.hogel.naroubrowser.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import javax.inject.Inject;

public class MainWebView extends WebView {

    private final Subject<Integer, Integer> scrollXSubject = PublishSubject.create();

    private final Subject<Integer, Integer> scrollYSubject = PublishSubject.create();

    private final Subject<Integer, Integer> pageSubject = PublishSubject.create();

    @Inject
    AnalyticsService analyticsService;

    @Inject
    VisitedUrlDao visitedUrlDao;

    private boolean disableJavascript = false;

    public MainWebView(Context context) {
        super(context);
        setup(context);
    }

    public MainWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollXSubject.onNext(l - oldl);
        scrollYSubject.onNext(t - oldt);
    }

    private void setup(Context context) {
        BrowserApplication.component(context).inject(this);
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
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
            pageSubject.onNext(0);
            if (UrlConst.PATTERN_URL_DISABLE_JS.matcher(url).find()) {
                getSettings().setJavaScriptEnabled(false);
                disableJavascript = true;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pageSubject.onNext(100);

            if (!visitedUrlDao.isExist(url)) {
                visitedUrlDao.create(url, getTitle());
            }
            if (disableJavascript) {
                getSettings().setJavaScriptEnabled(true);
                disableJavascript = false;
            }
        }
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            pageSubject.onNext(newProgress);
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

    public void listenScrollX(Action1<Integer> action) {
        scrollXSubject.subscribe(action);
    }

    public void listenScrollY(Action1<Integer> action) {
        scrollYSubject.subscribe(action);
    }

    public void listenPage(Action1<Integer> action) {
        pageSubject.subscribe(action);
    }
}
