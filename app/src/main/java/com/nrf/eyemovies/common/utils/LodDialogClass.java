package com.nrf.eyemovies.common.utils;

import android.content.Context;




/**
 * Created by yuyd on 2017/5/17.
 */

public class LodDialogClass {
    public static AnimDrawableAlertDialog customDialog;

    // 显示自定义加载对话框
    public static AnimDrawableAlertDialog showCustomCircleProgressDialog(Context context) {
        if (customDialog != null) {
            try {
                customDialog.dismiss();
                customDialog.cancel();
                customDialog = null;
            } catch (Exception e) {
            }
        }
        customDialog = new AnimDrawableAlertDialog(context);
        customDialog.setCancelable(true);// 是否可用用"返回键"取消
        try {
            customDialog.show();
        } catch (Exception e) {
        }
        customDialog.setCanceledOnTouchOutside(false);//设置dilog点击屏幕空白处不消失
        return customDialog;
    }

    // 关闭自定义加载对话框
    public static void closeCustomCircleProgressDialog() {
        if (customDialog != null) {
//            HttpExecutor.cancleAllRequest();//取消所有网络请求
            try {
                customDialog.cancel();
                customDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setCustomCircleProgressDialogHintText(String hintText) {
        if (customDialog != null) {
            customDialog.setHintText(hintText);
        }
    }
}
