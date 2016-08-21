package com.angluswang.newsclient.base;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.bean.NewsData;
import com.angluswang.newsclient.bean.TabData;
import com.angluswang.newsclient.global.GlobalContants;
import com.angluswang.newsclient.utils.UrlUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/19.
 * 页签详情页
 */
public class TabDetailPager extends BaseMenuDetailPager {

    private NewsData.NewsTabData mTabData;
    private TextView tvText;

    @ViewInject(R.id.vp_news)
    private ViewPager mViewPager;

    private final String mUrl;// 服务器网址
    private TabData mTabDetailData;

    private ArrayList<TabData.TopNewsData> mTopNewsList;

    public TabDetailPager(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;
        mUrl = GlobalContants.SERVER_URL + mTabData.url;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);

        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    // 从服务器 获取数据
    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Log.i("页签详情页返回结果:", result);

                        parseData(result);// 解析数据
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(mActivity, msg,
                                Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
    }

    // 解析 获取的数据
    private void parseData(String result) {
        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, TabData.class);
        Log.i("页签详情解析:", mTabDetailData.toString());

        mTopNewsList = mTabDetailData.data.topnews;
        // 使用 Genymotion 模拟器更换地址
        for (TabData.TopNewsData data : mTopNewsList) {
            data.topimage.replace("10.0.2.2", "10.0.3.2");
        }

        mViewPager.setAdapter(new TopNewsAdapter());
    }

    private class TopNewsAdapter extends PagerAdapter {

        private BitmapUtils utils;

        public TopNewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadFailedImage(R.drawable.topnews_item_default);
        }

        @Override
        public int getCount() {
            return mTabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView img = new ImageView(mActivity);
            img.setImageResource(R.drawable.topnews_item_default);
            img.setScaleType(ImageView.ScaleType.FIT_XY);// 基于控件大小填充图片

            TabData.TopNewsData topNewsData = mTopNewsList.get(position);
            // 传递 ImageView 对象和图片地址
            utils.display(img, UrlUtils.toGenymo(topNewsData.topimage));

            container.addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container,
                                int position, Object object) {
            container.removeView((View) object);
        }
    }
}
