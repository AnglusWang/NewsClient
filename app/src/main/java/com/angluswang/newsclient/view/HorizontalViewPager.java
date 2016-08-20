package com.angluswang.newsclient.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by AnglusWang on 2016/8/20.
 * 为解决滑动冲突 自定义 ViewPager
 */

public class HorizontalViewPager extends ViewPager {

    public HorizontalViewPager(Context context) {
        super(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 事件分发，请求父控件及祖宗控件 是否拦截
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        // 如果是第一个页面,需要显示侧边栏, 请求父控件拦截
        if (getCurrentItem() != 0) {// 不拦截
            getParent().requestDisallowInterceptTouchEvent(true);// 用getParent去请求,
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);// 拦截
        }

        return super.dispatchTouchEvent(ev);
    }
}
