package com.angluswang.newsclient.utils;

/**
 * Created by AnglusWang on 2016/8/21.
 */

public class UrlUtils {

    /**
     * 将服务端的 模拟器地址改为 Genymotion 可用的地址
     * @param url
     * @return
     */
    public static String toGenymo(String url) {
        return url.replace("10.0.2.2", "10.0.3.2");
    }
}
