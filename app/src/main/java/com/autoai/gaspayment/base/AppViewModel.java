package com.autoai.gaspayment.base;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public class AppViewModel extends ViewModel {

    private List<RxLiveData> mRxLiveDatas;

    protected <T> RxLiveData<T> createRxLiveData(){
        if (mRxLiveDatas == null) {
            mRxLiveDatas = new ArrayList<>(5);
        }
        RxLiveData<T> rxLiveData = new RxLiveData<>();
        mRxLiveDatas.add(rxLiveData);
        return rxLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRxLiveDatas != null){
            for (RxLiveData rxLiveData : mRxLiveDatas){
                rxLiveData.cancel();
            }
            mRxLiveDatas.clear();
        }
    }
}
