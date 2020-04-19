package com.example.tablehosttest;

import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.tablehosttest.frament.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="MainActivity";

    BottomNavigationView navigationView;

    HomeFragment homeFragment ;
    SearchFragment searchFragment ;
    SelectFragment selectFragment;
    RedPacketFragment redPacketFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        navigationView = findViewById(R.id.main_navigation_bar);
        initFragments();
        initListener();
    }

    private void initFragments() {
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        selectFragment = new SelectFragment();
        redPacketFragment = new RedPacketFragment();
        fm = getSupportFragmentManager();
        switchFragment(homeFragment);
    }

    private void initListener() {
        //组件被聚焦时
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.d(TAG,"title -- >"+menuItem.getTitle()+"id --> "+menuItem.getItemId() );
                if(menuItem.getItemId()==R.id.home){
                    Log.d(TAG,"切换到首页" );
                    switchFragment(homeFragment);
                }else if(menuItem.getItemId()==R.id.search){
                    Log.d(TAG,"切换到搜索" );
                    switchFragment(searchFragment);
                }else if(menuItem.getItemId()==R.id.select){
                    Log.d(TAG,"切换到精选" );
                    switchFragment(selectFragment);
                }else if(menuItem.getItemId()==R.id.red_packet){
                    Log.d(TAG,"切换到特惠" );
                    switchFragment(redPacketFragment);
                }
                return true;
            }
        });
    }

    private void switchFragment(BaseFragment baseFragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_page_container,baseFragment);
        transaction.commit();
    }

}
