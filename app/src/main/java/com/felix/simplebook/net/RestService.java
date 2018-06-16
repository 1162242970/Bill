package com.felix.simplebook.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 搭建网络框架Retrofit
 * 1.创建用来描述网络请求的接口
 */

public interface RestService {
    /**
     * 采用注解的形式创建网络请求的接口
     *
     * @return 返回一个Call接口
     * @GET get请求
     * @url注解 get请求中的url
     * @QueryMap 请求参数, 会将参数以键值对的形式添加到Url后面
     */
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * @Post post请求
     * @FormUrlEncoded 表单提交
     * @Field Post表单提交域
     * @Field 批量提交表单域
     */
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    /**
     * 原始数据不用添加FormUrlEncoded
     */
    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    /**
     * @PUT put请求 用于更新
     */
    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    /**
     * @DELETE deleter请求
     * 删除数据
     */
    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * @Streaming 下载时, 边下载边写入
     * 下载请求
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * 上传文件需要用到@Multipart和@POST注解
     * 参数使用@Part
     */
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part part);
}
