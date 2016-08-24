package com.angluswang.newsclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.angluswang.newsclient.R;

/**
 * Created by AnglusWang on 2016/8/23.
 * 新闻详情页
 */

public class NewsDetailActivity extends Activity
        implements View.OnClickListener {

    private WebView mWebView;
    private ImageButton btnBack;
    private ImageButton btnSize;
    private ImageButton btnShare;
    private ProgressBar pbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);

        initViews();
    }

    private void initViews() {
        mWebView = (WebView) findViewById(R.id.wv_web);
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnSize = (ImageButton) findViewById(R.id.btn_size);
        btnShare = (ImageButton) findViewById(R.id.btn_share);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);

        btnBack.setOnClickListener(this);
        btnSize.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        String url = getIntent().getStringExtra("url");

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);// 表示支持js
        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击缩放

        mWebView.setWebViewClient(new WebViewClient() {

            // 网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("onPageStarted: ", "网页开始加载。。。");

                pbProgress.setVisibility(View.VISIBLE);
            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("onPageFinished: ", "网页加载结束。。。");

                pbProgress.setVisibility(View.GONE);
            }

            // 所有跳转的链接都会在此方法中回调
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request) {
                Log.i("urlLoading: ", "跳转至---" + request);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                Log.i("onProgressChanged: ", newProgress + "");
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                Log.i("onReceivedTitle: ", title);
            }
        });

        mWebView.loadUrl(url);
    }

    // 设置按钮点击事件处理
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_size:
                showChooseDialog();// 显示选择对话框
                break;
            case R.id.btn_share:
                break;
            default:
                break;
        }
    }

    private int mCurrentChooseItem;// 记录当前选中的item, 点击确定前
    private int mCurrentItem = 2;// 记录当前选中的item, 点击确定后

    /**
     * 显示字体选择 对话框
     */
    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String[] items = new String[]{"超大号字体", "大号字体", "正常字体",
                "小号字体", "超小号字体"};
        builder.setTitle("字体设置");

        builder.setSingleChoiceItems(items, mCurrentItem,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCurrentChooseItem = which;
                    }
                });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebSettings settings = mWebView.getSettings();
                switch (mCurrentChooseItem) {
                    case 0:
                        settings.setTextZoom(200);
                        break;
                    case 1:
                        settings.setTextZoom(150);
                        break;
                    case 2:
                        settings.setTextZoom(100);
                        break;
                    case 3:
                        settings.setTextZoom(75);
                        break;
                    case 4:
                        settings.setTextZoom(50);
                        break;

                    default:
                        break;
                }
                mCurrentItem = mCurrentChooseItem;
            }
        });

        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
