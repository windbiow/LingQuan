package com.example.tablehosttest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tablehosttest.R;
import com.example.tablehosttest.model.domain.Categories;
import com.example.tablehosttest.model.domain.HomePagerContent;
import com.example.tablehosttest.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页分类详细内容 RecyclerView 适配器
 */
public class HomePageContentAdapter extends RecyclerView.Adapter<HomePageContentAdapter.InnerHolder> {

    List<HomePagerContent.DataBean> contents = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(contents.get(position));
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public void setData(List<HomePagerContent.DataBean> contents) {
        this.contents.clear();
        this.contents.addAll(contents);
        notifyDataSetChanged();
    }

    public void setMoreData(List<HomePagerContent.DataBean> contents) {
        int size = this.contents.size();
        this.contents.addAll(contents);
        notifyItemRangeChanged(size,contents.size());
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        TextView goods_title;
        TextView goods_off_price;
        TextView goods_real_price;
        TextView goods_previous_price;
        TextView goods_buy_number;
        ImageView goods_cover;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
             goods_title = itemView.findViewById(R.id.goods_title);
             goods_off_price = itemView.findViewById(R.id.goods_off_price);
             goods_real_price = itemView.findViewById(R.id.goods_real_price);
             goods_previous_price = itemView.findViewById(R.id.goods_previous_price);
             goods_buy_number = itemView.findViewById(R.id.goods_buy_number);
             goods_cover = itemView.findViewById(R.id.goods_cover);

        }

        protected void setData(HomePagerContent.DataBean data){
            goods_title.setText(data.getTitle());
            goods_off_price.setText(data.getShop_title());
            goods_real_price.setText(data.getZk_final_price());
//            goods_previous_price.setText(data.getCoupon_amount());
            goods_buy_number.setText("已有"+data.getVolume()+"人购买");
            Glide.with(itemView.getContext()).load(UrlUtil.getCoverPath(data.getPict_url())).into(goods_cover);
        }
    }
}
