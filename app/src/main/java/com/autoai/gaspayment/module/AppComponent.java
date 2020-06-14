package com.autoai.gaspayment.module;
import com.autoai.gaspayment.base.BaseApp;

import javax.inject.Singleton;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by nxp
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        AppModule.class,
        BuildersModule.class,

})
public interface AppComponent extends AndroidInjector<BaseApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseApp>{}
}
