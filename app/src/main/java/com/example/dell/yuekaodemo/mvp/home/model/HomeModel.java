package com.example.dell.yuekaodemo.mvp.home.model;

import com.example.dell.yuekaodemo.mvp.home.model.bean.HomeBean;
import com.example.dell.yuekaodemo.utils.Api;
import com.example.dell.yuekaodemo.utils.RetrofitManager;

import io.reactivex.Observable;

/**
 * Created by Dell on 2018/7/26.
 */

public class HomeModel {
    public Observable<HomeBean> getHome(){
        return RetrofitManager.getDefault().create(Api.class).getHome();
    }
}
