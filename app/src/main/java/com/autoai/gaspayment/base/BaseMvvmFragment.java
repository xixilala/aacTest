package com.autoai.gaspayment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.autoai.gaspayment.utils.TUtil;

import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public abstract class BaseMvvmFragment<T extends ViewDataBinding, M extends ViewModel> extends BaseFragment
        implements HasSupportFragmentInjector {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    private Class<M> viewModelClass;
    protected T mBinding;
    protected M mViewmodel;

    protected abstract int getLayoutId();
    protected abstract void initData();
    protected abstract void lazyLoadData();

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
//        View view = mBinding.getRoot();
//        viewModelClass = TUtil.getT1(this, 1);
//        if (viewModelClass != null) {
//            AndroidSupportInjection.inject(this);
//            mViewmodel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
//        }
//        return view;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, int FOR_OVERRIDE) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View view = mBinding.getRoot();
        viewModelClass = TUtil.getT1(this, 1);
        if (viewModelClass != null) {
            AndroidSupportInjection.inject(this);
            mViewmodel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
        }
        return view;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

}
