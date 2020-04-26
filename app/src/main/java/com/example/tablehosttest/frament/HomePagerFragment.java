package com.example.tablehosttest.frament;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.tablehosttest.R;
import com.example.tablehosttest.adapter.BannerPagerAdapter;
import com.example.tablehosttest.adapter.HomePageContentAdapter;
import com.example.tablehosttest.model.domain.Categories;
import com.example.tablehosttest.model.domain.HomePagerContent;
import com.example.tablehosttest.pager.MyBannerPager;
import com.example.tablehosttest.presenter.ICategoryPagerPresenter;
import com.example.tablehosttest.presenter.impl.CategoryPagerPresenterImpl;
import com.example.tablehosttest.util.Constants;
import com.example.tablehosttest.util.SizeUtils;
import com.example.tablehosttest.util.ToastUtils;
import com.example.tablehosttest.view.ICategoryPagerCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICategoryPagerPresenter categoryPagerPresenter;
    //本分类页面的分类id
    private Long materialId;

    public RecyclerView contentList;

    private HomePageContentAdapter contentAdapter;
    private MyBannerPager bannerView;
    private BannerPagerAdapter bannerAdapter;
    private LinearLayout banner_point_container;
    private TwinklingRefreshLayout refreshLayout;

    public static HomePagerFragment newInstance(Categories.DataBean category){
        HomePagerFragment homePagerFragment=new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE,category.getTitle());
        bundle.putLong(Constants.KEY_HOME_PAGER_MATERIALID,category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getRootViewResID() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {

        //获取RecycleView对象
        contentList = rootView.findViewById(R.id.home_pager_content_list);
        //设置布局管理器
        contentList.setLayoutManager(new LinearLayoutManager(getContext()));
        contentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom = 5;
            }
        });
        //创建适配器
        contentAdapter = new HomePageContentAdapter();
        //设置适配器
        contentList.setAdapter(contentAdapter);

        //创建轮播图对象
        bannerView = rootView.findViewById(R.id.banner_pager);
        banner_point_container =rootView.findViewById(R.id.banner_point_container);
        initBanner();


        //创建刷新视图对象
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableRefresh(false);

        initListener();
    }

     protected void initListener() {

        bannerView.setPageItemClickListener(new MyBannerPager.OnPageItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //TODO: 点击事件
            }
        });

        bannerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int targetPosition = position % banner.length;
                updateLooperIndicator(targetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //--------------------------------------------------------------------------------------------
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //TODO:获取更多
                if(null != categoryPagerPresenter) {
                    categoryPagerPresenter.loadMore(materialId);
                }
            }
        });
    }

    //轮播图数据
    private  Integer[] banner={R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3};

    private void initBanner() {
        bannerAdapter = new BannerPagerAdapter();
        bannerAdapter.setBanner(banner);

        bannerView.setAdapter(bannerAdapter);

        //设置轮播图起点
        int targetPosition = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % banner.length);
        bannerView.setCurrentItem(targetPosition);

        for(int i = 0;i<banner.length;i++){
            View point = new View(this.getContext());
            int size = SizeUtils.dip2px(this.getContext(),8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size,size);
            layoutParams.leftMargin =  SizeUtils.dip2px(this.getContext(),5);
            layoutParams.rightMargin =  SizeUtils.dip2px(this.getContext(),5);
            point.setLayoutParams(layoutParams);
            if(0 == i){
                point.setBackgroundResource(R.drawable.shape_indicator_point);
            }else{
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }

            banner_point_container.addView(point);
        }
    }

    /**
     * 切换指示器样式
     * @param targetPosition
     */
    private void updateLooperIndicator(int targetPosition) {
        for(int i = 0 ; i < banner_point_container.getChildCount();i++){
            View childAt = banner_point_container.getChildAt(i);
            if(targetPosition == i){
                childAt.setBackgroundResource(R.drawable.shape_indicator_point);
            }else{
                childAt.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
        }
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        materialId = arguments.getLong(Constants.KEY_HOME_PAGER_MATERIALID);
        String  title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        //TODO 加载数据
       if(null != categoryPagerPresenter) {
           categoryPagerPresenter.getContentById(materialId);
       }
    }

    @Override
    protected void initPresenter() {
        categoryPagerPresenter = CategoryPagerPresenterImpl.getInstance();
        categoryPagerPresenter.registerCallback(this);
    }

    @Override
    protected void release() {
        if(categoryPagerPresenter!=null){
            categoryPagerPresenter.unregisterCallback();
        }
    }

    @Override
    protected void retry() {
        if(categoryPagerPresenter!=null){
            categoryPagerPresenter.getContentById(materialId);
        }
    }

    /**
     * 获取当前分类id
     */
    @Override
    public Long getMaterialId(){
        return materialId;
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean> contents) {
        contentAdapter.setData(contents);
        setupState(State.SUCCESS);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
        ToastUtils.showToast("数据正在加载");
    }

    @Override
    public void onNetworkError() {
        setupState(State.ERROR);
        ToastUtils.showToast("获取数据出错");
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    @Override
    public void OnLoadMoreError() {
        ToastUtils.showToast("获取数据出错");
        if(refreshLayout!=null){
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void OnLoadMoreEmpty() {
        ToastUtils.showToast("没有更多数据");
    }

    @Override
    public void OnLoadMoreLoading() {
        ToastUtils.showToast("更多数据正在加载");
    }

    @Override
    public void OnLoadMoreLoaded(List<HomePagerContent.DataBean> contents) {
        contentAdapter.setMoreData(contents);
        ToastUtils.showToast("加载成功");
        Log.d("sss","获取数据-->" + contents.size());
        if(refreshLayout!=null){
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void OnLooperListLoaded(List<Categories.DataBean> contents) {

    }
}
