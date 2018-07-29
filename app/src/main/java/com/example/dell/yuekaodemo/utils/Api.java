package com.example.dell.yuekaodemo.utils;

import com.example.dell.yuekaodemo.constants.ConstantApi;
import com.example.dell.yuekaodemo.mvp.cart.model.bean.CartBean;
import com.example.dell.yuekaodemo.mvp.home.model.bean.AddCartBean;
import com.example.dell.yuekaodemo.mvp.home.model.bean.HomeBean;
import com.example.dell.yuekaodemo.mvp.home.model.bean.ParticularsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dell on 2018/7/26.
 */

public interface Api {

    @GET(ConstantApi.GETHOME)
    Observable<HomeBean> getHome();

    @GET(ConstantApi.GETPRODUCTDETAIL)
    Observable<ParticularsBean> getProductDefault(@Query("pid")int pid);

    @GET(ConstantApi.ADDCART)
    Observable<AddCartBean> addCart(@Query("uid")int uid,@Query("pid")int pid);

    @GET(ConstantApi.GETCARTS)
    Observable<CartBean> carts(@Query("uid")int uid);
}
