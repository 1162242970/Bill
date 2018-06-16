package com.felix.simplebook.net.rx;

import android.content.Context;


import com.felix.simplebook.net.RestCreator;
import com.felix.simplebook.net.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by android on 17-12-1.
 */

public class RxRestClientBuilder {

    private String mUrl;
    public static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mfile = null;

    /**
     * 只允许同包的RestClient来创建它
     * protected修饰
     */
    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mfile = file;
        return this;
    }

    public final RxRestClientBuilder file(String file) {
        this.mfile = new File(file);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }


    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS,  mBody, mLoaderStyle, mfile, mContext);
    }

}
