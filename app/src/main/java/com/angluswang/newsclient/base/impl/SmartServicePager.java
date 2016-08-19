package com.angluswang.newsclient.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.angluswang.newsclient.base.BasePager;

/**
 * Created by AnglusWang on 2016/8/19.
 * 主页
 */

public class SmartServicePager extends BasePager {

    public SmartServicePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tvTitle.setText("生活");// 修改标题

        TextView text = new TextView(mActivity);
        text.setText("智慧服务");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        // 向 FrameLayout 中动态添加布局
        flContent.addView(text);
    }
}
