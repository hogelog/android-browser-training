package org.hogel.naroubrowser.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.google.inject.Inject;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.consts.UrlConst;
import org.hogel.naroubrowser.db.dao.VisitedUrlDao;
import org.hogel.naroubrowser.views.MainWebView;
import roboguice.inject.InjectView;


public class MainActivity extends AbstractActivity {

    @Inject
    Resources resources;

    @Inject
    VisitedUrlDao visitedUrlDao;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.main_webview)
    MainWebView mainWebview;

    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @InjectView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private int scrolling = 0;

    private int toolbarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setup();

        if (savedInstanceState != null) {
            mainWebview.restoreState(savedInstanceState);
        } else {
            String url = getIntent().getDataString();
            if (TextUtils.isEmpty(url)) {
                mainWebview.loadUrl(UrlConst.URL_LAUNCH);
            } else {
                mainWebview.loadUrl(url);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mainWebview.loadUrl(intent.getDataString());
    }

    private void setup() {
        setSupportActionBar(toolbar);

        toolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height);

        mainWebview.setY(toolbarHeight);
        mainWebview.listenScrollY(y -> {
            scrolling += y;
            if (scrolling > toolbarHeight) {
                scrolling = toolbarHeight;
            } else if (scrolling < 0) {
                scrolling = 0;
            }

            resizeToolbar();
        }).listenProgress(progress -> {
            progressBar.setProgress(progress);
            if (progress == 0) {
                progressBar.setVisibility(View.VISIBLE);
            } else if (progress == 100) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }).listenVisitPage(visitPage -> {
            String url = visitPage.first;
            String title = visitPage.second;
            if (!visitedUrlDao.isExist(url)) {
                visitedUrlDao.create(url, title);
            }
            scrolling = 0;
            resizeToolbar();
        });

        swipeRefreshLayout.setOnRefreshListener(mainWebview::reload);
    }

    private void resizeToolbar() {
        toolbar.setTranslationY(-scrolling);
        progressBar.setTranslationY(-scrolling);

        if (scrolling == 0) {
            swipeRefreshLayout.setTranslationY(toolbarHeight);
            mainWebview.setTranslationY(0);
        } else {
            swipeRefreshLayout.setTranslationY(0);
            mainWebview.setTranslationY(toolbarHeight - scrolling);
        }
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

        if (id == R.id.action_bookmark) {
            mainWebview.loadUrl(UrlConst.URL_LAUNCH);
            return true;
        } else if (id == R.id.action_reload) {
            mainWebview.reload();
            return true;
        } else if (id == R.id.action_ranking) {
            mainWebview.loadUrl(UrlConst.URL_RANKING);
            return true;
        } else if (id == R.id.action_about) {
            startActivity(AboutActivity.createIntent(this));
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
