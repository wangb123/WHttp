package org.wbing.http.client;

import android.content.Context;

import org.wbing.http.client.cookie.PersistentCookieStore;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author wangbing
 * @date 2018/8/31
 */
public class WHttpCookieJar implements CookieJar {
    /**
     * Cookie的帮助类
     */
    private PersistentCookieStore cookieStore;
    private Context context;

    public WHttpCookieJar(Context context) {
        this.context = context;
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                //将cookie保存起来
                getCookieStore().add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return getCookieStore().get(url);
    }

    private PersistentCookieStore getCookieStore() {
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(context);
        }
        return cookieStore;
    }
}
