package com.angluswang.newsclient.utils.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.angluswang.newsclient.R;

/**
 * Created by AnglusWang on 2016/8/29.
 * 自定义 图片加载工具类
 */
public class MyBitmapUtils {

    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;

    public MyBitmapUtils() {
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils);
    }

    public void display(ImageView ivPic, String url) {

        ivPic.setImageResource(R.drawable.news_pic_default);// 设置默认加载图片

        Bitmap bitmap = null;
        // 从内存读取

        // 从(本地)SD卡读取
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            Log.i("MyBitmapUtils : ", "从本地读取图片啦...");
            return;
        }

        // 从网络读取
        mNetCacheUtils.getBitmapFromNet(ivPic, url);
    }
}
