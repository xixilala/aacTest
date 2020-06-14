package com.autoai.gaspayment.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description: 应用程序Activity管理类,用于管理Activity和应用程序退出
 */
public class AppManager {

    private static Stack<Activity> sActivityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     * @return
     */
    public static AppManager getAppManager(){
        if (instance == null){
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加activity到栈
     * @param activity
     */
    public void addActivity(Activity activity){
        if (sActivityStack == null){
            sActivityStack = new Stack<>();
        }
        sActivityStack.add(activity);
    }

    /**
     * 获取当前activity
     * @return
     */
    public Activity currentActivity(){
        Activity activity = sActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity(){
        Activity activity = sActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity != null){
            sActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名activity
     * @param cls
     */
    public void finishActivity(Class<?> cls){
        for (Activity activity : sActivityStack){
            if (activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        for (int i = 0; i < sActivityStack.size(); i++) {
            if (null != sActivityStack.get(i)){
                sActivityStack.get(i).finish();
            }
        }
        sActivityStack.clear();
    }

    /**
     * 退出程序
     * @param context
     */
    public void AppExit(Context context){
        finishAllActivity();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(context.getPackageName());
        System.exit(0);
    }

    public boolean isAppExit(){
        return sActivityStack == null || sActivityStack.isEmpty();
    }
}
