package com.nrf.eyemovies.module.follow.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nrf.eyemovies.R;
import com.nrf.eyemovies.module.base.BaseFragment;

/**
 * Created by Administrator on 2017/11/9.
 */

public class FollowFragment extends BaseFragment {
    @Override
    protected View getLayoutInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_follow,null);
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
        Log.e("FollowFragment", "onHiddenChanged: hidden---" + hidden);
    }
}
