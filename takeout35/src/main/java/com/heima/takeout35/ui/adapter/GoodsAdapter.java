package com.heima.takeout35.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.net.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by lidongzhi on 2017/4/15.
 */

public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private Context mContext;

    public GoodsAdapter(Context context) {
        mContext = context;
    }

    private List<GoodsInfo> mGoodsInfoList = new ArrayList<>();

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);  //馒头
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.item_type_header,parent,false);
        //从商品类别中取出名字
        //TODO:服务器没有对type字段赋值，只能手动赋值
        Log.e("typename", "type:" + goodsInfo.getTypeName());
        ((TextView) headerView).setText(goodsInfo.getTypeName());
        return headerView;
    }

    @Override
    public long getHeaderId(int position) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);  //馒头
        return goodsInfo.getTypeId();
    }


    @Override
    public int getCount() {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(mGoodsInfoList.get(position));
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.iv_icon)
        ImageView mIvIcon;
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_zucheng)
        TextView mTvZucheng;
        @InjectView(R.id.tv_yueshaoshounum)
        TextView mTvYueshaoshounum;
        @InjectView(R.id.tv_newprice)
        TextView mTvNewprice;
        @InjectView(R.id.tv_oldprice)
        TextView mTvOldprice;
        @InjectView(R.id.ib_minus)
        ImageButton mIbMinus;
        @InjectView(R.id.tv_count)
        TextView mTvCount;
        @InjectView(R.id.ib_add)
        ImageButton mIbAdd;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void setData(GoodsInfo goodsInfo) {
            mTvName.setText(goodsInfo.getName());
            //TODO:

        }
    }
}
