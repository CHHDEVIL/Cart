package com.example.dell.yuekaodemo.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;

/**
 * Created by Dell on 2018/7/26.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment{

    protected P presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(provideLayoutId(), null);
        initView();
        presenter = providePresenter();
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract P providePresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int provideLayoutId();

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
