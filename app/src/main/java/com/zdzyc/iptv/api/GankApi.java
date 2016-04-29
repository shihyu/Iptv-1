package com.zdzyc.iptv.api;

import com.zdzyc.iptv.data.MeizhiData;
import com.zdzyc.iptv.data.RelaxVideoData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zdzyc on 2016/4/29.
 */
public interface GankApi {

    @GET("/data/福利/" + 10 + "/{page}")
    Observable<MeizhiData> getMeizhiData(@Path("page") int page);

    @GET("/data/休息视频/" + 10 + "/{page}")
    Observable<RelaxVideoData> get休息视频Data(@Path("page") int page);
}
