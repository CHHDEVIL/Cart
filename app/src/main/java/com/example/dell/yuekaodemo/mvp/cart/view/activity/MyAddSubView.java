package com.example.dell.yuekaodemo.mvp.cart.view.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yuekaodemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dell on 2018/7/27.
 */

public class MyAddSubView extends LinearLayout {

    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private int number = 1;

    public MyAddSubView(Context context) {
        this(context, null);
    }

    public MyAddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.cart_item_child_adder_subtractor, this);
        ButterKnife.bind(view);
    }

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sub:
                if (number > 1) {
                    --number;
                    tvNumber.setText(number + "");
                    if (onNumberChangeListener != null) {
                        onNumberChangeListener.onNumberChange(number);
                    }
                } else {
                    Toast.makeText(getContext(), "商品最少为一", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_add:
                ++number;
                tvNumber.setText(number + "");
                if (onNumberChangeListener != null) {
                    onNumberChangeListener.onNumberChange(number);
                }
                break;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        tvNumber.setText(number + "");
    }

    OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public interface OnNumberChangeListener {
        void onNumberChange(int num);
    }
}
