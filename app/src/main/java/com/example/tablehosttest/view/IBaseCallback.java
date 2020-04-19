package com.example.tablehosttest.view;

public interface IBaseCallback {
    /**
     * 数据正在加载
     */
    void onLoading();

    /**
     * 数据加载出错
     */
    void onNetworkError();

    /**
     * 数据内容为空
     */
    void onEmpty();

}
