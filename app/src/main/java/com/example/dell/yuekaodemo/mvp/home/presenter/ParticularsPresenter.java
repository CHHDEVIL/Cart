package com.example.dell.yuekaodemo.mvp.home.presenter;

import android.view.View;

import com.example.dell.yuekaodemo.base.BasePresenter;
import com.example.dell.yuekaodemo.mvp.home.model.ParticularsModel;
import com.example.dell.yuekaodemo.mvp.home.model.bean.AddCartBean;
import com.example.dell.yuekaodemo.mvp.home.model.bean.ParticularsBean;
import com.example.dell.yuekaodemo.mvp.home.view.iview.IParticularsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dell on 2018/7/27.
 */

public class ParticularsPresenter extends BasePresenter<IParticularsView> {

    private ParticularsModel particularsModel;

    public ParticularsPresenter(IParticularsView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        particularsModel = new ParticularsModel();
    }

    public void getProductDetail(int pid) {
        particularsModel.getProductDetail(pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ParticularsBean>() {
                    @Override
                    public void accept(ParticularsBean particularsBean) throws Exception {
                        if (view != null) {
                            view.onParticularsSuccess(particularsBean);
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

    public void addCart(int uid, int pid) {
        particularsModel.addCart(uid, pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AddCartBean>() {
                    @Override
                    public void accept(AddCartBean addCartBean) throws Exception {
                        if (view != null) {
                            view.onAddCartSuccess(addCartBean);
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
