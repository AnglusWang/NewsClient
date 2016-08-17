package com.angluswang.newsclient.activity.personcenter;

import android.os.Bundle;

import com.angluswang.newsclient.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by AnglusWang on 2016/8/17.
 * 主页 活动界面
 */

public class HomeActivity extends SlidingFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView() {
    }
}
