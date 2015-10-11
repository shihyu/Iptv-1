package com.zdzyc.iptv.util;

import android.graphics.Bitmap;

import com.zdzyc.iptv.app.MyApplication;

/**
 * Created by zdzyc on 2015/9/21.
 */
public class Account {

    private static String KEY_NAME;
    private static String KEY_AGE;
    private static String KEY_SEX;
    private static String KEY_ADDRESS;
    private static String KEY_FACE;
    private static String KEY_COMPANY;//公司
    private static String KEY_OCCUPATION;//职业
    private static String KEY_TELEPHONE;//电话
    private static String KEY_QQ;//QQ
    private static String KEY_EMAIL;//email

    public static String getEmail() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_EMAIL, "");
    }

    public static void setEmail(String email) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_EMAIL, email).commit();
    }

    public static String getQq() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_QQ, "");
    }

    public static void setQq(String qq) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_QQ, qq).commit();
    }

    public static String getTelephone() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_TELEPHONE, "");
    }

    public static void setTelephone(String telephone) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_TELEPHONE, telephone).commit();
    }

    public static String getName() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_NAME, "");
    }

    public static void setName(String name) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_NAME, name).commit();
    }

    public static String getAge() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_AGE, "");
    }

    public static void setAge(String age) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_AGE, age).commit();
    }

    public static String getSex() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_SEX, "");
    }

    public static void setSex(String sex) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_SEX, sex).commit();
    }

    public static String getAddress() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_ADDRESS, "");
    }

    public static void setAddress(String address) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_ADDRESS, address).commit();
    }

    public String getFace() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_FACE, "");
    }

    public static void setFace(String face) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_FACE, face).commit();
    }

    public static String getCompany() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_COMPANY, "");
    }

    public static void setCompany(String company) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_COMPANY, company).commit();
    }

    public static String getOccupation() {
        return MyApplication.instance().getSharedPreferences()
                .getString(KEY_OCCUPATION, "");
    }

    public static void setOccupation(String occupation) {
        MyApplication.instance().getSharedPreferences().edit()
                .putString(KEY_OCCUPATION, occupation).commit();
    }
}
