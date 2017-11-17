package com.nrf.eyemovies.common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/11/13.
 */

public class IntentUtils {
    public static void sysStartActivity(Context context, Class<?> nameClass) {
        Intent intent = new Intent(context, nameClass);
        context.startActivity(intent);
    }
    public static void sysStartActivity(Context context, Class<?> nameClass, Bundle bundle) {
        Intent intent = new Intent(context, nameClass);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }


}