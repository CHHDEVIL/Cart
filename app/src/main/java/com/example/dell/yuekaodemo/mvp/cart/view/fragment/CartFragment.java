package com.example.dell.yuekaodemo.mvp.cart.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.dell.yuekaodemo.R;
import com.example.dell.yuekaodemo.base.BaseFragment;
import com.example.dell.yuekaodemo.mvp.cart.adapter.MyCartAdapter;
import com.example.dell.yuekaodemo.mvp.cart.model.bean.CartBean;
import com.example.dell.yuekaodemo.mvp.cart.presenter.CartPresenter;
import com.example.dell.yuekaodemo.mvp.cart.view.iview.ICartView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Dell on 2018/7/26.
 */

public class CartFragment extends BaseFragment<CartPresenter> implements ICartView {

    @BindView(R.id.cart_expandable)
    ExpandableListView cartExpandable;
    @BindView(R.id.cb_cart_all_select)
    CheckBox cbCartAllSelect;
    @BindView(R.id.tv_cart_total_price)
    TextView tvCartTotalPrice;
    @BindView(R.id.btn_cart_pay)
    Button btnCartPay;
    Unbinder unbinder;
    private MyCartAdapter myCartAdapter;

    @Override
    protected int provideLayoutId() {
        return R.layout.cart_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        presenter.getCart(16456);
    }

    @Override
    protected CartPresenter providePresenter() {
        return new CartPresenter(this);
    }


    @Override
    public void onCartSuccess(CartBean cartBean) {
        List<CartBean.DataBean> data = cartBean.getData();

        Log.d("TAG", "onCartSuccess: " + cartBean.getMsg());
        myCartAdapter = new MyCartAdapter(data);
        refreshSelectedAndTotalPriceAndTotalNumber();
        myCartAdapter.setOnCartListChangeListener(new MyCartAdapter.OnCartListChangeListener() {
            @Override
            public void onParentCheckedChange(int groupPosition) {
                //商家被点击
                boolean parentAllSelected = myCartAdapter.isParentAllSelected(groupPosition);
                myCartAdapter.changeParentAllStatus(groupPosition,!parentAllSelected);
                myCartAdapter.notifyDataSetChanged();

                refreshSelectedAndTotalPriceAndTotalNumber();

            }

            @Override
            public void onChildCheckedChange(int groupPosition, int childPosition) {
                //点击商品得到checkbox
                myCartAdapter.changeChildStatus(groupPosition,childPosition);
                myCartAdapter.notifyDataSetChanged();

                refreshSelectedAndTotalPriceAndTotalNumber();

            }

            @Override
            public void onNumberChange(int groupPosition, int childPosition, int number) {
                //当加减被点击
                myCartAdapter.changeNumber(groupPosition, childPosition, number);
                myCartAdapter.notifyDataSetChanged();

                refreshSelectedAndTotalPriceAndTotalNumber();

                //gengxin


            }

        });
        cartExpandable.setAdapter(myCartAdapter);
        //展开二级列表
        for (int i = 0; i < data.size(); i++) {
            cartExpandable.setGroupIndicator(null);
            cartExpandable.expandGroup(i);
        }
    }

    @Override
    public void onFailed(String error) {
        Log.d("tag", "onCartFailed: " + error);
    }

    //刷新checkbox状态和总价和总数量
    private void refreshSelectedAndTotalPriceAndTotalNumber(){
        //去判断是否所有得商品都被选中
        boolean allSelected = myCartAdapter.isAllSelected();
        //设置给全选checkbox
        cbCartAllSelect.setChecked(allSelected);

        //计算总价
        double totalPrice = myCartAdapter.calculateTotalPrice();
        tvCartTotalPrice.setText("总价"+totalPrice);

        //计算数量
        int totalNumber = myCartAdapter.calculateTotalNumber();
        btnCartPay.setText("去结算(" + totalNumber + ")");

    }

    @OnClick(R.id.cb_cart_all_select)
    public void onViewClicked() {
        boolean allSelected = myCartAdapter.isAllSelected();
        myCartAdapter.changeAllStatus(!allSelected);
        myCartAdapter.notifyDataSetChanged();

        //刷新底部数据
        refreshSelectedAndTotalPriceAndTotalNumber();
    }
}
