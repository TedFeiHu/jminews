package com.wuxianedu.jminews.utils;

import android.text.TextUtils;

/**
 * Created by Hu131 on 2016/6/21.
 */
public class ValidUtil {
    public static boolean isEmpty(String param){
        param = param.trim();
        if (TextUtils.isEmpty(param)){
            return true;
        }
        return false;
    }
}
