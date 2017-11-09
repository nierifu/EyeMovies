package com.example.administrator.eyepetizer.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.eyepetizer.R;
import com.example.administrator.eyepetizer.ui.BaseFragment;

/**
 * Created by Administrator on 2017/11/9.
 */

public class CategoryFragment extends BaseFragment {
    @Override
    protected View getLayoutInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_category,null);
        return view;
    }

    @Override
    protected void initView(View layoutInflateView) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindListener() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("CategoryFragment",  "onHiddenChanged: hidden---" + hidden);
    }
}
