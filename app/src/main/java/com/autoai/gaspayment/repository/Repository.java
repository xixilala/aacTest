package com.autoai.gaspayment.repository;

import com.autoai.gaspayment.beans.GankBean;
import com.autoai.gaspayment.data.HttpApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public class Repository {

    private HttpApi mHttpApi;

    @Inject
    public Repository(HttpApi httpApi){
      mHttpApi = httpApi;
    }

    public Flowable<List<GankBean.Gank>> getGankData(String type, int page){
        return mHttpApi.getGankData(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GankBean, List<GankBean.Gank>>() {
                    @Override
                    public List<GankBean.Gank> apply(GankBean gankBean) throws Exception {
                        return gankBean.results;
                    }
                });
    }
}
