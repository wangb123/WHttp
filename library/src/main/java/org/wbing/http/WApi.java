package org.wbing.http;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author wangbing
 * @date 2018/8/30
 */
public interface WApi {
    /**
     * get方法
     *
     * @param url    链接
     * @param params 地址
     * @return
     */
    @GET()
    Flowable<JsonObject> get(@Url String url, @QueryMap Map<String, String> params);

    /**
     * post方法
     *
     * @param url    链接
     * @param params 地址
     * @return
     */
    @FormUrlEncoded
    @POST()
    Flowable<JsonObject> post(@Url String url, @FieldMap Map<String, String> params);


    /**
     * post方法
     *
     * @param url    链接
     * @param params 地址
     * @return
     */
    @FormUrlEncoded
    @PUT()
    Flowable<JsonObject> put(@Url String url, @FieldMap Map<String, String> params);

}
