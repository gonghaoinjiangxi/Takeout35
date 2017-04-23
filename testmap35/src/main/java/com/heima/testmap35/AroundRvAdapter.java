package com.heima.testmap35;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lidongzhi on 2017/4/19.
 */

public class AroundRvAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public AroundRvAdapter(Context context) {
        mContext = context;
    }

    private List<PoiItem> mPoiItemList = new ArrayList<>();

    public void setPoiItemList(List<PoiItem> poiItemList) {
        mPoiItemList = poiItemList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_around,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mPoiItemList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mPoiItemList!=null){
            return mPoiItemList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvAddress;
        private TextView mTvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvAddress = (TextView) itemView.findViewById(R.id.tv_address);
        }


        public void setData(PoiItem poiItem) {
            mTvTitle.setText(poiItem.getTitle());
            mTvAddress.setText(poiItem.getSnippet()); //摘要 就是描述 该位置的详细信息
        }
    }
}
