package com.zdzyc.iptv.data.entity;

import java.util.Date;

/**
 * Created by zdzyc on 2016/5/6.
 * 将video信息合并入meizhi对象中
 */
public class MeizhiWithVideo {
    public String imgurl;
    public String videourl;
    public String publishDate;

    public MeizhiWithVideo(String imgurl, String videourl, String publishDate) {
        this.imgurl = imgurl;
        this.videourl = videourl;
        this.publishDate = publishDate;
    }
}
