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

import java.net.URI;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 2018/7/27.
 */

public class MyTuijianAdapter extends RecyclerView.Adapter<MyTuijianAdapter.ViewHolder> {

    private List<HomeBean.DataBean.TuijianBean.ListBeanX> list;

    public MyTuijianAdapter(List<HomeBean.DataBean.TuijianBean.ListBeanX> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_tuijian_recycler, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.homeFenleiTitle.setText(list.get(i).getTitle());
        viewHolder.homeFenleiSubhead.setText(list.get(i).getSubhead());
        viewHolder.homeFenleiPrice.setText("￥"+list.get(i).getPrice());
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        viewHolder.homeFenleiImage.setImageURI(Uri.parse(split[0]));
        viewHolder.homeFenleiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pid = list.get(i).getPid();
                onTuijianClickListener.onClickListener(pid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.home_fenlei_image)
        SimpleDraweeView homeFenleiImage;
        @BindView(R.id.home_fenlei_title)
        TextView homeFenleiTitle;
        @BindView(R.id.home_fenlei_subhead)
        TextView homeFenleiSubhead;
        @BindView(R.id.home_fenlei_price)
        TextView homeFenleiPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    OnTuijianClickListener onTuijianClickListener;

    public void setOnTuijianClickListener(OnTuijianClickListener onTuijianClickListener){
        this.onTuijianClickListener = onTuijianClickListener;
    }

    public interface OnTuijianClickListener{
        void onClickListener(int pid);
    }

}
