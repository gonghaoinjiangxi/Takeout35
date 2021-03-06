package com.heima.takeout35.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.heima.takeout35.R;
import com.heima.takeout35.model.net.GoodsInfo;
import com.heima.takeout35.model.net.Seller;
import com.heima.takeout35.pay.PayResult;
import com.heima.takeout35.pay.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

//    /**商户PID*/
//    public static final String PARTNER = "2088221626451032";
//
//    /**商户收款账号*/
//    public static final String SELLER = "3393900637@qq.com";
//
//    /**商户私钥(PKCS8格式编码)*/
//    public static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMStleK6i5AOGkd5NPUYFlkZJuk56aLhWR8U1ataEGWALOA3d8n3XaIO9tQEzJJUVJIKbCDIR9D1zPS9LPchloqb0OHk9y9I4SJss1ZQZayDWW179ojpOzTqhmto7d566+mebddjRcNiUwD2bDGmcWXigCyAvncHKaW4nuO9iuVfAgMBAAECgYEAu/48PasXycthHR5jGz855VJgWh/sDa+e01HD5vTApXR98Je0XY2fp07sab5omBoZeDqUHkWyN68riGfmuhYV4JyWppub5iayzxHS2N5761PmB+2RxxD6bF7k+cy1qG84sZw0t3qUaChCJiizK5LAd9uNSv50n9Dr7MwHz3OgHcECQQDgofT1SdwQ3WJdXUIkeYhe0LaXbO3hiT5LmfO685TQMov2efYgBjfa22VvFxJd1tei2ml6mYWIx+1zAfe+xwu/AkEA4CRS7xH1tbEJknLVbpWwWjHCaA0bd155y/9/Bkvm0taOtm4pdddm5Mrz+w+dh1mYivWy01c0NLy9I6mmWUUOYQJAWOzEZDYRADwjrII2pOnXqnFFVzywDxCdsKAJdIDo8GKSNciiPps3kVQ5G3kutCdQxg9gokAUNMmwnk6xHLz/UQJBALEXgf8prXz0d5+h40gQNNnOXs9fK8hQeOLY5z/OUH1c0D0LJO7aVY2HXOWMHOaHv6JrJfMc/z57sOSwcaIukYECQQCUhwTfT5hjn1F1yzvCb/l9HugT3qPlgtHCWAtWiPzFS3nbTFmB0szkoW0/ao9uddsMI5MMGN88xy34aN1FPhi5";
//
//    /**支付宝公钥*/
//    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    //切换到总部商户PID
    public static final String PARTNER = "2088011085074233";
    //商户收款账号
    public static final String SELLER = "917356107@qq.com";
    //商户私钥，pkcs8格式
    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL51jaxQhxW9PnWpW+nz6yJ76tp9eGFXmfGnuxMK+Pmx/qavdsewXOLBfI2OSCR39TzxwMYvCmUrnrt0fVSa7mblbNos2FnMM9ijnx8bsAAhm+i7BKhuaHMunJKH69L+D753zH3P1YIh0ly5DnAr3WPqHydp384qBvb8NS9Tay0HAgMBAAECgYB82PIVknP6fCMFXg8yPQJViIVa1ASlSpdPIXQv93FdvKABA+QI4kMBIXRUFoCT506KtK55OzzFNOLIXoQJgcXj69z0l6pmjJJgXMaBW/9rOzelot13CiGatrIrGngEZO+bCBTud/jQA598zjZ1g182tT+FLDL7GIftW2hC8GqtAQJBAN+XrYsyfL+uSmLdAVEz1vzziU1naGr10Msm1jMnnO/JYdB+84j7FSHxsQ4YOgsmeN5YVsJcVfc/CReOxknns38CQQDaEHnVPDt+Z7sqT7bN0UKh0/CrqkDTiIjhz1lJyIIoqVRoeJjJn1wlEKBV5R9gkTJutQTVU19XFtblMEnOy6p5AkEAw170rEmMSa0QoHw+d2bVtydR1QnDapqqO6kOx5oYfkm4J4eWYx4J5CQdMpSmuzF9scL85E3sa+NvnV8LEm7cHwJALtXzFPWG4bNt47yTSslzQka/Hl/G5Kginj1mtA44xnr4AihEyKlNpThY95nqj1cgOd7vVtI9W/sv1LH2aFAeIQJBAIqXbMc6xGVfuiFAJKtg+AFNMBP0UOEgMEoKo4RPFp21nBhFgL9/WYM4ZjyHUdr45rCySAqQovw4DCHLfQZC23I=";
    //支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+dY2sUIcVvT51qVvp8+sie+rafXhhV5nxp7sTCvj5sf6mr3bHsFziwXyNjkgkd/U88cDGLwplK567dH1Umu5m5WzaLNhZzDPYo58fG7AAIZvouwSobmhzLpySh+vS/g++d8x9z9WCIdJcuQ5wK91j6h8nad/OKgb2/DUvU2stBwIDAQAB";


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
                pay();
                break;
        }
    }

    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        //TODO:如何设置订单号
        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    public String sign(String content) {
        return SignUtils.sign(content, PRIVATE_KEY);
    }

    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public void pay() {
        // 订单
        String orderInfo = getOrderInfo(mTvOrderName.getText().toString().trim(), mTvOrderDetail.getText().toString(), "0.02");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OnlinePaymentActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OnlinePaymentActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OnlinePaymentActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OnlinePaymentActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
}
