package com.nrf.eyemovies.common.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nrf.eyemovies.R;


public class AnimDrawableAlertDialog extends AlertDialog {

    private ImageView progressImg;
    private TextView hintTextView;
    //帧动画
    private AnimationDrawable animation;
    private static AnimDrawableAlertDialog customProgressDialog = null;

    public AnimDrawableAlertDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_drawable_dialog_layout);

        //点击imageview外侧区域，动画不会消失
        setCanceledOnTouchOutside(false);

        progressImg = (ImageView) findViewById(R.id.refreshing_drawable_img);
        hintTextView = (TextView) findViewById(R.id.tv_refreshing_drawable_txt);
        //加载动画资源
        animation = (AnimationDrawable) progressImg.getDrawable();
    }

    public void setHintText(String hintText) {
        if (hintTextView != null) {
            hintTextView.setVisibility(View.VISIBLE);
            hintTextView.setText(hintText);
        }
    }

    /**
     * 在AlertDialog的 onStart() 生命周期里面执行开始动画
     */
    @Override
    protected void onStart() {
        super.onStart();
        animation.start();
    }

    /**
     * 在AlertDialog的onStop()生命周期里面执行停止动画
     */
    @Override
    protected void onStop() {
        super.onStop();
        animation.stop();
    }
}
