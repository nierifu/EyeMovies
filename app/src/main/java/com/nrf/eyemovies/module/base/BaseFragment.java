package com.nrf.eyemovies.module.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nrf.eyemovies.R;
import com.nrf.eyemovies.utils.LOG;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/3.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected String tagName;
    private Unbinder mButterBind;

   public View emptyView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tagName = this.getClass().getSimpleName();
        View layoutInflateView = getLayoutInflateView(inflater, container, savedInstanceState);
        mButterBind = ButterKnife.bind(this, layoutInflateView);
        emptyView = View.inflate(getActivity(), R.layout.layout_no_data_layout, null);
        initView(layoutInflateView);
        initData();
        bindListener();

        return layoutInflateView;
    }




    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    /**
     * 初始化fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View getLayoutInflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 用于findviewbyID
     *
     * @param layoutInflateView 当前加载的view
     */
    protected abstract void initView(View layoutInflateView);

    /**
     * 绑定数据
     */
    protected abstract void initData();

    /**
     * 绑定点击事件
     */
    protected abstract void bindListener();

//    @Override
    @Override
    public void onPause() {
        LOG.e("--------------------------onPause----------"+this.getClass().getSimpleName());
        super.onPause();
    }
//
//    @Override
    @Override
    public void onResume() {
        LOG.e("--------------------------onResume----------"+this.getClass().getSimpleName());
        super.onResume();
    }

    @Override
    public void onDestroy() {

        if (mButterBind != null) {
            mButterBind.unbind();
        }
        super.onDestroy();
    }
}
