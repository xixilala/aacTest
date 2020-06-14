package com.autoai.gaspayment.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: nxp
 * @date: 2020/6/14
 * description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mActivity;
    private Unbinder mUnbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setBaseConfig();
    }

    private void setBaseConfig(){
        AppManager.getAppManager().addActivity(this);

        mUnbinder = ButterKnife.bind(this);
    }



    protected void readyGO(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }


}
