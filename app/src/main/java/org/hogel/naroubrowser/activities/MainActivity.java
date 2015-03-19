package org.hogel.naroubrowser.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.InjectView;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.consts.UrlConst;
import org.hogel.naroubrowser.services.AnalyticsService;
import org.hogel.naroubrowser.views.MainWebView;

import javax.inject.Inject;


public class MainActivity extends AbstractActivity {

    @Inject
    AnalyticsService analyticsService;

    @InjectView(R.id.main_webview)
    MainWebView mainWebview;

    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component().inject(this);

        setContentView(R.layout.activity_main);

        setup();

        if (savedInstanceState == null) {
            mainWebview.loadUrl(UrlConst.URL_LAUNCH);
        } else {
            mainWebview.restoreState(savedInstanceState);
        }
    }

    private void setup() {
        mainWebview.setCallback(new MainWebView.Callback() {
            @Override
            public void onPageStarted() {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished() {
                progressBar.setProgress(100);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onProgressChanged(int progress) {
                progressBar.setProgress(progress);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainWebview.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_bookmark) {
            mainWebview.loadUrl(UrlConst.URL_LAUNCH);
            return true;
        } else if (id == R.id.action_reload) {
            mainWebview.reload();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mainWebview.canGoBack()) {
            mainWebview.goBack();
            return;
        }
        super.onBackPressed();
    }
}
