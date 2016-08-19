package com.angluswang.newsclient.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.angluswang.newsclient.R;

/**
 * Created by AnglusWang on 2016/8/19.
 * 主页面下 5个子页面的基类
 */

public class BasePager {

    public Activity mActivity;
    public View mRootView;// 布局对象

    public TextView tvTitle;// 标题对象
    public FrameLayout flContent;// 内容

    public BasePager(Activity activity) {
        mActivity = activity;

        initViews();
    }

    /**
     * 初始化 布局
     */
    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
    }

    /**
     * 初始化 数据
     */
    public void initData() {

    }
}
