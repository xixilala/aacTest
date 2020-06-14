package com.autoai.gaspayment.module;

import android.content.Context;

import com.autoai.gaspayment.constant.ApiConstant;
import com.autoai.gaspayment.data.HttpApi;

import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)  //打印相关
                //.addNetworkInterceptor(logInterceptor)  //打印LOG相关
                .build();
    }

    @Provides
    @Singleton
    public HttpApi provideHttpApi(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.HTTP_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //    .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpApi httpApi = retrofit.create(HttpApi.class);
        return httpApi;
    }
}
