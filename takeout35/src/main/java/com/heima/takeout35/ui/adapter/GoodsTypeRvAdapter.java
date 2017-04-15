package com.heima.takeout35.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.net.GoodsTypeInfo;
import com.heima.takeout35.ui.fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2017/4/15.
 */

public class GoodsTypeRvAdapter extends RecyclerView.Adapter {
    private GoodsFragment mGoodsFragment;
    private Context mContext;

    public GoodsTypeRvAdapter(Context context, GoodsFragment goodsFragment) {
        mContext = context;
        this.mGoodsFragment = goodsFragment;
    }

    private List<GoodsTypeInfo> mGoodsTypeInfoList = new ArrayList<>();

    public void setGoodsTypeInfoList(List<GoodsTypeInfo> goodsTypeInfoList) {
        mGoodsTypeInfoList = goodsTypeInfoList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_type, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mGoodsTypeInfoList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(mGoodsTypeInfoList!=null){
            return mGoodsTypeInfoList.size();
        }
        return 0;
    }

    public int selectIndex = 0;
    class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        @InjectView(R.id.tvCount)
        TextView mTvCount;
        @InjectView(R.id.type)
        TextView mType;
        private int mPosition;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            this.mView = view;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectIndex = mPosition;
                    notifyDataSetChanged();

                    //2.右侧列表跳转到index位置,遍历集合，找出下标
                    int typeId = mGoodsTypeInfoList.get(selectIndex).getId();
                    int position = mGoodsFragment.mGoodsFragmentPresenter.getGoodsPositionByTypeId(typeId);
                    //3.listview调用setselection（position)
                    mGoodsFragment.mSlhlv.setSelection(position);
                }
            });
        }

        public void setData(GoodsTypeInfo goodsTypeInfo, int position) {
            this.mPosition = position;
            //1.对不同的position做不同的ui处理
            if(position == selectIndex){
                //白底黑字
                mView.setBackgroundColor(Color.WHITE);
                mType.setTextColor(Color.BLACK);
                mType.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                //灰底灰字 #b9dedcdc
                mView.setBackgroundColor(Color.parseColor("#b9dedcdc"));
                mType.setTextColor(Color.GRAY);
                mType.setTypeface(Typeface.DEFAULT);
            }
            //TODO:问服务器开发人员
//            mTvCount.setText(goodsTypeInfo.get);

            mType.setText(goodsTypeInfo.getName());
        }
    }
}
