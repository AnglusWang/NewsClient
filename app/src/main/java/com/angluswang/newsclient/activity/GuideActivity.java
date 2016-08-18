package com.angluswang.newsclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.angluswang.newsclient.R;
import com.angluswang.newsclient.utils.PrefUtils;

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

    private Button btnStart; // 开始体验
    private LinearLayout llPointGroup;
    private View viewRedPoint;

    private int mPointWidth; // 圆点之间的距离

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

        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.setOnPageChangeListener(new GuideListener());

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

        // 获取视图树, 对layout结束事件进行监听
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    // 当layout执行结束后回调此方法
                    @Override
                    public void onGlobalLayout() {
                        llPointGroup.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        mPointWidth = llPointGroup.getChildAt(1).getLeft()
                                - llPointGroup.getChildAt(0).getLeft();
                    }
                });

        // 给开始体验按钮设置点击侦听
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 更新 sp 数据, 下次不再进入向导页
                PrefUtils.setBoolean(GuideActivity.this,
                        "is_user_guide_showed", true);

                startActivity(new Intent(GuideActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    /**
     * ViewPager 适配器
     */
    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return iv_List.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
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

    /**
     * ViewPager 滑动监听
     */
    private class GuideListener implements ViewPager.OnPageChangeListener {

        // 滑动事件
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int len = (int) (mPointWidth * positionOffset) + position * mPointWidth;
            // 获取当前红点的布局参数
            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
            params.leftMargin = len; // 设置左边距

            viewRedPoint.setLayoutParams(params); // 重新给小红点设置布局参数
        }

        // 某页面被选中
        @Override
        public void onPageSelected(int position) {

            // 开始体验 按钮处理
            if (position == iv_Ids.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        // 滑动状态发送改变
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
