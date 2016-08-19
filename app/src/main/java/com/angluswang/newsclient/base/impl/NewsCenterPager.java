package com.angluswang.newsclient.base.impl;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.angluswang.newsclient.activity.MainActivity;
import com.angluswang.newsclient.base.BaseMenuDetailPager;
import com.angluswang.newsclient.base.BasePager;
import com.angluswang.newsclient.base.menudetail.InteractMenuDetailPager;
import com.angluswang.newsclient.base.menudetail.NewsMenuDetailPager;
import com.angluswang.newsclient.base.menudetail.PhotoMenuDetailPager;
import com.angluswang.newsclient.base.menudetail.TopicMenuDetailPager;
import com.angluswang.newsclient.bean.NewsData;
import com.angluswang.newsclient.fragment.LeftMenuFragment;
import com.angluswang.newsclient.global.GlobalContants;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/19.
 * 主页
 */

public class NewsCenterPager extends BasePager {

    private NewsData mNewsData;
    private ArrayList<BaseMenuDetailPager> mPagers;// 4个菜单详情页的集合

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tvTitle.setText("新闻");// 修改标题
        setSlidingMenuEnable(true);

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

        // 刷新测边栏的数据
        MainActivity mainUi = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
        leftMenuFragment.setMenuData(mNewsData);

        // 准备4个菜单详情页
        mPagers = new ArrayList<>();
        mPagers.add(new NewsMenuDetailPager(mActivity,
                mNewsData.data.get(0).children));
        mPagers.add(new TopicMenuDetailPager(mActivity));
        mPagers.add(new PhotoMenuDetailPager(mActivity));
        mPagers.add(new InteractMenuDetailPager(mActivity));

        setCurrentMenuDetailPager(0);// 设置菜单详情页-新闻为默认当前页
    }

    /**
     * 设置当前菜单详情页
     */
    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager pager = mPagers.get(position);// 获取当前要显示的菜单详情页
        flContent.removeAllViews();// 清除之前的布局
        flContent.addView(pager.mRootView);// 将菜单详情页的布局设置给帧布局

        // 设置当前页的标题
        NewsData.NewsMenuData menuData = mNewsData.data.get(position);
        tvTitle.setText(menuData.title);

        pager.initData();// 初始化当前页面的数据
    }
}
