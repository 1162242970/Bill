package com.felix.simplebook.net;



import com.felix.simplebook.net.rx.RxRestService;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 2.创建Retrofit实例和网络请求接口的实例
 */

public class RestCreator {

    private static final class ParamsHolder {
        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    /**
     * 构建全局Retrofit客户端
     * 1.设置网络请求的Url地址
     * 2.设置一个OkHttpClient作为网络执行器
     * 3.设置数据解析器
     * 4.添加Rxjava Adapter
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = "http://127.0.0.1/";
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                添加拦截器
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 为Okhttp添加拦截器
     */
    private static final class OkHttpHolder {
        private static final int TIME_OUT = 60;

        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = null;

        /**
         * 初始化添加拦截器
         */
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }


    /**
     * Service接口
     */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * Service接口
     */
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_SERVICE;
    }

}
