package com.angluswang.newsclient.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.activity.MainActivity;
import com.angluswang.newsclient.base.impl.NewsCenterPager;
import com.angluswang.newsclient.bean.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/18.
 * 左侧菜单栏
 */

public class LeftMenuFragment extends BaseFragment {

    private ListView lvMenu;
    private ArrayList<NewsData.NewsMenuData> mMenuList;
    private MenuAdapter mAdapter;// 菜单适配器

    private int mCurrentPos;// 当前被点击的菜单项

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        lvMenu = (ListView) view.findViewById(R.id.lv_list);

        return view;
    }

    @Override
    public void initData() {
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                mCurrentPos = i;
                mAdapter.notifyDataSetChanged();
                setCurrentMenuDetailPager(i);

                toggleSlidingMenu();// 隐藏
            }
        });
    }

    /**
     * 切换 SlidingMenu 状态
     */
    protected void toggleSlidingMenu() {
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();// 切换状态, 显示时隐藏, 隐藏时显示
    }

    /**
     * 设置 当前菜单详情页
     */
    protected void setCurrentMenuDetailPager(int position) {
        MainActivity mainUi = (MainActivity) mActivity;
        ContentFragment fragment = mainUi.getContentFragment();// 获取主页面fragment
        NewsCenterPager pager = fragment.getNewsCenterPager();// 获取新闻中心页面
        pager.setCurrentMenuDetailPager(position);// 设置当前菜单详情页
    }

    /**
     * 设置网络数据
     */
    public void setMenuData(NewsData data) {
        Log.i("侧边栏拿到数据啦:", data.toString());

        mMenuList = data.data;
        mAdapter = new MenuAdapter();
        lvMenu.setAdapter(mAdapter);
    }

    // 左边菜单 ListView 适配器
    private class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public NewsData.NewsMenuData getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(mActivity, R.layout.list_menu_item, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            NewsData.NewsMenuData newsMenuData = getItem(position);
            tvTitle.setText(newsMenuData.title);

            return view;
        }

    }

}
