package com.autoai.gaspayment.base;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.autoai.gaspayment.utils.TUtil;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public abstract class BaseMvvmActivity<T extends ViewDataBinding, M extends ViewModel> extends BaseActivity
        implements HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<android.app.Fragment> frameworkFragmentInjector;
    @Inject
    ViewModelProvider.Factory viewModelFactory;


    private Class<M> viewModelClass;
    protected M mViewmodel;
    protected T mBinding;

    protected abstract @LayoutRes
    int getLayoutId();
    protected abstract void create(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        viewModelClass = TUtil.getT1(this, 1);
        AndroidInjection.inject(this);
        if (viewModelClass != null) {
            mViewmodel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
        }
        create(savedInstanceState);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    @Override
    public AndroidInjector<android.app.Fragment> fragmentInjector() {
        return frameworkFragmentInjector;
    }

}
