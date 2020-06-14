package com.autoai.gaspayment;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;

import com.autoai.gaspayment.base.BaseAacActivity;
import com.autoai.gaspayment.beans.GankBean;
import com.autoai.gaspayment.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends BaseAacActivity<MainViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void create(Bundle savedInstanceState) {
        mViewmodel.getGank("Android", 1);
        addObserve();
        Log.d("lllllllllllll", "create: ");
    }

    private void addObserve(){
        mViewmodel.mRxLiveData.observeData(this, new Observer<List<GankBean.Gank>>() {
            @Override
            public void onChanged(List<GankBean.Gank> ganks) {
                Log.d("------>", "onChanged: " + ganks.toString());
            }
        });
    }
}