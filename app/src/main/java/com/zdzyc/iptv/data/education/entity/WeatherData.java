package com.zdzyc.iptv.data.education.entity;

import java.util.List;

/**
 * Created by zhoudezheng on 16/5/15.
 */
public class WeatherData {
    private String wendu;

    private String ganmao;

    private List<Forecast> forecasts;

    private Yesterday yesterday;

    private String aqi;

    private String city;

    public void setWendu(String wendu){
        this.wendu = wendu;
    }
    public String getWendu(){
        return this.wendu;
    }
    public void setGanmao(String ganmao){
        this.ganmao = ganmao;
    }
    public String getGanmao(){
        return this.ganmao;
    }
    public void setForecast(List<Forecast> forecast){
        this.forecasts = forecast;
    }
    public List<Forecast> getForecast(){
        return this.forecasts;
    }
    public void setYesterday(Yesterday yesterday){
        this.yesterday = yesterday;
    }
    public Yesterday getYesterday(){
        return this.yesterday;
    }
    public void setAqi(String aqi){
        this.aqi = aqi;
    }
    public String getAqi(){
        return this.aqi;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
}
