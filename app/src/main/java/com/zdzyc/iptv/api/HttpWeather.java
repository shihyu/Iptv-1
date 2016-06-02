package com.zdzyc.iptv.api;

import android.text.TextUtils;

import com.zdzyc.iptv.app.MyApplication;
import com.zdzyc.iptv.data.education.WeatherListData;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
public class HttpWeather {

    public static final String BASE_URL = "http://wthrcdn.etouch.cn/";

    private static final int DEFAULT_TIMEOUT = 5;

    private static HttpWeather instance;

    private Retrofit retrofit;
    public GankApi gankApi;


    //设缓存有效期为两个星期
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 14;
    //查询缓存的Cache-Control设置
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    /**
     * 创建拦截器
     * <p/>
     * 设置max-age为60s之后，这60s之内不管你有没有网,都读缓存。
     * <p/>
     * max-stale设置为4周，意思是，网络未连接的情况下设置缓存时间为4周。
     */
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Response response = chain.proceed(request);

            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=60,max-stale=2419200";
            }
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    //设置缓存路径
    File httpCacheDirectory = new File(MyApplication.sContext.getCacheDir(), "responses");
    //设置缓存 10M
    Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

    //创建OkHttpClient，并添加拦截器和缓存代码
    //手动创建一个OkHttpClient并设置超时时间
    OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .cache(cache).build();

    //构造方法私有
    private HttpWeather() {

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        gankApi = retrofit.create(GankApi.class);
    }

    //获取单例
    public static HttpWeather getInstance() {
        if (instance == null) {
            instance = new HttpWeather();
        }
        return instance;
    }

    /***
     * 获取天气
     *
     * @param subscriber
     * @param city
     */
    public void getWeatherData(Subscriber<WeatherListData> subscriber, String city) {
        Observable observable = gankApi.getWeatherData(city);
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
