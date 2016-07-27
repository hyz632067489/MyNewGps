package com.hyz.mynewgps;

import android.app.Application;

import com.hyz.mynewgps.common.Constants;
import com.orhanobut.logger.Logger;

import okhttplib.CacheLevel;
import okhttplib.CacheType;
import okhttplib.OkHttpUtil;

/**
 * Created by ${hyz} on 2016/7/26.
 */
public class App extends Application {

    public static App app;

    public static  App getApp(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        OkHttpUtil.init(this)
                .setConnectTimeout(30)//超时时间设置
                .setMaxCacheSize(10 * 1024 * 1024)//设置缓存空间大小
                .setCacheLevel(CacheLevel.FIRST_LEVEL)//缓存等级
                .setCacheType(CacheType.NETWORK_THEN_CACHE)//缓存类型
                .setShowHttpLog(true)//显示请求日志
                .setShowLifecycleLog(true)
                .setRetryOnConnectionFailure(true)
                .build();

        initLogger();
    }

    private void initLogger() {
        Logger.init(Constants.LOGGER_TAG).methodCount(1).hideThreadInfo();
    }
}
