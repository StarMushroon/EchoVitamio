package com.xiao.echovitamio;

import android.content.Context;

/**
 * Created by StarMushroom on 2017/4/7.
 */

public class DensityUtil {

    public static int dip2px(float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
