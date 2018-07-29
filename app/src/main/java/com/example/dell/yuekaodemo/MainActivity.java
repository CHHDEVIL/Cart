package com.example.dell.yuekaodemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.dell.yuekaodemo.mvp.cart.view.fragment.CartFragment;
import com.example.dell.yuekaodemo.mvp.home.view.fragment.HomeFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.main_bottom_bar)
    BottomBar mainBottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentManager manager1 = getSupportFragmentManager();
        FragmentTransaction transaction1 = manager1.beginTransaction();
        transaction1.replace(R.id.main_frame, new HomeFragment());
        transaction1.commit();

        mainBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.tab_home:
                        FragmentManager manager1 = getSupportFragmentManager();
                        FragmentTransaction transaction1 = manager1.beginTransaction();
                        transaction1.replace(R.id.main_frame, new HomeFragment());
                        transaction1.commit();
                        break;
                    case R.id.tab_cart:
                        FragmentManager manager2 = getSupportFragmentManager();
                        FragmentTransaction transaction2 = manager2.beginTransaction();
                        transaction2.replace(R.id.main_frame, new CartFragment());
                        transaction2.commit();
                        break;
                }
            }
        });



    }
}
