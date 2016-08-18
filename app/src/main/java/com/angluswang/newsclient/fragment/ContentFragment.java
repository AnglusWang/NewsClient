package com.angluswang.newsclient.fragment;

import android.view.View;

import com.angluswang.newsclient.R;

/**
 * Created by AnglusWang on 2016/8/18.
 * 主页
 */

public class ContentFragment extends BaseFragment {
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        return view;
    }
}
