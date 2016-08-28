package com.angluswang.newsclient.global;

/**
 * Created by AnglusWang on 2016/8/19.
 * 定义全局参数
 */

public class GlobalContants {

    // 使用 Genymotion 模拟器时，为 10.0.3.2，其他模拟器 10.0.2.2
    public static final String SERVER_URL = "http://10.0.3.2:8080/zhbj";
    public static final String CATEGORIES_URL =
            SERVER_URL + "/categories.json"; // 获取分类信息的接口
    public static final String PHOTOS_URL = SERVER_URL
            + "/photos/photos_1.json";// 获取组图信息的接口
}
