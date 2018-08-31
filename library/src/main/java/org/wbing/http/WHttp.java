package org.wbing.http;

import android.content.Context;
import android.util.Log;


import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangbing
 * @date 2018/8/29
 */
public abstract class WHttp {

    public static void init(Context context) {
        WHttpConfig.init(context);
    }

    public static WApi request() {
        return request(WApi.class);
    }

    private static HashMap<Class, WeakReference> cache = new HashMap<>();

    public static <T> T request(final Class<T> service) {
        if (cache.containsKey(service)) {
            WeakReference reference = cache.get(service);
            if (reference != null && reference.get() != null) {
//                Log.e("TAG", "复用服务：" + service.getSimpleName());
                return (T) reference.get();
            } else {
                cache.remove(service);
            }
        }
//        Log.e("TAG", "创建服务：" + service.getSimpleName());
        T t = new Retrofit.Builder()
                .baseUrl(WHttpConfig.getBaseUrl())
                .client(WHttpConfig.getClient())
                .addConverterFactory(GsonConverterFactory.create(WHttpConfig.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(service);
        cache.put(service, new WeakReference<>(t));
        return t;
    }
}
