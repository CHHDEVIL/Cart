package com.example.dell.yuekaodemo.mvp.home.presenter;

import com.example.dell.yuekaodemo.base.BasePresenter;
import com.example.dell.yuekaodemo.mvp.home.model.HomeModel;
import com.example.dell.yuekaodemo.mvp.home.model.bean.HomeBean;
import com.example.dell.yuekaodemo.mvp.home.view.iview.IHomeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dell on 2018/7/26.
 */

public class HomePresenter extends BasePresenter<IHomeView> {

    private HomeModel homeModel;
    private Object home;

    public HomePresenter(IHomeView iHomeView) {
        super(iHomeView);
    }

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    public void getHome() {
        homeModel.getHome()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeBean>() {
                    @Override
                    public void accept(HomeBean homeBean) throws Exception {
                        if (view != null) {
                            view.onHomeSuccess(homeBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(view!=null){
                            view.onFailed(throwable.toString());
                        }
                    }
                });
    }
}
