package com.autoai.gaspayment.utils;

import androidx.annotation.VisibleForTesting;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public class MainThreadAssert {

    @VisibleForTesting
    public static MainThreadPlugin sPlugin = new MainThreadPlugin();

    public static void assertMainThread(){
        sPlugin.assertMainThread();
    }

    public static void assertNotMainThread(){
        sPlugin.assertNotMainThread();
    }

    public static boolean isMainThread(){
        return sPlugin.isMainThread();
    }
}
