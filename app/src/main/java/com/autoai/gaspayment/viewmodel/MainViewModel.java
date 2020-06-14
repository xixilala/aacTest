package com.autoai.gaspayment.viewmodel;

import com.autoai.gaspayment.base.AppViewModel;
import com.autoai.gaspayment.base.RxLiveData;
import com.autoai.gaspayment.beans.GankBean;
import com.autoai.gaspayment.repository.Repository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public class MainViewModel extends AppViewModel {

    private Repository mRepository;
    public RxLiveData<List<GankBean.Gank>> mRxLiveData;

    @Inject
    public MainViewModel(Repository repository){
        mRepository = repository;
        mRxLiveData = createRxLiveData();
    }

    public void getGank(String title , int page){
        mRxLiveData.execute(mRepository.getGankData(title, page), true);
    }
}
