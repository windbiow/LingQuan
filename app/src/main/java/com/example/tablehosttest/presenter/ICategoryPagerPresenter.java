package com.example.tablehosttest.presenter;

import com.example.tablehosttest.view.ICategoryPagerCallback;

/**
 * 数据请求组件
 * 请求首页分类详细数据
 */
public interface ICategoryPagerPresenter extends BasePresenter<ICategoryPagerCallback>{
    /**
     * 根据分类id获取对应分类内容
     */
    void getContentById(int materialId);

    /**
     * 获取更多信息(下划动作)
     *
     * @param materialId
     */
    void loadMore(int materialId);

    /**
     * 刷新
     */
    void reload();


}