package com.example.dell.yuekaodemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Dell on 2018/7/27.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity{


    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        ButterKnife.bind(this);
        initView();

        presenter = providePresenter();

        initData();

    }

    protected abstract P providePresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int provideLayoutId();

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
