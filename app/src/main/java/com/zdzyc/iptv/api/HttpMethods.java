package com.zdzyc.iptv.api;

import com.zdzyc.iptv.data.MeizhiData;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zdzyc on 2016/4/29.
 */
public class HttpMethods {
    public static final String BASE_URL = "http://gank.io/api";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private GankApi gankApi;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        gankApi = retrofit.create(GankApi.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /***
     * 获取妹子图片
     * @param subscriber
     * @param page
     */
    public void getMeizhiData(Subscriber<MeizhiData> subscriber, int page) {
        Observable observable = gankApi.getMeizhiData(page);
        toSubscribe(observable, subscriber);
    }

    /**
     * 封装这个为了后面少些这些重复的代码
     *
     * @param o
     * @param s
     * @param <T>
     */
    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


}
