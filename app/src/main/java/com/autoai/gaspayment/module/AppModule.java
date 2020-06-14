package com.autoai.gaspayment.module;

import android.content.Context;

import com.autoai.gaspayment.base.BaseApp;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
@Module(includes = {
        ApiModule.class,
        ViewModelModule.class,
})
public class AppModule {

    @Provides
    @Singleton
    Context provideContent(BaseApp application){
        return application.getApplicationContext();
    }
}
