package com.felix.simplebook.net;

import android.content.Context;


import com.felix.simplebook.net.callback.IError;
import com.felix.simplebook.net.callback.IFailure;
import com.felix.simplebook.net.callback.IRequest;
import com.felix.simplebook.net.callback.ISuccess;
import com.felix.simplebook.net.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by android on 17-12-1.
 */

public class RestClientBuilder {

    private String mUrl;
    public static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mIRequest = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mfile = null;

    /**
     * 只允许同包的RestClient来创建它
     * protected修饰
     */
    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mfile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mfile = new File(file);
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }


    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mDownloadDir, mExtension, mName, mIRequest, mISuccess,
                mIFailure, mIError, mBody, mLoaderStyle, mfile, mContext);
    }

}
