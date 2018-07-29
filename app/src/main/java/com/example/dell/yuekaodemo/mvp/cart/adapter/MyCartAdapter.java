package com.example.dell.yuekaodemo.mvp.cart.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dell.yuekaodemo.R;
import com.example.dell.yuekaodemo.mvp.cart.model.bean.CartBean;
import com.example.dell.yuekaodemo.mvp.cart.view.activity.MyAddSubView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 2018/7/27.
 */

public class MyCartAdapter extends BaseExpandableListAdapter {

    private List<CartBean.DataBean> list;

    public MyCartAdapter(List<CartBean.DataBean> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getList() == null ? 0 : list.get(i).getList().size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        CartBean.DataBean parent = list.get(groupPosition);
        ParentViewHolder parentViewHolder;

        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.cart_item_parent, null);
            parentViewHolder = new ParentViewHolder(view);
            view.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) view.getTag();
        }
        parentViewHolder.tvSellerName.setText(parent.getSellerName());
        //根据商品确定商家的checkbox是否被选中
        boolean parentAllSelected = isParentAllSelected(groupPosition);
        parentViewHolder.cbSeller.setChecked(parentAllSelected);
        parentViewHolder.cbSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击商家的checkbox
                if (onCartListChangeListener != null) {
                    onCartListChangeListener.onParentCheckedChange(groupPosition);
                }
            }
        });

        return view;
    }

    //当前的商家所有商品是否被选中
    public boolean isParentAllSelected(int groupPosition) {
        CartBean.DataBean dataBean = list.get(groupPosition);
        List<CartBean.DataBean.ListBean> list1 = dataBean.getList();
        for (CartBean.DataBean.ListBean listBean : list1) {
            if (listBean.getSelected() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        CartBean.DataBean dataBean = list.get(groupPosition);
        List<CartBean.DataBean.ListBean> listBean = dataBean.getList();
        CartBean.DataBean.ListBean child = listBean.get(childPosition);

        ChildViewHolder childViewHolder;

        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.cart_item_child, null);
            childViewHolder = new ChildViewHolder(view);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.tvTitleName.setText(child.getTitle());
        childViewHolder.tvPrice.setText("￥" + child.getBargainPrice());
        String images = child.getImages();
        String[] split = images.split("\\|");
        Uri uri = Uri.parse(split[0]);
        childViewHolder.childImage.setImageURI(uri);
        //商品的checkbox状态
        childViewHolder.childCb.setChecked(child.getSelected() == 1);
        childViewHolder.childCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击商品的checkbox
                if (onCartListChangeListener != null) {
                    onCartListChangeListener.onChildCheckedChange(groupPosition, childPosition);
                }
            }
        });
        //得到商品的数量
        childViewHolder.addRemoveView.setNumber(child.getNum());
        childViewHolder.addRemoveView.setOnNumberChangeListener(new MyAddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int num) {
                //拿到商品最新的数量
                if (onCartListChangeListener != null) {
                    onCartListChangeListener.onNumberChange(groupPosition, childPosition, num);
                }
            }
        });
        return view;
    }

    //所有的商品是否被选中
    public boolean isAllSelected() {
        for (int i = 0; i < list.size(); i++) {
            CartBean.DataBean dataBean = list.get(i);
            List<CartBean.DataBean.ListBean> list = dataBean.getList();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getSelected() == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    //计算总价
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            CartBean.DataBean dataBean = list.get(i);
            List<CartBean.DataBean.ListBean> list = dataBean.getList();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getSelected() == 1) {
                    double bargainPrice = list.get(j).getBargainPrice();
                    int num = list.get(j).getNum();
                    totalPrice += bargainPrice*num;
                }
            }
        }
        return totalPrice;
    }

    //计算数量
    public int calculateTotalNumber() {
        int totalNumber = 0;
        for (int i = 0; i < list.size(); i++) {
            CartBean.DataBean dataBean = list.get(i);
            List<CartBean.DataBean.ListBean> list = dataBean.getList();
            for (int j = 0; j < list.size(); j++) {
                //只要是选中状态
                if (list.get(j).getSelected() == 1) {
                    int num = list.get(j).getNum();
                    totalNumber += num;
                }
            }
        }
        return totalNumber;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //当商家的checkbox被点击的时候调用,设置当前商家的所有商品
    public void changeParentAllStatus(int groupPostition, boolean isSelected) {
        CartBean.DataBean dataBean = list.get(groupPostition);
        List<CartBean.DataBean.ListBean> list2 = dataBean.getList();
        for (int i = 0; i < list2.size(); i++) {
            CartBean.DataBean.ListBean listBean = list2.get(i);
            listBean.setSelected(isSelected ? 1 : 0);
        }
    }

    //改变当前商品状态
    public void changeChildStatus(int groupPosition, int childPosition) {
        CartBean.DataBean dataBean = list.get(groupPosition);
        List<CartBean.DataBean.ListBean> list = dataBean.getList();
        CartBean.DataBean.ListBean listBean = list.get(childPosition);
        listBean.setSelected(listBean.getSelected() == 0 ? 1 : 0);
    }


    //设置所有商品的状态
    public void changeAllStatus(boolean selected) {
        for (int i = 0; i < list.size(); i++) {
            CartBean.DataBean dataBean = list.get(i);
            List<CartBean.DataBean.ListBean> list = dataBean.getList();
            for (int j = 0; j < list.size(); j++) {
                list.get(j).setSelected(selected ? 1 : 0);
            }
        }

    }

    //当加减器被点击得时候调用，改变当前商品得数量
    public void changeNumber(int groupPosition, int childPosition, int number) {
        CartBean.DataBean dataBean = list.get(groupPosition);
        List<CartBean.DataBean.ListBean> list = dataBean.getList();
        CartBean.DataBean.ListBean listBean = list.get(childPosition);
        listBean.setNum(number);
    }

    static class ParentViewHolder {
        @BindView(R.id.cb_seller)
        CheckBox cbSeller;
        @BindView(R.id.tv_seller_name)
        TextView tvSellerName;

        ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.child_cb)
        CheckBox childCb;
        @BindView(R.id.child_image)
        SimpleDraweeView childImage;
        @BindView(R.id.tv_title_name)
        TextView tvTitleName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.add_remove_view)
        MyAddSubView addRemoveView;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    OnCartListChangeListener onCartListChangeListener;

    public void setOnCartListChangeListener(OnCartListChangeListener onCartListChangeListener) {
        this.onCartListChangeListener = onCartListChangeListener;
    }

    public interface OnCartListChangeListener {
        //checkbox点击商家接口回调
        void onParentCheckedChange(int groupPosition);

        //checkbox点击商品接口回调
        void onChildCheckedChange(int groupPosition, int childPosition);

        //加减器接口回调
        void onNumberChange(int groupPosition, int childPosition, int number);
    }
}
