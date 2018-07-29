package com.example.dell.yuekaodemo.mvp.home.view.iview;

import com.example.dell.yuekaodemo.base.IView;
import com.example.dell.yuekaodemo.mvp.home.model.bean.HomeBean;

/**
 * Created by Dell on 2018/7/26.
 */

public interface IHomeView extends IView {
    void onHomeSuccess(HomeBean homeBean);

    void onFailed(String error);
}
