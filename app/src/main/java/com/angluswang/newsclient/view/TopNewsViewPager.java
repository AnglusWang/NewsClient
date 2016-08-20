package com.angluswang.newsclient.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by AnglusWang on 2016/8/20.
 * 为解决滑动冲突 自定义 ViewPager
 */

public class TopNewsViewPager extends ViewPager {

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 事件分发，请求父控件及祖宗控件 是否拦截
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
