package com.angluswang.newsclient.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.base.BasePager;
import com.angluswang.newsclient.base.impl.GovAffairsPager;
import com.angluswang.newsclient.base.impl.HomePager;
import com.angluswang.newsclient.base.impl.NewsCenterPager;
import com.angluswang.newsclient.base.impl.SettingPager;
import com.angluswang.newsclient.base.impl.SmartServicePager;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/18.
 * 主页
 */

public class ContentFragment extends BaseFragment {

    private ViewPager vpPager;
    private RadioGroup rgGroup;

    private ArrayList<BasePager> mPagerList;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        vpPager = (ViewPager) view.findViewById(R.id.vp_pager);

        return view;
    }

    @Override
    public void initData() {
        rgGroup.check(R.id.rb_home);// 设置默认选择状态

        // 初始化5个子页面
        mPagerList = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            BasePager pager = new BasePager(mActivity);
//            mPagerList.add(pager);
//        }
        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new NewsCenterPager(mActivity));
        mPagerList.add(new SmartServicePager(mActivity));
        mPagerList.add(new GovAffairsPager(mActivity));
        mPagerList.add(new SettingPager(mActivity));

        vpPager.setAdapter(new ContentAdapter());

        // 监听RadioGroup的选择事件
        rgGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb_home:
                                // 设置当前页面 去掉切换页面的动画
                                vpPager.setCurrentItem(0, false);
                                break;
                            case R.id.rb_news:
                                vpPager.setCurrentItem(1, false);// 设置当前页面
                                break;
                            case R.id.rb_smart:
                                vpPager.setCurrentItem(2, false);// 设置当前页面
                                break;
                            case R.id.rb_gov:
                                vpPager.setCurrentItem(3, false);// 设置当前页面
                                break;
                            case R.id.rb_setting:
                                vpPager.setCurrentItem(4, false);// 设置当前页面
                                break;
                            default:
                                break;
                        }
                    }
                });

        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {
                mPagerList.get(position).initData();// 获取当前被选中的页面, 初始化该页面数据
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerList.get(0).initData();// 初始化首页数据
    }

    class ContentAdapter extends PagerAdapter {

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
            BasePager pager = mPagerList.get(position);
            container.addView(pager.mRootView);
            pager.initData();// 初始化数据
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 获取 新闻中心页面
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagerList.get(1);
    }
}
