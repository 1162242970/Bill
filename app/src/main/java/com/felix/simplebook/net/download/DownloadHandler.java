package com.felix.simplebook.net.download;

import android.os.AsyncTask;


import com.felix.simplebook.net.RestCreator;
import com.felix.simplebook.net.callback.IError;
import com.felix.simplebook.net.callback.IFailure;
import com.felix.simplebook.net.callback.IRequest;
import com.felix.simplebook.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android on 17-12-4.
 */

public class DownloadHandler {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest IREQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess ISUCCESS;
    private final IFailure IFAILURE;
    private final IError IERROR;

    public DownloadHandler(String mUrl,
                           IRequest mRequest,
                           String mDownloadDir,
                           String mExtension,
                           String mName,
                           ISuccess iSuccess,
                           IFailure iFailure,
                           IError iError) {
        this.URL = mUrl;
        this.IREQUEST = mRequest;
        this.DOWNLOAD_DIR = mDownloadDir;
        this.EXTENSION = mExtension;
        this.NAME = mName;
        this.ISUCCESS = iSuccess;
        this.IFAILURE = iFailure;
        this.IERROR = iError;
    }

    public void handleDownload() {
        if (IREQUEST != null) {
            IREQUEST.onRequestStart();
        }
        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(IREQUEST, ISUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION,
                                    response, NAME);

                            //一定要判断AsyncTask结束再调用结束接口,否则会文件下载不全
                            if (task.isCancelled()){
                                if (IREQUEST != null) {
                                    IREQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if (IERROR != null){
                                IERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (IFAILURE != null) {
                            IFAILURE.onFailure();
                        }
                    }
                });

    }

}