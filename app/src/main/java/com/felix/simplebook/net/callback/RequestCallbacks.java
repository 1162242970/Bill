package com.felix.simplebook.net.callback;

import android.os.Handler;


import com.felix.simplebook.net.loader.LatteLoader;
import com.felix.simplebook.net.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RequestCallbacks类继承自Callback,
 * 向服务器发送请求的时候需要调用RequestCallbacks
 *
 */

public class RequestCallbacks implements Callback<String> {
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IFailure IFAILURE;
    private final IError IERROR;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IRequest iRequest, ISuccess iSuccess, IFailure iFailure, IError iError, LoaderStyle iLoaderStyle) {
        this.IREQUEST = iRequest;
        this.ISUCCESS = iSuccess;
        this.IFAILURE = iFailure;
        this.IERROR = iError;
        this.LOADER_STYLE = iLoaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (ISUCCESS != null) {
                    ISUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (IERROR != null) {
                IERROR.onError(response.code(), response.message());
            }
        }
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            },1000);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (IFAILURE != null) {
            IFAILURE.onFailure();
        }

        if (IREQUEST != null) {
            IREQUEST.onRequestEnd();
        }
    }
}
