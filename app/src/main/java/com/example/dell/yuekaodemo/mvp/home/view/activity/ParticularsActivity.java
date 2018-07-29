package com.example.dell.yuekaodemo.mvp.home.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yuekaodemo.R;
import com.example.dell.yuekaodemo.base.BaseActivity;
import com.example.dell.yuekaodemo.mvp.home.model.bean.AddCartBean;
import com.example.dell.yuekaodemo.mvp.home.model.bean.ParticularsBean;
import com.example.dell.yuekaodemo.mvp.home.presenter.ParticularsPresenter;
import com.example.dell.yuekaodemo.mvp.home.view.iview.IParticularsView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Dell on 2018/7/27.
 */

public class ParticularsActivity extends BaseActivity<ParticularsPresenter> implements IParticularsView {


    @BindView(R.id.particulars_back)
    ImageView particularsBack;
    @BindView(R.id.particulars_share)
    ImageView particularsShare;
    @BindView(R.id.particulars_banner)
    Banner particularsBanner;
    @BindView(R.id.particulars_title)
    TextView particularsTitle;
    @BindView(R.id.particulars_subhead)
    TextView particularsSubhead;
    @BindView(R.id.particulars_price)
    TextView particularsPrice;
    @BindView(R.id.particulars_add_car)
    Button particularsAddCar;
    private List<String> images = new ArrayList<>();
    private int pid;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_particulars;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 0);
        presenter.getProductDetail(pid);
    }

    @Override
    protected ParticularsPresenter providePresenter() {
        return new ParticularsPresenter(this);
    }


    @OnClick({R.id.particulars_back, R.id.particulars_add_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.particulars_back:
                finish();
                break;
            case R.id.particulars_add_car:
                presenter.addCart(72,pid);
                break;
        }
    }

    @Override
    public void onParticularsSuccess(ParticularsBean particularsBean) {
        ParticularsBean.DataBean data = particularsBean.getData();
        if (particularsBean.getCode().equals("0")){
            String image = data.getImages();
            String[] split = image.split("\\|");
            for (int i = 0; i < split.length; i++) {
                images.add(split[i]);
            }
            //设置图片加载器
            particularsBanner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            particularsBanner.setImages(images);
            particularsBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
            //banner设置方法全部调用完毕时最后调用
            particularsBanner.start();

            particularsTitle.setText(data.getTitle());
            particularsSubhead.setText(data.getSubhead());
            particularsPrice.setText("￥"+data.getBargainPrice());

            pid = data.getPid();
        }
    }

    @Override
    public void onAddCartSuccess(AddCartBean addCartBean) {
        if (addCartBean.getCode().equals("0")){
            Toast.makeText(this, addCartBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailed(String error) {
        Log.d("TAG", "onFailed: "+error);
    }
    /**
     * 图片加载器
     */
    private class GlideImageLoader  extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(ParticularsActivity.this);
            return simpleDraweeView;
        }
    }

}
