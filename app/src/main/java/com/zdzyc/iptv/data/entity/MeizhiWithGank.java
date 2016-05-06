package com.zdzyc.iptv.data.entity;

/**
 * Created by zdzyc on 2016/5/6.
 */
public class MeizhiWithGank {
    private String imgUrl;
    private Gank gank;

    public MeizhiWithGank(String imgUrl, Gank gank) {
        this.imgUrl = imgUrl;
        this.gank = gank;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Gank getGank() {
        return gank;
    }

    public void setGank(Gank gank) {
        this.gank = gank;
    }
}
