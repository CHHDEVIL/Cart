package com.example.dell.yuekaodemo.mvp.home.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dell.yuekaodemo.R;
import com.example.dell.yuekaodemo.base.BaseFragment;
import com.example.dell.yuekaodemo.mvp.home.adapter.MyFenleiAdapter;
import com.example.dell.yuekaodemo.mvp.home.adapter.MyMiaoshaAdapter;
import com.example.dell.yuekaodemo.mvp.home.adapter.MyTuijianAdapter;
import com.example.dell.yuekaodemo.mvp.home.model.bean.HomeBean;
import com.example.dell.yuekaodemo.mvp.home.presenter.HomePresenter;
import com.example.dell.yuekaodemo.mvp.home.view.activity.ParticularsActivity;
import com.example.dell.yuekaodemo.mvp.home.view.iview.IHomeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Dell on 2018/7/26.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView {

    @BindView(R.id.home_banner)
    Banner homeBanner;
    @BindView(R.id.home_fenlei)
    RecyclerView homeFenlei;
    @BindView(R.id.home_miaosha)
    RecyclerView homeMiaosha;
    @BindView(R.id.home_tuijian)
    RecyclerView homeTuijian;
    private List<String> images = new ArrayList<>();


    @Override
    protected int provideLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        presenter.getHome();
    }

    @Override
    protected HomePresenter providePresenter() {
        return new HomePresenter(this);
    }


    @Override
    public void onHomeSuccess(HomeBean homeBean) {
        if (homeBean.getCode().equals("0")){
            HomeBean.DataBean data = homeBean.getData();
            List<HomeBean.DataBean.BannerBean> banner = data.getBanner();
            for (int i = 0; i < banner.size(); i++) {
                String icon = banner.get(i).getIcon();
                images.add(icon);
            }
            //设置图片加载器
            homeBanner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            homeBanner.setImages(images);
            //设置指示器样式
            homeBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
            //设置方法全部调用完毕时最后调用
            homeBanner.start();
            //*****************************分类******************************
            List<HomeBean.DataBean.FenleiBean> fenlei = data.getFenlei();
            MyFenleiAdapter myFenleiAdapter = new MyFenleiAdapter(fenlei);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
            gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            homeFenlei.setLayoutManager(gridLayoutManager);
            homeFenlei.setAdapter(myFenleiAdapter);
            //*****************************秒杀*******************************
            List<HomeBean.DataBean.MiaoshaBean.ListBean> list = data.getMiaosha().getList();
            MyMiaoshaAdapter myMiaoshaAdapter = new MyMiaoshaAdapter(list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
            homeMiaosha.setLayoutManager(linearLayoutManager);
            homeMiaosha.setAdapter(myMiaoshaAdapter);
            //***************************为您推荐*******************************
            List<HomeBean.DataBean.TuijianBean.ListBeanX> tuijian = data.getTuijian().getList();
            MyTuijianAdapter myTuijianAdapter = new MyTuijianAdapter(tuijian);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 2) {
                @Override
                public boolean canScrollVertically() {
                    return false;//禁止滑动
                }
            };
            homeTuijian.setLayoutManager(manager);
            homeTuijian.setAdapter(myTuijianAdapter);
            myTuijianAdapter.setOnTuijianClickListener(new MyTuijianAdapter.OnTuijianClickListener() {
                @Override
                public void onClickListener(int pid) {
                    Intent intent = new Intent(getContext(), ParticularsActivity.class);
                    intent.putExtra("pid",pid);
                    startActivity(intent);
                }
            });


        }

    }

    @Override
    public void onFailed(String error) {
        Log.d("TAG", "onFailed: "+error);
    }

    /**
     * 图片加载器
     */
    private class GlideImageLoader  extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());
            return simpleDraweeView;
        }
    }



}
