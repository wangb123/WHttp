#WHttp文档#
####使用方法：####

```groovy
	dependencies { 
	    implementation 'org.wbing:http:0.0.1'
	    implementation 'org.aspectj:aspectjrt:1.8.9' 
	    /**
	     *     用于网络
	     *     retrofit
	     */
	    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
	    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
	    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
	    implementation "io.reactivex.rxjava2:rxjava:2.1.17"
	}
```

AndroidManifest.xml中添加

```xml
        <!-- 是否正在debug -->
        <meta-data
            android:name="w_http_debug"
            android:value="false" />
        <!-- 测试域名 -->
        <meta-data
            android:name="w_http_host_debug"
            android:value="https://testopen.api/" />
        <!-- 线上域名 -->
        <meta-data
            android:name="w_http_host_release"
            android:value="http://open.api/" />
```


##功能说明##
>基于[retrofit2](https://github.com/square/retrofit)封装的http库


###1、简单使用

```java
WHttp.request().get("http://api.m.huya.com/filter/fansGroupSensitiveFilter",
                new HashMap<>(0))
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onError(Throwable e) {
                        WLogTool.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        WLogTool.e("结束");
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        WLogTool.e(jsonObject.toString());
                    }
                });
```

###2、自定义api
####2.1、定义ApiService

```java
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
```

####2.2、使用ApiService

```java
        WHttp.request(TestApi.class).get("", new HashMap<String, String>() {{
            //...
        }})
                .compose(provider.bindToLifecycle())
                .subscribe(new SafeObserver<>(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        WLogTool.e(jsonObject.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        WLogTool.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        WLogTool.e("结束");
                    }
                }));
```



