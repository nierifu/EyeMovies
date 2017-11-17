package com.nrf.eyemovies.common.http;

import com.nrf.eyemovies.common.http.config.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/11/9.
 * 接口管理类
 */

public class ApiManager {
    private ApiService apiService;
    private static ApiManager apiManager;

    public synchronized static ApiManager getInstance(){
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public ApiService getApiService(){
        if(apiService == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)//设计网络超时时间
                    .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                    .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                    .addInterceptor(InterceptorUtil.HeaderInterceptor())
                    .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HttpConfig.baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Rxjava retrofit结合起来
                    .addConverterFactory(GsonConverterFactory.create())       //添加gson解析库
                    .build();

            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }

    /**
     * 网络请求示例
     */
//    public void getData() {
//        ApiManager.getInstance().getApiService()
//                .getdata("我是中国人")//网络请求方法
//                .compose(this.<BaseEntity<ABean>>setThread())
//                .subscribe(new BaseObserver<ABean>() {
//                    @Override
//                    protected void onSuccees(BaseEntity<ABean> t) throws Exception {
//
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//                    }
//                });
//    }
}
