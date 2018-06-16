package com.felix.simplebook.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.litepal.LitePalApplication;

/**
 * Created by android on 17-12-6.
 * SharedPreferences工具类
 */

public final class MyPreference {

    /**
     * 提示:
     * Activity.getPreferences(int mode)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml
     * Context.getSharedPreferences(String name,int mode)生成name.xml
     */


    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(LitePalApplication.getContext()
                    .getApplicationContext());
    private static final String APP_PREFERENCES_KEY = "profile";

    private static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }

    public static void setAppProfile(String val) {
        getAppPreference()
                .edit()
                .putString(APP_PREFERENCES_KEY, val)
                .apply();
    }

    public static String getAppProfile() {
        return getAppPreference().getString(APP_PREFERENCES_KEY, null);
    }

    public static JSONObject getAppProfileJson() {
        final String profile = getAppProfile();
        return JSON.parseObject(profile);
    }

    public static void removeAppProfile() {
        getAppPreference()
                .edit()
                .remove(APP_PREFERENCES_KEY)
                .apply();
    }

    public static void clearAppPreferences() {
        getAppPreference()
                .edit()
                .clear()
                .apply();
    }

    /**
     * 设定一个Flag确认是否是第一次进入
     *
     */
    public static void setAppFlag(String key, boolean flag) {
        getAppPreference()
                .edit()
                .putBoolean(key, flag)
                .apply();
    }

    public static boolean getAppFlag(String key) {
        return getAppPreference()
                .getBoolean(key, false);
    }

    /**
     * 提供给外部存入SharedPreferences的接口
     * @param key 存入SharedPreferences时的Key
     * @param val 存入的值
     */
    public static void addCustomAppProfile(String key, String val) {
        getAppPreference()
                .edit()
                .putString(key, val)
                .apply();
    }

    /**
     * 提供外部获取SharedPreferences的接口
     * @param key 获取SharedPreferences的Key
     * @return 获取的值
     */
    public static String getCustomAppProfile(String key) {
        return getAppPreference().getString(key, "");
    }

}
