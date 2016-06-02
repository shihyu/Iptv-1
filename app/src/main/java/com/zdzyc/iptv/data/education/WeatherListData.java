package com.zdzyc.iptv.data.education;

import com.zdzyc.iptv.data.education.entity.WeatherData;

/**
 * Created by zhoudezheng on 16/5/15.
 */
public class WeatherListData {
    private String desc;

    private int status;

    private WeatherData data;

    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setData(WeatherData data){
        this.data = data;
    }
    public WeatherData getData(){
        return this.data;
    }
}
