package com.felix.simplebook.utils.callback;

import android.support.annotation.Nullable;

/**
 * 全局回调,图片剪裁后的回调
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
