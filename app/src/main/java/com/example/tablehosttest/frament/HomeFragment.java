package com.example.tablehosttest.frament;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.ViewPager;
import com.example.tablehosttest.R;
import com.example.tablehosttest.adapter.HomePagerAdapter;
import com.example.tablehosttest.model.domain.Categories;
import com.example.tablehosttest.presenter.impl.HomePresenterImpl;
import com.example.tablehosttest.view.IHomeCallback;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class HomeFragment extends BaseFragment implements IHomeCallback {

    TabLayout tabLayout;

    ViewPager viewPager;

    HomePagerAdapter homePagerAdapter;

    HomePresenterImpl homePresenter;


    @Override
    protected int getRootViewResID() {
        return R.layout.frament_home;
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_home_frame_layout,container,false);
    }

    @Override
    protected void initPresenter() {
        homePresenter = new HomePresenterImpl();
        homePresenter.registerCallback(this);
    }

    @Override
    protected void initView(View rootView) {
        tabLayout = rootView.findViewById(R.id.home_indicator);
        viewPager = rootView.findViewById(R.id.home_pager);
        tabLayout.setupWithViewPager(viewPager);
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
    }

    @Override
    protected void loadData() {
        homePresenter.getCategories();
    }

    @Override
    protected void release() {
        if(homePresenter!=null){
            homePresenter.unregisterCallback();
        }
    }

    @Override
    protected void retry() {
        if(homePresenter!=null){
            homePresenter.getCategories();
        }
    }

    @Override
    public void onCategoriesLoaded(Categories categories) {
            setupState(State.SUCCESS);
         //从这里得到加载的数据
        if(homePagerAdapter!=null) {
            homePagerAdapter.setCategories(categories);
        }
    }

    @Override
    public void onNetworkError() {
        setupState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }
}
