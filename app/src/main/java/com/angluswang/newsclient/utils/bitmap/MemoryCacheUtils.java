package com.angluswang.newsclient.utils.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by AnglusWang on 2016/8/30.
 * 内存缓存 工具类
 */
public class MemoryCacheUtils {

    public LruCache<String, Bitmap> mBitmapLruCache;

    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;// 模拟器默认是16M
        mBitmapLruCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getRowBytes() * value.getHeight();// 获取图片占用内存大小
                return byteCount;
            }
        };
    }

    public Bitmap getBitmapForMemory(String url) {
        return mBitmapLruCache.get(url);
    }

    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mBitmapLruCache.put(url, bitmap);
    }
}
