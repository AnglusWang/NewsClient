package com.angluswang.newsclient.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.activity.HomeActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by AnglusWang on 2016/8/19.
 * 主页面下 5个子页面的基类
 */

public class BasePager {

    public Activity mActivity;
    public View mRootView;// 布局对象

    public TextView tvTitle;// 标题对象
    public FrameLayout flContent;// 内容
    public ImageButton btnMenu;// 菜单按钮

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

        btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu(); // 切换 侧边栏的显隐状态
            }
        });
    }

    /**
     * 初始化 数据
     */
    public void initData() {

    }

    /**
     * 切换 SlidingMenu 的状态
     */
    public void toggleSlidingMenu() {
        HomeActivity mainUi = (HomeActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();// 切换状态, 显示时隐藏, 隐藏时显示
    }

    /**
     * 设置侧边栏 开启或关闭
     */
    public void setSlidingMenuEnable(boolean enable) {

        HomeActivity mainUi = (HomeActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();

        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 开启
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);// 关闭
        }
    }
}
