package com.example.tablehosttest.view;

import com.example.tablehosttest.model.domain.Categories;
import com.example.tablehosttest.model.domain.HomePagerContent;

import java.util.List;

/**
 * 数据加载回调动作
 */
public interface ICategoryPagerCallback extends IBaseCallback{

    /**
     * 数据加载成功
     * @param contents
     */
    void onContentLoaded(List<HomePagerContent.DataBean> contents);

    /**
     * 加载更多  内容出错
     */
    void OnLoadMoreError();

    /**
     * 加载更多  内容为空
     */
    void OnLoadMoreEmpty();

    /**
     * 正在加载更多内容中
     */
    void OnLoadMoreLoading();

    /**
     * 加载更多 完成
     * @param contents
     */
    void OnLoadMoreLoaded(List<HomePagerContent.DataBean> contents);

    /**
     * 加载轮播图 完成
     * @param contents
     */
    void OnLooperListLoaded(List<Categories.DataBean> contents);

    /**
     * 获取当前分类id
     */
    Long getMaterialId();

}
