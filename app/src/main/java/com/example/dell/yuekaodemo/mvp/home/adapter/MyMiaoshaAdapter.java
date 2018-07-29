package com.example.dell.yuekaodemo.mvp.home.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.yuekaodemo.R;
import com.example.dell.yuekaodemo.mvp.home.model.bean.HomeBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 2018/7/27.
 */

public class MyMiaoshaAdapter extends RecyclerView.Adapter<MyMiaoshaAdapter.ViewHolder> {

    private List<HomeBean.DataBean.MiaoshaBean.ListBean> list;

    public MyMiaoshaAdapter(List<HomeBean.DataBean.MiaoshaBean.ListBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_miaosha_recycler, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        viewHolder.homeMiaoshaImage.setImageURI(Uri.parse(split[0]));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_miaosha_image)
        SimpleDraweeView homeMiaoshaImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
