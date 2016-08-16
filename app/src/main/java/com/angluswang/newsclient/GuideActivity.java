package com.angluswang.newsclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by AnglusWang on 2016/8/15.
 * 向导页
 */

public class GuideActivity extends Activity {

    private static final int[] iv_Ids = new int[] { R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3 };

    private ViewPager vpGuide;
    private ArrayList<ImageView> iv_List;
    private GuideAdapter adapter;

    private Button btnStart;// 开始体验

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {

        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStart = (Button) findViewById(R.id.btn_start);

        // 初始化引导页的3个页面
        iv_List = new ArrayList<>();
        for (int iv_Id : iv_Ids) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(iv_Id); // 设置引导页背景
            iv_List.add(image);
        }

        adapter = new GuideAdapter();
        vpGuide.setAdapter(adapter);

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
