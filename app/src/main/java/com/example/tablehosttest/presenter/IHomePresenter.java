package com.example.tablehosttest.presenter;

import com.example.tablehosttest.view.IHomeCallback;

/**
 * 数据请求组件
 * 请求首页分类tab信息
 */
public interface IHomePresenter extends BasePresenter<IHomeCallback>{
    /**
     * 获取商品分类
     */
    void getCategories();
}
