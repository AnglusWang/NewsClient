package com.angluswang.newsclient.activity;

import android.app.Activity;
import android.os.Bundle;

import com.angluswang.newsclient.R;

/**
 * Created by AnglusWang on 2016/8/17.
 * 主页 活动界面
 */

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView() {
    }
}
