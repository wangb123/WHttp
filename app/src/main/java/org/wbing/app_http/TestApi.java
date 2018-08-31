package org.wbing.app_http;

import com.google.gson.JsonObject;

import org.wbing.base.ui.WCallback;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author wangbing
 * @date 2018/8/31
 */
public interface TestApi {

    /**
     * get请求
     *
     * @param path   路径
     * @param params 请求参数
     * @return
     */
    @GET("api/sharecenter/v1/{path}")
    Observable<JsonObject> get(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, String> params);

}
