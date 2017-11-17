package com.nrf.eyemovies.common;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;




/**
 * Created by Administrator on 2017/7/28.
 */


public class BaseApplication extends MultiDexApplication {
    private static BaseApplication instance;
    private static final String db_name = "search_history";

    //    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化网络加载框架
//        NetManager.getInstance().initNetFramework(new OkHttpFramework(new OKHttpPullRequest()), this);

        initImConfig();
        // 初始化
        initUM();
        IMinit();
    }

    public void initImConfig() {
    }

    private void IMinit() {

    }

    private void initUM() {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



    public static BaseApplication getInstance() {
        return instance;
    }

    public static boolean isShowLive = false;//测试情况下
}
