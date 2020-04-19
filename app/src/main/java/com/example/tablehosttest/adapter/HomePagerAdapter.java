package com.example.tablehosttest.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import com.example.tablehosttest.frament.HomePagerFragment;
import com.example.tablehosttest.model.domain.Categories;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页顶部导航框适配器
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    List<Categories.DataBean> categories = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        HomePagerFragment homePagerFragment = HomePagerFragment.newInstance(categories.get(position));
        return homePagerFragment;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    public void setCategories(Categories categories) {
        this.categories.clear();
        this.categories.addAll(categories.getData());
        notifyDataSetChanged();
    }
}
