package org.wbing.http;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.wbing.http.client.WHttpCookieJar;

import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * @author wangbing
 * @date 2018/8/30
 */
public class WHttpConfig {
    private static Context mContext;
    private static String mBaseUrl;
    private static Gson mGson;
    private static OkHttpClient mClient;

    static void init(Context context) {
        mContext = context;
    }

    public static String getBaseUrl() {
        if (TextUtils.isEmpty(mBaseUrl)) {
            ApplicationInfo appInfo = null;
            try {
                appInfo = mContext.getPackageManager()
                        .getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            assert appInfo != null;
            Bundle metaData = appInfo.metaData;
            if (metaData == null) {
                throw new RuntimeException("未在manifest中配置 w_http_debug、host_debug、host_release");
            }
            boolean debug = metaData.getBoolean("w_http_debug", false);
            String host = metaData.getString(debug ? "w_http_host_debug" : "w_http_host_release");
            if (TextUtils.isEmpty(host)) {
                throw new RuntimeException("未在manifest中配置" + (debug ? "w_http_host_debug" : "w_http_host_release"));
            } else {
                mBaseUrl = host;
            }
        }
        return mBaseUrl;
    }

    public static Gson getGson() {
        if (mGson == null) {
            mGson = new GsonBuilder()
                    .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                    .create();
        }
        return mGson;
    }

    public static OkHttpClient getClient() {
        if (mClient == null) {
            mClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .addInterceptor(new WHttpInterceptor())
                    .cookieJar(new WHttpCookieJar(mContext))
                    .sslSocketFactory(getSSLSocketFactory())
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        }
        return mClient;
    }


    /**
     * 获取SSLSocketFactory
     *
     * @param certificates 证书流文件
     * @return
     */
    private static SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;

//        try {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            int index = 0;
//            for (InputStream certificate : certificates) {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//
//                try {
//                    if (certificate != null)
//                        certificate.close();
//                } catch (IOException e) {
//                }
//            }
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//            return sslContext.getSocketFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

}
