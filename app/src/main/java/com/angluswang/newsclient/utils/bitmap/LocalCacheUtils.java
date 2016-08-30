package com.angluswang.newsclient.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.angluswang.newsclient.utils.MD5Encode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by AnglusWang on 2016/8/30.
 * 本地缓存 工具类
 */
public class LocalCacheUtils {

    public final static String CACHE_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/cache_news_client";

    /**
     * 从本地存储 获取图片
     *
     * @param url
     * @return
     */
    public Bitmap getBitmapFromLocal(String url) {

        try {
            String fileName = MD5Encode.encode(url);
            File file = new File(url, fileName);

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将图片 存储到本地
     *
     * @param url
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        try {
            String fileName = MD5Encode.encode(url);
            File file = new File(CACHE_PATH, fileName);

            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {// 如果文件不存在，创建文件夹
                parentFile.mkdirs();
            }

            // 将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
