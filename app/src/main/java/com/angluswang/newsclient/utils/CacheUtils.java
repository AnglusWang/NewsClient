package com.angluswang.newsclient.utils;

import android.content.Context;

/**
 * Created by AnglusWang on 2016/8/24.
 * 缓存工具类
 */

public class CacheUtils {
    /**
     * 设置缓存 key 是url, value是json
     */
    public static void setCache(String key, String value, Context context) {
        PrefUtils.setString(context, key, value);
        //可以将缓存放在文件中, 文件名就是Md5(url), 文件内容是json
    }

    /**
     * 获取缓存 key 是url
     */
    public static String getCache(String key, Context context) {
        return PrefUtils.getString(context, key, null);
    }
}
