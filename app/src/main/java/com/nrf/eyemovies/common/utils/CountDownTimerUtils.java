package com.nrf.eyemovies.common.utils;

import android.os.CountDownTimer;

/**
 * Created by MIN on 16/5/9.
 * 发送验证码按钮倒计时
 */
public class CountDownTimerUtils extends CountDownTimer {

    private CountDownTimerCallback mTimerCallback;
    private long countDownInterval;
    /**
     * 倒计时工具类
     *
     * @param millisInFuture    总的倒计时时间
     * @param countDownInterval 每次倒退的单位
     * @param mTimerCallback    回调
     */
    public CountDownTimerUtils(long millisInFuture, long countDownInterval, CountDownTimerCallback mTimerCallback) {
        super(millisInFuture, countDownInterval);
        this.mTimerCallback = mTimerCallback;
        this.countDownInterval = countDownInterval;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mTimerCallback == null) {
            return;
        }
        mTimerCallback.onTick(millisUntilFinished/countDownInterval);

        //   mCode.setTextColor(mContext.getResources().getColor(R.color.loaderror_bg));
    }

    @Override
    public void onFinish() {
        if (mTimerCallback == null) {
            return;
        }
        mTimerCallback.onFinish();
    }

    /**
     * 倒计时回调
     */
    public interface CountDownTimerCallback {
        /**
         * 倒计时进行中的回调
         *
         * @param millsUntilFInished 距离结束的时间
         */
        void onTick(long millsUntilFInished);

        /**
         * 倒计时结束回调
         */
        void onFinish();
    }
}
