package com.example.dell.yuekaodemo.mvp.cart.model;


import com.example.dell.yuekaodemo.mvp.cart.model.bean.CartBean;
import com.example.dell.yuekaodemo.utils.Api;
import com.example.dell.yuekaodemo.utils.RetrofitManager;

import io.reactivex.Observable;

/**
 * Created by Dell on 2018/7/27.
 */

public class CartModel{

    public Observable<CartBean> getCart(int uid){
        return RetrofitManager.getDefault().create(Api.class).carts(uid);
    }

}
