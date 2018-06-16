package com.felix.simplebook.net.loader;

import android.content.Context;


import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * 提供AVLoadingIndicatorView
 * 缓存Indicator,将其放入WeakHashMap中,第一次获取的时候就去反射读取,后面就不用重复读取
 *
 */

public final class LoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context) {

        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    /**
     * 利用反射的机制去创建对应的Indicator
     * 添加对应的包名,返回Indiator对象
     *
     */
    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return  (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
