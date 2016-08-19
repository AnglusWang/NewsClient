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

public class GovAffairsPager extends BasePager {

    public GovAffairsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tvTitle.setText("人口管理");// 修改标题

        TextView text = new TextView(mActivity);
        text.setText("政务");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        // 向 FrameLayout 中动态添加布局
        flContent.addView(text);
    }
}
