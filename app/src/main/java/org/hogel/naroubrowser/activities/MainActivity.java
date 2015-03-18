package org.hogel.naroubrowser.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.InjectView;
import com.google.android.gms.analytics.Tracker;
import org.hogel.naroubrowser.BrowserApplication;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.consts.UrlConst;
import org.hogel.naroubrowser.views.MainWebView;


public class MainActivity extends AbstractActivity {
    @InjectView(R.id.main_webview)
    MainWebView mainWebview;

    Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        if (savedInstanceState == null) {
            mainWebview.loadUrl(UrlConst.URL_LAUNCH);
        } else {
            mainWebview.restoreState(savedInstanceState);
        }
    }

    private void setup() {
        tracker = ((BrowserApplication) getApplication()).getTracker();

        mainWebview.setCallback(new MainWebView.Callback() {
            @Override
            public void onLoadResource(String title) {
                setTitle(title);
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
