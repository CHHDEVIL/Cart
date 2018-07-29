package com.example.dell.yuekaodemo.mvp.home.model;

import com.example.dell.yuekaodemo.mvp.home.model.bean.AddCartBean;
import com.example.dell.yuekaodemo.mvp.home.model.bean.ParticularsBean;
import com.example.dell.yuekaodemo.utils.Api;
import com.example.dell.yuekaodemo.utils.RetrofitManager;

import io.reactivex.Observable;

/**
 * Created by Dell on 2018/7/27.
 */

public class ParticularsModel {
    public Observable<ParticularsBean> getProductDetail(int pid){
        return RetrofitManager.getDefault().create(Api.class).getProductDefault(pid);
    }

    public Observable<AddCartBean> addCart(int uid,int pid){
        return RetrofitManager.getDefault().create(Api.class).addCart(uid, pid);
    }

}
