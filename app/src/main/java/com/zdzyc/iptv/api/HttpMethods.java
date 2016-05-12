package com.zdzyc.iptv.api;

import com.zdzyc.iptv.data.GankData;
import com.zdzyc.iptv.data.MeizhiData;
import com.zdzyc.iptv.data.MeizhiWithGankData;
import com.zdzyc.iptv.data.MeizhiWithVideoData;
import com.zdzyc.iptv.data.entity.Gank;
import com.zdzyc.iptv.data.entity.Meizhi;
import com.zdzyc.iptv.data.entity.MeizhiWithGank;
import com.zdzyc.iptv.data.entity.MeizhiWithVideo;
import com.zdzyc.iptv.data.entity.Video;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by zdzyc on 2016/4/29.
 */
public class HttpMethods {
    public static final String BASE_URL = "http://gank.io/api/";

    private static final int DEFAULT_TIMEOUT = 5;

    private static HttpMethods instance;

    private Retrofit retrofit;
    public GankApi gankApi;

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

    //获取单例
    public static HttpMethods getInstance() {
        if (instance == null) {
            instance = new HttpMethods();
        }
        return instance;
    }

    /***
     * 获取妹子图片
     *
     * @param subscriber
     * @param page
     */
    public void getMeizhiData(Subscriber<MeizhiData> subscriber, int page) {
        Observable observable = gankApi.getMeizhiData(page);
        toSubscribe(observable, subscriber);
    }

    /***
     * 获取干货
     *
     * @param subscriber
     * @param page
     */
    public void getGankData(Subscriber<MeizhiWithGankData> subscriber, int page) {
        Observable observableGankData = gankApi.getGankData(page);
        final Observable observableMeizhiData = gankApi.getMeizhiData(page);
        Observable observable = Observable.
                zip(observableGankData, observableMeizhiData, new Func2<GankData, MeizhiData, MeizhiWithGankData>() {
                    @Override
                    public MeizhiWithGankData call(GankData gankData, MeizhiData meizhiData) {
                        MeizhiWithGankData meizhiWithGankData = new MeizhiWithGankData();
                        int i =0;
                        for (Gank gank : gankData.results){
                            Meizhi meizhi = meizhiData.results.get(i++);
                            MeizhiWithGank meizhiWithGank = new MeizhiWithGank(meizhi.getUrl(),gank);
                            meizhiWithGankData.data.add(meizhiWithGank);
                        }
                        return meizhiWithGankData;
                    }
                });
        toSubscribe(observable, subscriber);
    }

    /***
     * 获取休息视频
     *
     * @param subscriber
     * @param page
     */
    public void getRelaxVideoData(Subscriber<MeizhiWithVideoData> subscriber, int page) {
        Observable observableMeizhiData = gankApi.getMeizhiData(page);
        Observable observableVideoData = gankApi.RelaxVideoData(page);
        Observable observable = Observable.
                zip(observableVideoData, observableMeizhiData, new Func2<Video, Meizhi, MeizhiWithVideo>() {
                    @Override
                    public MeizhiWithVideo call(Video video, Meizhi meizhi) {
                        return new MeizhiWithVideo(meizhi.getUrl(), video.getUrl(), meizhi.getCreatedAt());
                    }
                });
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
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


}
