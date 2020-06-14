package com.autoai.gaspayment.utils;

import android.os.Looper;

import androidx.annotation.VisibleForTesting;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public class MainThreadPlugin {

    private volatile Thread mMainThread;

    public MainThreadPlugin() {
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    public MainThreadPlugin(Thread mainThread){
        mMainThread = mainThread;
    }

    public void assertMainThread(){
        if (!isMainThread()){
            throw new IllegalStateException("Access not allowed on non main thread");
        }
    }

    public void assertNotMainThread(){
        if (isMainThread()){
            if (isMainThread()){
                throw new IllegalStateException("Access not allowed on main thread");
            }
        }
    }

    public boolean isMainThread(){
        if (mMainThread == null){
            return Looper.getMainLooper().getThread() == Thread.currentThread();
        } else {
            return mMainThread == Thread.currentThread();
        }
    }
}
