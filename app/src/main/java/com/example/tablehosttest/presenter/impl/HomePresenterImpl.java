package com.example.tablehosttest.presenter.impl;

import android.util.Log;
import com.example.tablehosttest.model.Api;
import com.example.tablehosttest.model.domain.Categories;
import com.example.tablehosttest.presenter.IHomePresenter;
import com.example.tablehosttest.util.RetrofitManager;
import com.example.tablehosttest.view.IHomeCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.net.HttpURLConnection;

public class HomePresenterImpl implements IHomePresenter {

    private IHomeCallback callback;

    @Override
    public void getCategories() {
        if(callback!=null){
            callback.onLoading();
        }
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categories> task = api.getCategories();
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                //返回数据
                int code = response.code();
                Log.d(HomePresenterImpl.this.toString(),"result code --> " + code);
                if(code == HttpURLConnection.HTTP_OK){
                    //请求成功
                    Categories category =response.body();
//                    Log.d(HomePresenterImpl.this.toString(),"result body --> \n" + category.getData().toString());
                    if(callback!=null){
                        if(category==null||category.getData().size() == 0){
                            callback.onEmpty();
                        }else{
                            callback.onCategoriesLoaded(category);
                        }
                    }
                }else{
                    //请求失败
                    Log.i(HomePresenterImpl.this.toString(),"请求失败");
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                //失败
                Log.i(HomePresenterImpl.this.toString(),"请求错误");
                callback.onNetworkError();
            }
        });
    }

    @Override
    public void registerCallback(IHomeCallback callback) {
        this.callback=callback;
    }

    @Override
    public void unregisterCallback() {
        this.callback = null;
    }
}
