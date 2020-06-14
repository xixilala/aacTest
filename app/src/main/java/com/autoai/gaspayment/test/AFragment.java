package com.autoai.gaspayment.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.autoai.gaspayment.R;
import com.autoai.gaspayment.base.BaseAacFragment;
import com.autoai.gaspayment.viewmodel.MainViewModel;

/**
 * @author: apple
 * @date: 2020/6/14
 * description:
 */
public class AFragment extends BaseAacFragment<MainViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.afragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoadData() {

    }

}
