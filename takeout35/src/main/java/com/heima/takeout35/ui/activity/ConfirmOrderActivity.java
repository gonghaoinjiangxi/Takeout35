package com.heima.takeout35.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.dao.RecepitAddress;
import com.heima.takeout35.model.net.GoodsInfo;
import com.heima.takeout35.model.net.Seller;
import com.heima.takeout35.utils.PriceFormater;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/18.
 */
public class ConfirmOrderActivity extends AppCompatActivity {

    private static final int SELECT_ADDRESS = 1001;
    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.iv_location)
    ImageView mIvLocation;
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
    @InjectView(R.id.rl_location)
    RelativeLayout mRlLocation;
    @InjectView(R.id.iv_arrow)
    ImageView mIvArrow;
    @InjectView(R.id.iv_icon)
    ImageView mIvIcon;
    @InjectView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @InjectView(R.id.ll_select_goods)
    LinearLayout mLlSelectGoods;
    @InjectView(R.id.tv_deliveryFee)
    TextView mTvDeliveryFee;
    @InjectView(R.id.tv_CountPrice)
    TextView mTvCountPrice;
    @InjectView(R.id.tvSubmit)
    TextView mTvSubmit;
    private Seller mSeller;
    private List<GoodsInfo> mCartList;
    private float mCountPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.inject(this);
        processIntent();
    }

    private void processIntent() {
        if(getIntent()!=null){
            mSeller = (Seller) getIntent().getSerializableExtra("seller");
            mTvSellerName.setText(mSeller.getName());
            mTvDeliveryFee.setText(PriceFormater.format(Float.parseFloat(mSeller.getDeliveryFee())));

            //计算价格
            mCartList = (List<GoodsInfo>) getIntent().getSerializableExtra("cartList");
            //$4
            mCountPrice = Float.parseFloat(mSeller.getDeliveryFee());
            if(mCartList !=null && mCartList.size()>0){
                for(GoodsInfo goodsInfo : mCartList){
                    mCountPrice += goodsInfo.getCount() * goodsInfo.getNewPrice();
                }
            }
            mTvCountPrice.setText("待支付" + PriceFormater.format(mCountPrice));
        }
    }

    @OnClick({R.id.ib_back, R.id.rl_location, R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rl_location:
                //选地址
                Intent addressIntent = new Intent(this, RecepitAddressActivity.class);
                startActivityForResult(addressIntent, SELECT_ADDRESS);
                break;
            case R.id.tvSubmit:
                Intent intent = new Intent(this, OnlinePaymentActivity.class);
                //商家名字，购物车所有商品
                intent.putExtra("seller", mSeller);
                intent.putExtra("cartList", (Serializable) mCartList);
                intent.putExtra("countPrice", mCountPrice);  //增加一个总价
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
           RecepitAddress recepitAddress = (RecepitAddress) data.getSerializableExtra("address");
            mTvName.setText(recepitAddress.getName());
            mTvSex.setText(recepitAddress.getSex());
            mTvAddress.setText(recepitAddress.getAddress());
            mTvPhone.setText(recepitAddress.getPhone());
        }
    }
}
