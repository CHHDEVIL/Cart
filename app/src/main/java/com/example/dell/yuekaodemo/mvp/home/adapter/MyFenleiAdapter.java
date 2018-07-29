package com.example.dell.yuekaodemo.mvp.home.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.yuekaodemo.R;
import com.example.dell.yuekaodemo.mvp.home.model.bean.HomeBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 2018/7/27.
 */

public class MyFenleiAdapter extends RecyclerView.Adapter<MyFenleiAdapter.ViewHolder> {

    private List<HomeBean.DataBean.FenleiBean> list;

    public MyFenleiAdapter(List<HomeBean.DataBean.FenleiBean> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_fenlei_recycler, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.homeFenleiName.setText(list.get(i).getName());
        String icon = list.get(i).getIcon();
        viewHolder.homeFenleiImage.setImageURI(Uri.parse(icon));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_fenlei_image)
        SimpleDraweeView homeFenleiImage;
        @BindView(R.id.home_fenlei_name)
        TextView homeFenleiName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
