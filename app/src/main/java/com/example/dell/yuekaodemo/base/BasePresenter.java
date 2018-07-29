package com.example.dell.yuekaodemo.base;

/**
 * Created by Dell on 2018/7/26.
 */

public abstract class BasePresenter<V extends IView> {
    protected V view;

    public BasePresenter(V view) {
        this.view = view;
        //初始化model
        initModel();
    }

    protected abstract void initModel();

    void onDestroy() {
        view = null;
    }
}
