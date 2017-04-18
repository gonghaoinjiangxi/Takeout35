package com.heima.takeout35.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.net.GoodsInfo;
import com.heima.takeout35.model.net.Seller;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/18.
 */
public class OnlinePaymentActivity extends AppCompatActivity {
    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_residualTime)
    TextView mTvResidualTime;
    @InjectView(R.id.tv_order_name)
    TextView mTvOrderName;
    @InjectView(R.id.tv)
    TextView mTv;
    @InjectView(R.id.tv_order_detail)
    TextView mTvOrderDetail;
    @InjectView(R.id.iv_triangle)
    ImageView mIvTriangle;
    @InjectView(R.id.ll_order_toggle)
    RelativeLayout mLlOrderToggle;
    @InjectView(R.id.tv_receipt_connect_info)
    TextView mTvReceiptConnectInfo;
    @InjectView(R.id.tv_receipt_address_info)
    TextView mTvReceiptAddressInfo;
    @InjectView(R.id.ll_goods)
    LinearLayout mLlGoods;
    @InjectView(R.id.ll_order_detail)
    LinearLayout mLlOrderDetail;
    @InjectView(R.id.tv_pay_money)
    TextView mTvPayMoney;
    @InjectView(R.id.iv_pay_alipay)
    ImageView mIvPayAlipay;
    @InjectView(R.id.cb_pay_alipay)
    CheckBox mCbPayAlipay;
    @InjectView(R.id.tv_selector_other_payment)
    TextView mTvSelectorOtherPayment;
    @InjectView(R.id.ll_hint_info)
    LinearLayout mLlHintInfo;
    @InjectView(R.id.iv_pay_wechat)
    ImageView mIvPayWechat;
    @InjectView(R.id.cb_pay_wechat)
    CheckBox mCbPayWechat;
    @InjectView(R.id.iv_pay_qq)
    ImageView mIvPayQq;
    @InjectView(R.id.cb_pay_qq)
    CheckBox mCbPayQq;
    @InjectView(R.id.iv_pay_fenqile)
    ImageView mIvPayFenqile;
    @InjectView(R.id.cb_pay_fenqile)
    CheckBox mCbPayFenqile;
    @InjectView(R.id.ll_other_payment)
    LinearLayout mLlOtherPayment;
    @InjectView(R.id.bt_confirm_pay)
    Button mBtConfirmPay;
    private float countPrice;
    private Seller mSeller;
    private List<GoodsInfo> mCartList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        ButterKnife.inject(this);
        processIntent();
    }

    private void processIntent() {
        mSeller = (Seller) getIntent().getSerializableExtra("seller");
        mCartList = (List<GoodsInfo>) getIntent().getSerializableExtra("cartList");
        countPrice = getIntent().getFloatExtra("countPrice", 0.01f);
        mTvPayMoney.setText("￥" + 0.01);
        mTvOrderName.setText("来自" + mSeller.getName() + "的订单");
        mTvOrderDetail.setText("您一共订了" + mCartList.size() + "类商品");
        fillGoods();
    }

    private void fillGoods() {
        //ll_goods
        if(mCartList!=null && mCartList.size()>0){
            for(GoodsInfo goodsInfo: mCartList){
                TextView textView = new TextView(this);
                textView.setText(goodsInfo.getName() + "    ￥" + goodsInfo.getNewPrice() + " * " + goodsInfo.getCount());
                mLlGoods.addView(textView);
            }
        }
    }

    boolean isOpened = false;  //默认关闭
    @OnClick({R.id.ib_back, R.id.iv_triangle, R.id.bt_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.iv_triangle:
                //点击三角形展开订单详情，ll_order_detail
                if(isOpened){
                    //关闭它
                    mLlOrderDetail.setVisibility(View.GONE);
                    isOpened = false;
                }else{
                    //展开它
                    mLlOrderDetail.setVisibility(View.VISIBLE);
                    isOpened = true;
                }

                break;
            case R.id.bt_confirm_pay:
                //TODO:付钱

                break;
        }
    }
}
