package com.example.tablehosttest.frament;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tablehosttest.R;

public abstract class BaseFragment extends Fragment {

    FrameLayout baseContainer;

    View successView;

    View loadingView;

    View errorView;

    View emptyView;

    private State currentState = State.NONE;

    public enum State{
        NONE,LOADING,SUCCESS,ERROR,EMPTY
    }

    protected void retry(){
        //重新加载内容
        Log.d(this.toString(),"重新加载内容");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = loadRootView(inflater,container);
        baseContainer = rootView.findViewById(R.id.base_container);
        loadStatesView(inflater,container);
        initPresenter();
        loadData();
        initView(rootView);
        return rootView;
    }

    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_frame_layout,container,false);
    }

    protected  void loadStatesView(LayoutInflater inflater, ViewGroup container){
        //成功的页面
        successView = loadSuccessView(inflater, container);
        baseContainer.addView(successView);

        //loading的页面
        loadingView = loadLoadingView(inflater, container);
        baseContainer.addView(loadingView);

        //error的页面
        errorView = loadErrorView(inflater, container);
        errorView.findViewById(R.id.network_error_tips).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
            }
        });
        baseContainer.addView(errorView);

        //内容为空的页面
        emptyView = loadEmptyView(inflater, container);
        baseContainer.addView(emptyView);

        setupState(State.NONE);
    }

    private View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.loading_view,container,false);
    }

    private View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.error_view,container,false);
    }

    private View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.empty_view,container,false);
    }

    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        int resID = getRootViewResID();
        return inflater.inflate(resID,container,false);
    }


    protected void setupState(State state){
        this.currentState=state;
        successView.setVisibility(currentState==State.SUCCESS?View.VISIBLE:View.GONE);
        loadingView.setVisibility(currentState==State.LOADING?View.VISIBLE:View.GONE);
        errorView.setVisibility(currentState==State.ERROR?View.VISIBLE:View.GONE);
        emptyView.setVisibility(currentState==State.EMPTY?View.VISIBLE:View.GONE);
    }

    protected  void initView(View rootView){};

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        release();
    }

    //释放资源
    protected  void release(){};

    //创建Presenter
    protected  void initPresenter(){};

    //加载数据
    protected  void loadData(){};

    //选择根视图
    protected abstract int getRootViewResID();

    //初始化监听行为
    protected  void initListener(){};

}
