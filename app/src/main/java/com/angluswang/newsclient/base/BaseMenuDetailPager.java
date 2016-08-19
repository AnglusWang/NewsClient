package com.angluswang.newsclient.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by AnglusWang on 2016/8/19.
 * 菜单详情页 基类
 */
public abstract class BaseMenuDetailPager {

    public Activity mActivity;

    public View mRootView;// 根布局对象

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initViews();
    }

    /**
     * 初始化界面
     */
    public abstract View initViews();

    /**
     * 初始化数据
     */
    public void initData() {

    }

}
