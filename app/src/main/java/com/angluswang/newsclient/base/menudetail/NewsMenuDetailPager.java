package com.angluswang.newsclient.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.base.BaseMenuDetailPager;
import com.angluswang.newsclient.base.TabDetailPager;
import com.angluswang.newsclient.bean.NewsData;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/19.
 * 菜单详情页-新闻
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ViewPager mViewPager;

    private ArrayList<TabDetailPager> mPagerList;

    private ArrayList<NewsData.NewsTabData> mNewsTabData;// 页签网络数据
    private TabPageIndicator mIndicator;

    public NewsMenuDetailPager(Activity activity,
                               ArrayList<NewsData.NewsTabData> children) {
        super(activity);

        mNewsTabData = children;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        return view;
    }

    @Override
    public void initData() {
        mPagerList = new ArrayList<>();

        // 初始化页签数据
        for (int i = 0; i < mNewsTabData.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, mNewsTabData.get(i));
            mPagerList.add(pager);
        }

        mViewPager.setAdapter(new MenuDetailAdapter());
        mIndicator.setViewPager(mViewPager);// 要在 Viewpager 设置适配器后设置
    }

    private class MenuDetailAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabData.get(position).title;// 获取标题
        }

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
