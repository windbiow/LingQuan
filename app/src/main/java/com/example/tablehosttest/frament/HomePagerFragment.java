package com.example.tablehosttest.frament;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tablehosttest.R;
import com.example.tablehosttest.adapter.HomePageContentAdapter;
import com.example.tablehosttest.model.domain.Categories;
import com.example.tablehosttest.model.domain.HomePagerContent;
import com.example.tablehosttest.presenter.ICategoryPagerPresenter;
import com.example.tablehosttest.presenter.impl.CategoryPagerPresenterImpl;
import com.example.tablehosttest.util.Constants;
import com.example.tablehosttest.view.ICategoryPagerCallback;

import java.util.List;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICategoryPagerPresenter categoryPagerPresenter;
    //本分类页面的分类id
    private int materialId;

    public RecyclerView contentList;

    private HomePageContentAdapter contentAdapter;

    public static HomePagerFragment newInstance(Categories.DataBean category){
        HomePagerFragment homePagerFragment=new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE,category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIALID,category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getRootViewResID() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {

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
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        materialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIALID);
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

    /**
     * 获取当前分类id
     */
    @Override
    public int getMaterialId(){
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
    }

    @Override
    public void onNetworkError() {
        setupState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    @Override
    public void OnLoadMoreError() {

    }

    @Override
    public void OnLoadMoreEmpty() {

    }

    @Override
    public void OnLoadMoreLoading() {

    }

    @Override
    public void OnLoadMoreLoaded(List<Categories.DataBean> contents) {

    }

    @Override
    public void OnLooperListLoaded(List<Categories.DataBean> contents) {

    }
}
