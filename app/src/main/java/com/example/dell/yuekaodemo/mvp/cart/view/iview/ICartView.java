package com.example.dell.yuekaodemo.mvp.cart.view.iview;

import com.example.dell.yuekaodemo.base.IView;
import com.example.dell.yuekaodemo.mvp.cart.model.bean.CartBean;

/**
 * Created by Dell on 2018/7/27.
 */

public interface ICartView extends IView {
    void onCartSuccess(CartBean cartBean);

    void onFailed(String error);

}
