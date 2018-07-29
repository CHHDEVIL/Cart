package com.example.dell.yuekaodemo.mvp.cart.presenter;

import com.example.dell.yuekaodemo.base.BasePresenter;
import com.example.dell.yuekaodemo.mvp.cart.model.CartModel;
import com.example.dell.yuekaodemo.mvp.cart.model.bean.CartBean;
import com.example.dell.yuekaodemo.mvp.cart.view.iview.ICartView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dell on 2018/7/27.
 */

public class CartPresenter extends BasePresenter<ICartView> {

    private CartModel cartModel;

    public CartPresenter(ICartView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        cartModel = new CartModel();
    }

    public void getCart(int uid) {
        cartModel.getCart(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CartBean>() {
                    @Override
                    public void accept(CartBean cartBean) throws Exception {
                        if (view != null) {
                            view.onCartSuccess(cartBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (view != null) {
                            view.onFailed(throwable.toString());
                        }
                    }
                });
    }
}
