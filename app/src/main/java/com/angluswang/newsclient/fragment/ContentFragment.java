package com.angluswang.newsclient.fragment;

import android.view.View;
import android.widget.RadioGroup;

import com.angluswang.newsclient.R;

/**
 * Created by AnglusWang on 2016/8/18.
 * 主页
 */

public class ContentFragment extends BaseFragment {

    private RadioGroup rgGroup;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);

        return view;
    }

    @Override
    public void initData() {
        rgGroup.check(R.id.rb_home);// 设置默认选择状态
    }
}
