package com.example.tablehosttest.presenter.impl;

import android.util.Log;
import com.example.tablehosttest.model.Api;
import com.example.tablehosttest.model.domain.HomePagerContent;
import com.example.tablehosttest.presenter.ICategoryPagerPresenter;
import com.example.tablehosttest.util.RetrofitManager;
import com.example.tablehosttest.util.UrlUtil;
import com.example.tablehosttest.view.ICategoryPagerCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryPagerPresenterImpl implements ICategoryPagerPresenter {

    private List<ICategoryPagerCallback> callbackList = new ArrayList<>();

    private Map<Long,Integer> pagesInfo = new HashMap<>();

    public static final Integer DEFAULT = 1;

    private CategoryPagerPresenterImpl(){}

    private static ICategoryPagerPresenter sInstance = null;

    public static ICategoryPagerPresenter getInstance(){
        if(sInstance == null){
            sInstance = new CategoryPagerPresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void  getContentById(Long materialId) {

        for(ICategoryPagerCallback callback:callbackList){
            if(callback.getMaterialId()==materialId){
                callback.onLoading();
            }
        }

        //加载内容
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Integer targetPage = pagesInfo.get(materialId);
        if(targetPage==null){
            pagesInfo.put(materialId,DEFAULT);
        }
        targetPage = pagesInfo.get(materialId);
        String homePagerUrl = UrlUtil.createHomePagerUrl(materialId, targetPage);
        Log.d(this.toString(),"home pager url -->" +homePagerUrl);
        Call<HomePagerContent> task = api.getHomePagerContent(homePagerUrl);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                Log.d(this.toString(),"code -->" +code);
                if(code == HttpURLConnection.HTTP_OK){
                    HomePagerContent pagerContent = response.body();
                    Log.d("this.toString()","responseBody -->"+ pagerContent.toString());
                    handleHomePageContentResult(pagerContent,materialId);
                }else{
                    //错误处理
                    handleNetWorkError(materialId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                Log.d(this.toString(),"onFailure -->" +t.toString());
                handleNetWorkError(materialId);
            }
        });
    }

    //调用网络问题处理回调函数
    private void handleNetWorkError(Long materialId) {
            for(ICategoryPagerCallback callback:callbackList){
                if(callback.getMaterialId()==materialId){
                    callback.onNetworkError();
                }
            }
    }

    //调用数据接收成功回调函数
    private void handleHomePageContentResult(HomePagerContent pagerContent,Long materialId) {
        boolean b = pagerContent==null||pagerContent.getData().size()==0;
        //通知UI层更新数据
        for(ICategoryPagerCallback callback:callbackList){
            if(callback.getMaterialId()==materialId){
                if(b){
                    callback.onEmpty();
                }else{
                    callback.onContentLoaded(pagerContent.getData());
                }
            }
        }
    }



    @Override
    public void loadMore(Long materialId) {
        for(ICategoryPagerCallback callback:callbackList){
            if(callback.getMaterialId()==materialId){
                callback.OnLoadMoreLoading();
            }
        }
        //加载内容
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Integer targetPage = pagesInfo.get(materialId);
        if(targetPage==null){
            pagesInfo.put(materialId,DEFAULT);
        }
        targetPage = pagesInfo.get(materialId);
        targetPage++;
        String homePagerUrl = UrlUtil.createHomePagerUrl(materialId, targetPage);
        Log.d(this.toString(),"home pager url -->" +homePagerUrl);
        Call<HomePagerContent> task = api.getHomePagerContent(homePagerUrl);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                Log.d(this.toString(),"code -->" +code);
                if(code == HttpURLConnection.HTTP_OK){
                    HomePagerContent pagerContent = response.body();
                    Log.d("this.toString()","responseBody -->"+ pagerContent.toString());
                    handleLoadMoreContentResult(pagerContent,materialId);
                }else{
                    //错误处理
                    handleLoadMoreNetWorkError(materialId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                //错误处理
                handleLoadMoreNetWorkError(materialId);
            }
        });
    }

    //调用网络问题处理回调函数
    private void handleLoadMoreNetWorkError(Long materialId) {
        for(ICategoryPagerCallback callback:callbackList){
            if(callback.getMaterialId()==materialId){
                callback.OnLoadMoreError();
            }
        }
    }

    //调用数据接收成功回调函数
    private void handleLoadMoreContentResult(HomePagerContent pagerContent,Long materialId) {
        pagesInfo.put(materialId,pagesInfo.get(materialId)+1);
        boolean b = pagerContent==null||pagerContent.getData().size()==0;
        //通知UI层更新数据
        for(ICategoryPagerCallback callback:callbackList){
            if(callback.getMaterialId()==materialId){
                if(b){
                    callback.OnLoadMoreEmpty();
                }else{
                    callback.OnLoadMoreLoaded(pagerContent.getData());
                }
            }
        }
    }

    @Override
    public void reload() {

    }

    @Override
    public void registerCallback(ICategoryPagerCallback callback) {
        if(!callbackList.contains(callback)){
            callbackList.add(callback);
        }
    }

    @Override
    public void unregisterCallback() {

    }
}
