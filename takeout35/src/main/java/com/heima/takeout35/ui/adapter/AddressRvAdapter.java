package com.heima.takeout35.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.dao.RecepitAddress;
import com.heima.takeout35.ui.activity.AddOrEditAddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/16.
 */

public class AddressRvAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public AddressRvAdapter(Context context) {
        mContext = context;
    }

    private List<RecepitAddress> mAddressList = new ArrayList<>();

    public void setAddressList(List<RecepitAddress> addressList) {
        mAddressList = addressList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_receipt_address, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mAddressList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mAddressList!=null){
            return mAddressList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_sex)
        TextView mTvSex;
        @InjectView(R.id.tv_phone)
        TextView mTvPhone;
        @InjectView(R.id.tv_label)
        TextView mTvLabel;
        @InjectView(R.id.tv_address)
        TextView mTvAddress;
        @InjectView(R.id.iv_edit)
        ImageView mIvEdit;
        private RecepitAddress mAddress;

        @OnClick({R.id.iv_edit})
        public void editAddress(View view){
            Intent intent = new Intent(mContext, AddOrEditAddressActivity.class);
            //修改地址，所以要带历史资料
            intent.putExtra("address", mAddress);
            mContext.startActivity(intent);
        }

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //把地址返回给确认订单
                    Intent data = new Intent();
                    data.putExtra("address", mAddress);
                    ((Activity) mContext).setResult(200,data);
                    ((Activity) mContext).finish();
                }
            });
        }

        public void setData(RecepitAddress address) {
            this.mAddress = address;
            mTvName.setText(address.getName());
            mTvSex.setText(address.getSex());
            mTvAddress.setText(address.getAddress() + "," + address.getDetailAddress());
            mTvPhone.setText(address.getPhone() + "," + address.getPhoneOther());
            mTvLabel.setText(address.getLabel());
            mTvLabel.setTextColor(Color.BLACK);
        }
    }
}
