package com.nrf.eyemovies.common.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nrf.eyemovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2017/08/15
 *     desc   : 普通的类似ios的对话框
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */

public class CommonDialog extends Dialog {
    private final Context mContext;
    @BindView(R.id.title_dialog)
    TextView titleDialog;
    @BindView(R.id.msg_dialog)
    TextView msgDialog;
    @BindView(R.id.viewlin)
    View viewlin;
    @BindView(R.id.cancel_dialog)
    Button cancelDialog;
    @BindView(R.id.ok_dialog)
    Button okDialog;
    @BindView(R.id.ll_button)
    LinearLayout llButton;

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.dialog_untran);
        mContext = context;
        setContentView(R.layout.dialog_canncle_attention);
        ButterKnife.bind(this);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

            }
        });
    }

    private String title;
    private String content;
    private String cancel;
    private String ensure;
    private float textSize = -1;


    /**
     * 确定按钮事件监听 默认是dismiss对话框
     *
     * @param onClickListenerEnsure
     */
    public void setOnClickListenerEnsure(View.OnClickListener onClickListenerEnsure) {
        this.onClickListenerEnsure = onClickListenerEnsure;
    }

    /**
     * 取消按钮事件监听 默认是dismiss对话框
     *
     * @param onClickListenerCancel
     */
    public void setOnClickListenerCancel(View.OnClickListener onClickListenerCancel) {
        this.onClickListenerCancel = onClickListenerCancel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置确定按钮内容 默认为确定
     *
     * @param ensure
     */
    public void setEnsure(String ensure) {
        this.ensure = ensure;
    }

    public void setContentTextSize(float textSize){
        this.textSize = textSize;
    }
    /**
     * 设置内容    默认为空
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 设置取消按钮内容  默认为取消
     *
     * @param cancel
     */
    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    private View.OnClickListener onClickListenerCancel;
    private View.OnClickListener onClickListenerEnsure;
    @Override
    public void show() {
        if (TextUtils.isEmpty(title)) {
            titleDialog.setVisibility(View.GONE);
        } else {
            titleDialog.setVisibility(View.VISIBLE);
            setTextViewTxt(titleDialog, title);
        }
        if (TextUtils.isEmpty(cancel)) {
            cancel = mContext.getString(R.string.cancel);
        }
        if (TextUtils.isEmpty(ensure)) {
            ensure = mContext.getString(R.string.ensure);
        }
        setTextViewTxt(msgDialog, content);
        setTextViewTxt(cancelDialog, cancel);
        setTextViewTxt(okDialog,ensure);
        setTextSize(msgDialog,textSize);
        setButtonOnClickListener(cancelDialog, onClickListenerCancel);
        setButtonOnClickListener(okDialog, onClickListenerEnsure);
        super.show();
    }

    private void setButtonOnClickListener(TextView textView, View.OnClickListener onClickListener) {
        if (textView == null) {
            return;
        }
        if (onClickListener == null) {
            onClickListener = onClickListenerDismiss;
        }
        textView.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListenerDismiss = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private void setTextViewTxt(TextView textView, String string) {
        if (null == textView) {
            return;
        }
        if (TextUtils.isEmpty(string)) {
            string = "";
        }
        textView.setText(string);
    }

    private void setTextSize (TextView textView, float size){
        if (null == textView) {
            return;
        }
        if (size <= 0) {
            return;
        }
        textView.setTextSize(size);

    }
}
