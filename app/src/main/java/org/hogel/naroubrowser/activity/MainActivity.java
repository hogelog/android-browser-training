package org.hogel.naroubrowser.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;
import butterknife.BindView;
import com.google.inject.Inject;
import org.hogel.naroubrowser.R;
import org.hogel.naroubrowser.constant.UrlConstant;
import org.hogel.naroubrowser.database.dao.VisitedUrlDao;
import org.hogel.naroubrowser.service.AnalyticsService;
import org.hogel.naroubrowser.view.MainWebView;


public class MainActivity extends AbstractActivity {

    @Inject
    Resources resources;

    @Inject
    VisitedUrlDao visitedUrlDao;

    @Inject
    AnalyticsService analyticsService;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_webview)
    MainWebView mainWebview;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.swipe_layout)
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
                mainWebview.loadUrl(UrlConstant.URL_LAUNCH);
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
        setActionBar(toolbar);

        toolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height);

        mainWebview.setY(toolbarHeight);
        mainWebview
            .listenScrollY(this::onListenScrollY)
            .listenProgress(this::onListenProgress)
            .listenVisitPage(this::onListenVisitPage);

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
            analyticsService.trackMainMenu("bookmark");
            mainWebview.loadUrl(UrlConstant.URL_LAUNCH);
            return true;
        } else if (id == R.id.action_reload) {
            analyticsService.trackMainMenu("reload");
            mainWebview.reload();
            return true;
        } else if (id == R.id.action_ranking) {
            analyticsService.trackMainMenu("ranking");
            mainWebview.loadUrl(UrlConstant.URL_RANKING);
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

    private void onListenScrollY(int y) {
        scrolling += y;
        if (scrolling > toolbarHeight) {
            scrolling = toolbarHeight;
        } else if (scrolling < 0) {
            scrolling = 0;
        }

        resizeToolbar();
    }

    private void onListenProgress(int progress) {
        progressBar.setProgress(progress);
        if (progress == 0) {
            progressBar.setVisibility(View.VISIBLE);
        } else if (progress == 100) {
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void onListenVisitPage(Pair<String, String> visitPage) {
        String url = visitPage.first;
        String title = visitPage.second;
        if (!visitedUrlDao.isExist(url)) {
            visitedUrlDao.create(url, title);
        }
        scrolling = 0;
        resizeToolbar();
    }
}
