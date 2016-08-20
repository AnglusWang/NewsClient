package com.angluswang.newsclient.bean;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/20.
 * 获取新闻 服务器返回数据 json 解析实体类
 */

public class TabData {

    public int retcode;

    public TabDetail data;

    public class TabDetail {
        public String title;
        public String more;
        public ArrayList<TabNewsData> news;
        public ArrayList<TopNewsData> topnews;

        @Override
        public String toString() {
            return "TabDetail [title=" + title + ", news=" + news
                    + ", topnews=" + topnews + "]";
        }
    }

    /**
     * 新闻列表对象
     */
    public class TabNewsData {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TabNewsData [title=" + title + "]";
        }
    }

    /**
     * 头条新闻
     */
    public class TopNewsData {
        public String id;
        public String topimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TopNewsData [title=" + title + "]";
        }
    }

    @Override
    public String toString() {
        return "TabData [data=" + data + "]";
    }
}
