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
    private MemoryCacheUtils mMemoryCacheUtils;

    public MyBitmapUtils() {
        mLocalCacheUtils = new LocalCacheUtils();
        mMemoryCacheUtils = new MemoryCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
    }

    public void display(ImageView ivPic, String url) {

        ivPic.setImageResource(R.drawable.news_pic_default);// 设置默认加载图片

        Bitmap bitmap = null;
        // 从内存读取
        bitmap = mMemoryCacheUtils.getBitmapForMemory(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            Log.i("MyBitmapUtils : ", "从内存读取到图片啦...");
            return;
        }

        // 从(本地)SD卡读取
        bitmap =  mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            Log.i("MyBitmapUtils : ", "从本地读取到图片啦...");
            return;
        }

        // 从网络读取
        mNetCacheUtils.getBitmapFromNet(ivPic, url);
        Log.i("MyBitmapUtils : ", "从网络读取到图片啦...");
    }
}
