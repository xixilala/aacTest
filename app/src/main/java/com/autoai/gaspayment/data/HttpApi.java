package com.autoai.gaspayment.data;
import com.autoai.gaspayment.beans.GankBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public interface HttpApi {


    @GET("{type}/20/{page}")
    Flowable<GankBean> getGankData(@Path("type") String type, @Path("page") int page);
}
