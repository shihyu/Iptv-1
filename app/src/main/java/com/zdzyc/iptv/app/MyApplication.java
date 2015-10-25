package com.zdzyc.iptv.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Window;
import android.view.WindowManager;

import com.zdzyc.iptv.R;
import com.zdzyc.iptv.util.Account;
import com.zdzyc.iptv.util.Common;
import com.zdzyc.iptv.util.Constant;

/**
 * Created by zdzyc on 2015/9/20.
 */
public class MyApplication extends Application{

    private static MyApplication instance;
    public static Account account;
    public static Context sContext;

    public MyApplication(){
        instance = this;
    }

    public static MyApplication instance() {
        if (instance != null)
            return instance;
        throw new IllegalStateException("Application has not been created");
    }

    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences(Constant.SHARE_PER_NAME, 3);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        Common.SCREEN_W = wm.getDefaultDisplay().getWidth();
        Common.SCREEN_H = wm.getDefaultDisplay().getHeight();
    }



}
