package com.angluswang.newsclient.utils.bitmap;

import android.widget.ImageView;

import com.angluswang.newsclient.R;

/**
 * Created by AnglusWang on 2016/8/29.
 * 自定义 图片加载工具类
 */
public class MyBitmapUtils {

    private NetCacheUtils mNetCacheUtils;

    public MyBitmapUtils() {
        mNetCacheUtils = new NetCacheUtils();
    }

    public void display(ImageView ivPic, String url) {

        ivPic.setImageResource(R.drawable.news_pic_default);
        // 从内存读取

        // 从SD卡读取

        // 从网络读取
        mNetCacheUtils.getBitmapFromNet(ivPic, url);
    }
}
