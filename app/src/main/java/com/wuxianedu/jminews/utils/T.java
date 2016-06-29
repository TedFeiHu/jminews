package com.wuxianedu.jminews.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Hu131 on 2016/6/21.
 */
public class T {
    public static void showShort(Context context, String param) {
        Toast.makeText(context, param, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String param) {
        Toast.makeText(context, param, Toast.LENGTH_LONG).show();
    }
}
