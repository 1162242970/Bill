package com.felix.simplebook.utils.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;


import org.litepal.LitePalApplication;

/**
 * 测量工具类
 */

public class DimenUtil {


    public static int getScreenWidth() {
        final Resources resources = LitePalApplication.getContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = LitePalApplication.getContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
