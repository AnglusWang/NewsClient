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
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

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

        mViewPager.setAdapter(new TopNewsAdapter());
    }

    private class TopNewsAdapter extends PagerAdapter {
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
