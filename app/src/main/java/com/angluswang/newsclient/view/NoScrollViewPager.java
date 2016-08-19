package com.angluswang.newsclient.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by AnglusWang on 2016/8/19.
 * 不能左右滑的 ViewPager
 */

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        this(context, null);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 表示事件是否拦截, 返回false表示不拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 重写onTouchEvent事件,什么都不用做
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}