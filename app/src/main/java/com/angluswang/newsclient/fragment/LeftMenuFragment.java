package com.angluswang.newsclient.fragment;

import android.view.View;

import com.angluswang.newsclient.R;

/**
 * Created by AnglusWang on 2016/8/18.
 * 左侧菜单栏
 */

public class LeftMenuFragment extends BaseFragment {
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        return view;
    }
}
