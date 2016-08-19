package com.angluswang.newsclient.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.angluswang.newsclient.base.BasePager;
import com.angluswang.newsclient.bean.NewsData;
import com.angluswang.newsclient.global.GlobalContants;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by AnglusWang on 2016/8/19.
 * 主页
 */

public class NewsCenterPager extends BasePager {

    private NewsData mNewsData;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tvTitle.setText("新闻");// 修改标题
        setSlidingMenuEnable(true);

        TextView text = new TextView(mActivity);
        text.setText("新闻");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        // 向 FrameLayout 中动态添加布局
        flContent.addView(text);

        getDataFromService();
    }

    /**
     * 从服务器 获取数据
     */
    private void getDataFromService() {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, GlobalContants.CATEGORIES_URL,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Log.i("返回结果:", result);

                        parseData(result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(mActivity, msg,
                                Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
    }

    /**
     * 解析 服务器返回的 json 数据
     *
     * @param result
     */
    private void parseData(String result) {

        Gson gson = new Gson();
        mNewsData = gson.fromJson(result, NewsData.class);
        Log.i("解析结果:", mNewsData.toString());
    }
}
