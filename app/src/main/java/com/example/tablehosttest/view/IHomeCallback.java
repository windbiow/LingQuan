package com.example.tablehosttest.view;

import com.example.tablehosttest.model.domain.Categories;

/**
 * 数据加载回调动作
 */
public interface IHomeCallback extends IBaseCallback{
    /**
     * 数据加载成功
     * @param categories
     */
    void onCategoriesLoaded(Categories categories);


}
