package com.angluswang.newsclient.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.activity.NewsDetailActivity;
import com.angluswang.newsclient.bean.NewsData;
import com.angluswang.newsclient.bean.TabData;
import com.angluswang.newsclient.global.GlobalContants;
import com.angluswang.newsclient.utils.CacheUtils;
import com.angluswang.newsclient.utils.PrefUtils;
import com.angluswang.newsclient.utils.UrlUtils;
import com.angluswang.newsclient.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/19.
 * 页签详情页
 */
public class TabDetailPager extends BaseMenuDetailPager
        implements ViewPager.OnPageChangeListener {

    private NewsData.NewsTabData mTabData;

    @ViewInject(R.id.vp_news)
    private ViewPager mViewPager;

    private final String mUrl;// 服务器网址
    private TabData mTabDetailData;

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;// 头条新闻的标题
    private ArrayList<TabData.TopNewsData> mTopNewsList;

    @ViewInject(R.id.indicator)
    private CirclePageIndicator mIndicator;// 头条新闻位置指示器

    @ViewInject(R.id.lv_list)
    private RefreshListView lvList;// 新闻列表
    private ArrayList<TabData.TabNewsData> mNewsList; // 新闻数据集合
    private NewsAdapter newsAdapter;

    private String mMoreUrl;// 下一页链接

    public TabDetailPager(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;
        mUrl = GlobalContants.SERVER_URL + mTabData.url;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        View headerView = View.inflate(mActivity, R.layout.list_header_topnews, null);

        ViewUtils.inject(this, view);
        ViewUtils.inject(this, headerView);

        // 将头条新闻以头布局的形式加给 listView
        lvList.addHeaderView(headerView);

        // 设置下拉刷新监听
        lvList.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                if (mMoreUrl != null) {
                    getMoreDataFromServer();
                } else {
                    Toast.makeText(mActivity, "最后一页了",
                            Toast.LENGTH_SHORT).show();
                    lvList.onRefreshComplete(false); // 收起加载更多的布局
                }
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                // 在本地记录已读状态
                String ids = PrefUtils.getString(mActivity, "read_ids", "");
                String readId = mNewsList.get(i).id;
                if (!ids.contains(readId)) {
                    ids = ids + readId + ",";
                    PrefUtils.setString(mActivity, "read_ids", ids);
                }

                changeReadState(view);// 局部改变 View 的阅读状态

                // 跳转至新闻详情页
                Intent intent = new Intent();
                intent.setClass(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", mNewsList.get(i).url);
                mActivity.startActivity(intent);
            }
        });

        return view;
    }

    // 改变 View 的阅读状态
    private void changeReadState(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            parseData(cache, false);
        }

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
                        parseData(result, false);// 解析数据

                        lvList.onRefreshComplete(true);

                        // 设置缓存
                        CacheUtils.setCache(mUrl, result, mActivity);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(mActivity, msg,
                                Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                        lvList.onRefreshComplete(false);
                    }
                });
    }

    // 获取更多数据
    private void getMoreDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mMoreUrl,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        parseData(result, true);

                        lvList.onRefreshComplete(true);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(mActivity, msg,
                                Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                        lvList.onRefreshComplete(false);
                    }
                });
    }

    // 解析 获取的数据
    private void parseData(String result, boolean isMore) {
        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, TabData.class);
        Log.i("页签详情解析:", mTabDetailData.toString());

        // 处理下一页链接
        String more = mTabDetailData.data.more;
        if (!TextUtils.isEmpty(more)) {
            mMoreUrl = GlobalContants.SERVER_URL + more;
        } else {
            mMoreUrl = null;
        }

        if (!isMore) {
            mTopNewsList = mTabDetailData.data.topnews;
            mNewsList = mTabDetailData.data.news;

            if (mTopNewsList != null) {
                mViewPager.setAdapter(new TopNewsAdapter());

                mIndicator.setViewPager(mViewPager);
                mIndicator.setOnPageChangeListener(this);
                mIndicator.setSnap(true);// 支持快照显示
                mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点

                tvTitle.setText(mTopNewsList.get(0).title);
            }

            if (mNewsList != null) {
                newsAdapter = new NewsAdapter();
                lvList.setAdapter(newsAdapter);

            }
        } else {// 如果是加载下一页,需要将数据追加给原来的集合
            ArrayList<TabData.TabNewsData> news = mTabDetailData.data.news;
            mNewsList.addAll(news);
            newsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 头条新闻 适配器
     */
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

    @Override
    public void onPageScrolled(int position,
                               float positionOffset,
                               int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabData.TopNewsData topNewsData = mTopNewsList.get(position);
        tvTitle.setText(topNewsData.title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 新闻 ListView 适配器
     */
    private class NewsAdapter extends BaseAdapter {

        private BitmapUtils utils;

        public NewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public TabData.TabNewsData getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_news_item, null);
                holder = new ViewHolder();

                holder.imgPic = (ImageView) convertView.findViewById(R.id.img_pic);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            TabData.TabNewsData item = getItem(position);

            holder.tvTitle.setText(item.title);
            holder.tvDate.setText(item.pubdate);
            utils.display(holder.imgPic, item.listimage);

            String ids = PrefUtils.getString(mActivity, "read_ids", "");
            if (ids.contains(getItem(position).id)) {
                holder.tvTitle.setTextColor(Color.GRAY);
            } else {
                holder.tvTitle.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView imgPic;
    }
}
