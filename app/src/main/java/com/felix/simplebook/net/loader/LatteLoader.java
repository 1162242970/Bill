package com.felix.simplebook.net.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


import com.felix.simplebook.R;
import com.felix.simplebook.utils.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by android on 17-12-1.
 * 创建全图的Dialog,设置
 *
 */

public class LatteLoader {

    private static final int LOADER_SIZE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotateMultipleIndicator.name();

    /**
     * 三个重载showLoading方法,一个为默认,一个传入字符串,一个传入LoaderStyle枚举类
     * @param context Dialog的上下文对象一定要传入当前Fragment或者Activity的
     * @param type 进度条Indicator样式
     */

    public static void showLoading(Context context, String type) {

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        //创建avLoadingIndicatorView并加载到Dialog中
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        //通过工具类获取屏幕宽高
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE;
            lp.height = deviceHeight / LOADER_SIZE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context, Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    /**
     * 取消Dialog显示
     */
    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }

        }
    }

}
