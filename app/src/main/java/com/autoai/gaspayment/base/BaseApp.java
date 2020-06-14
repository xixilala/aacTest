package com.autoai.gaspayment.base;

import com.autoai.gaspayment.module.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public class BaseApp extends DaggerApplication {

    private static BaseApp mApp;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static BaseApp getApplication(){
        return mApp;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
