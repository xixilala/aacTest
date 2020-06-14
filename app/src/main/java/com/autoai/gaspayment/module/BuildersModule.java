package com.autoai.gaspayment.module;
import com.autoai.gaspayment.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
@Module
public abstract class BuildersModule {


    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}
