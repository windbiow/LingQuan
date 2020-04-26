package com.example.tablehosttest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * 轮播图适配器
 */
public class BannerPagerAdapter extends PagerAdapter {

    Integer[] banner;

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final int realPosition = position % banner.length;
        ImageView iv = new ImageView(container.getContext());
        iv.setImageResource(banner[realPosition]);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setBanner(Integer[] banner) {
        this.banner=banner;
    }
}
