package com.zdzyc.iptv.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Window;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.util.Account;
import com.zdzyc.iptv.util.Common;
import com.zdzyc.iptv.util.Constant;

/**
 * Created by zdzyc on 2015/9/20.
 */
public class MyApplication extends Application{

    private static MyApplication instance;
    public static  DisplayImageOptions options; //配置图片加载及显示选项
    public static Account account;

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
        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        Common.SCREEN_W = wm.getDefaultDisplay().getWidth();
        Common.SCREEN_H = wm.getDefaultDisplay().getHeight();
        initImageLoader(getApplicationContext());
    }
    /**初始化图片加载类配置信息**/
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)//加载图片的线程数
                .denyCacheImageMultipleSizesInMemory() //解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//设置磁盘缓存文件名称
                .tasksProcessingOrder(QueueProcessingType.LIFO)//设置加载显示图片队列进程
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);

        //配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.loading)    //在ImageView加载过程中显示图片
                .showImageForEmptyUri(R.mipmap.error)  //image连接地址为空时
                .showImageOnFail(R.mipmap.error)  //image加载失败
                .cacheInMemory(true)  //加载图片时会在内存中加载缓存
                .cacheOnDisc(true)   //加载图片时会在磁盘中加载缓存
                .displayer(new RoundedBitmapDisplayer(20))  //设置用户加载图片task(这里是圆角图片显示)
                .build();
    }


}
