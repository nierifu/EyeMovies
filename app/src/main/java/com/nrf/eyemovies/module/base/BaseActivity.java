package com.nrf.eyemovies.module.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nrf.eyemovies.common.utils.ToastUtils;

import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/9.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private boolean TOUCH_BACK;
    public Context context;
    public String tagName;
    private RelativeLayout container;
    private static LinkedList<AppCompatActivity> activities;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TOUCH_BACK = false;
        }
    };
    public Bundle saveInstanceState;
    private Unbinder mButterBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TOUCH_BACK = false;
        super.onCreate(savedInstanceState);
        context = this;
        if (activities == null) {
            activities = new LinkedList<>();
        }
        tagName = this.getClass().getSimpleName();
        activities.add(this);


        this.saveInstanceState=savedInstanceState;
        container = new RelativeLayout(context);
        container.addView(LayoutInflater.from(context).inflate(getLayoutId(), null, false), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(container, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mButterBind = ButterKnife.bind(this);
        initView();
        initData();
        bindListener();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }



    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);友盟

    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);友盟
    }


    /**
     * 设置布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始控件 做一些findviewbyId
     */
    protected abstract void initView();

    /**
     * 设置数据的显示
     */
    protected abstract void initData();

    /**
     * 设置点击事件
     */
    protected abstract void bindListener();



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (activities != null) {
            if (activities.size() == 1) {
                //防止误触返回键
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (!TOUCH_BACK) {
                        TOUCH_BACK = true;
                        ToastUtils.showShort(context, "再按一下退出");
                        handler.sendEmptyMessageDelayed(1, 2000);
                    } else {
                        exitApp();
                    }
                    return false;
                }
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activities != null) {
            activities.remove(this);
        }
        mButterBind.unbind();
    }

    /**
     * 彻底退出app
     */
    protected void exitApp() {
        for (AppCompatActivity baseActivity : activities) {
            baseActivity.finish();
        }
        activities.clear();
        activities = null;
    }


    /**
     * 网络请求线程切换
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T,T> setThread(){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }



}
