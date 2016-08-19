package com.angluswang.newsclient.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.fragment.ContentFragment;
import com.angluswang.newsclient.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by AnglusWang on 2016/8/17.
 * 主页 活动界面
 */

public class MainActivity extends SlidingFragmentActivity {

    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView() {

        setBehindContentView(R.layout.left_menu);// 设置侧边栏
        SlidingMenu slidingMenu = getSlidingMenu();// 获取侧边栏对象
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置全屏触摸
        slidingMenu.setBehindOffset(240);// 设置预留屏幕的宽度

        initFragment();
    }

    /**
     * 初始化 Fragment，将 Fragment 塞给布局文件
     */
    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();// 开启事务
        transaction.replace(R.id.fl_left_menu,
                new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
        transaction.replace(R.id.fl_content,
                new ContentFragment(), FRAGMENT_CONTENT);
        transaction.commit();
    }

    /**
     * 获取侧边栏 fragment
     */
    public LeftMenuFragment getLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LeftMenuFragment fragment =
                (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MENU);

        return fragment;
    }

    /**
     * 获取主页面的 Fragment
     */
    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ContentFragment fragment =
                (ContentFragment) fm.findFragmentByTag(FRAGMENT_CONTENT);

        return fragment;
    }

}
