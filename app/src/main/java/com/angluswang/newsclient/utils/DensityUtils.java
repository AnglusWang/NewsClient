package com.angluswang.newsclient.utils;

import android.content.Context;

/**
 * Created by AnglusWang on 2016/9/2.
 * px dp 转换工具类
 */
public class DensityUtils {

    public static int dp2px(Context ctx, float dp) {
        float density = ctx.getResources().getDisplayMetrics().density;// 屏幕密度
        int px = (int) (dp * density + 0.5f);// +0.5f --> 四舍五入

        return px;
    }

    public static float px2dp(Context ctx, int px) {
        float density = ctx.getResources().getDisplayMetrics().density;
        float dp = px / density;

        return dp;
    }
}
