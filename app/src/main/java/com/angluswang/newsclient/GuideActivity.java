package com.angluswang.newsclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/15.
 * 向导页
 */

public class GuideActivity extends Activity {

    private static final int[] iv_Ids = new int[]{R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3};

    private ViewPager vpGuide;
    private ArrayList<ImageView> iv_List;
    private GuideAdapter adapter;

    private Button btnStart;// 开始体验
    private LinearLayout llPointGroup;
    private View viewRedPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {

        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStart = (Button) findViewById(R.id.btn_start);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint = (View) findViewById(R.id.view_red_point);

        // 初始化引导页的3个页面
        iv_List = new ArrayList<>();
        for (int iv_Id : iv_Ids) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(iv_Id); // 设置引导页背景
            iv_List.add(image);
        }

        adapter = new GuideAdapter();
        vpGuide.setAdapter(adapter);

        // 初始化引导页的小圆点
        for (int i = 0; i < iv_Ids.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray); // 设置引导页默认圆点

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(10, 10);
            if (i > 0) {
                params.leftMargin = 10; // 设置圆点间隔
            }
            point.setLayoutParams(params); // 设置圆点的大小

            llPointGroup.addView(point); // 将圆点添加给线性布局
        }

    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return iv_List.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(iv_List.get(position));
            return iv_List.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
