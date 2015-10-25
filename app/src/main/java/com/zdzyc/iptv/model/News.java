package com.zdzyc.iptv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by zdzyc on 2015/10/25.
 * <p/>
 * 新闻实体类
 */
public class News implements Parcelable {
    private String title;//标题
    private String details;//详情
    private String picture;//图片
    private String video;//视频
    private String createdAt;//创建日期
    private String updatedAt;//更新日期
    private String support;//赞的个数

    public News() {
    }

    public News(String title, String details, String picture, String video, String createdAt, String updatedAt, String support) {
        this.title = title;
        this.details = details;
        this.picture = picture;
        this.video = video;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.support = support;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错
        // 2.序列化对象
        dest.writeString(title);
        dest.writeString(details);
        dest.writeString(picture);
        dest.writeString(video);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(support);
    }

    // 1.必须实现Parcelable.Creator接口,否则在获取News数据的时候，会报错，如下：
    // android.os.BadParcelableException:
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.zdzyc.iptv.model.News
    // 2.这个接口实现了从Percel容器读取News数据，并返回News对象给逻辑层使用
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
    // 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
    // 5.反序列化对象
    public static final Parcelable.Creator<News> CREATOR = new Creator() {

        @Override
        public News createFromParcel(Parcel source) {
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            News news = new News();
            news.setTitle(source.readString());
            news.setDetails(source.readString());
            news.setPicture(source.readString());
            news.setVideo(source.readString());
            news.setCreatedAt(source.readString());
            news.setUpdatedAt(source.readString());
            news.setSupport(source.readString());
            return news;
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
