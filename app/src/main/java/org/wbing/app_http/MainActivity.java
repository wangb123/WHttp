package org.wbing.app_http;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.wbing.app_http.databinding.ActivityMainBinding;
import org.wbing.base.ui.impl.WAct;
import org.wbing.http.WHttp;
import org.wbing.tools.WLogTool;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.SafeObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends WAct<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WHttp.init(getApplicationContext());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getBinding().btn.setOnClickListener(v -> loadData());
    }

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    private final LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(this);

    @Override
    public void loadData() {
//        WHttp.request(TestApi.class).get("filter/fansGroupSensitiveFilter", new HashMap<String, String>() {{
//            put("center_id", "888");
//        }})
//                .compose(provider.bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .delay(5, TimeUnit.SECONDS)
//                .subscribe(new SafeObserver<>(new Observer<JsonObject>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(JsonObject jsonObject) {
//                        WLogTool.e(jsonObject.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        WLogTool.e(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        WLogTool.e("结束");
//                    }
//                }));
//
//
//        if (true) return;

        WHttp.request().get("http://api.m.huya.com/filter/fansGroupSensitiveFilter",
                new HashMap<>(0))
                .subscribeOn(Schedulers.io())
                .delay(5, TimeUnit.SECONDS)
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
    }

    @Override
    public void recycle() {

    }
}
