package com.felix.simplebook.net.interceptor;

import android.support.annotation.RawRes;


import com.felix.simplebook.utils.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by android on 17-12-4.
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int rwaId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rwaId;
    }

    /**
     * 传入拦截，返回一个json
     */
    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();

    }

    private Response debugResponse(Chain chain, @RawRes int rawid) {
        //获取raw中的JSON数组
        final String json  = FileUtil.getRawFile(rawid);
        return getResponse(chain, json);

    }

    /**
     *
     *在拦截方法中,如果拦截的url包含所要拦截的关键字,返回一个事先准备好的JSON数据
     * 否则原样返回
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
