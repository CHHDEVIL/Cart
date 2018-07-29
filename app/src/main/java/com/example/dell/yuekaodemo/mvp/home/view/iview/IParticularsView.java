package com.example.dell.yuekaodemo.mvp.home.view.iview;

import com.example.dell.yuekaodemo.base.IView;
import com.example.dell.yuekaodemo.mvp.home.model.bean.AddCartBean;
import com.example.dell.yuekaodemo.mvp.home.model.bean.ParticularsBean;

/**
 * Created by Dell on 2018/7/27.
 */

public interface IParticularsView extends IView{
    void onParticularsSuccess(ParticularsBean particularsBean);

    void onAddCartSuccess(AddCartBean addCartBean);

    void onFailed(String error);
}
