package com.heima.takeout35.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.presenter.LoginActivityPresenter;
import com.heima.takeout35.utils.SMSUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by lidongzhi on 2017/4/13.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int TIME_MINUS = -1;
    private static final int TIME_IS_OUT = -2;
    @InjectView(R.id.iv_user_back)
    ImageView mIvUserBack;
    @InjectView(R.id.iv_user_password_login)
    TextView mIvUserPasswordLogin;
    @InjectView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @InjectView(R.id.tv_user_code)
    TextView mTvUserCode;
    @InjectView(R.id.et_user_code)
    EditText mEtUserCode;
    @InjectView(R.id.login)
   TextView mLogin;

    EventHandler  mEventHandler = new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //2.提交验证码成功
                    Log.e("sms","提交验证码成功");
                    //TODO:登录自己的服务器，完成登录业务
//                    mLoginActivityPresenter.loginByPhone(mPhone, -2);

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //1.获取验证码成功
                    Log.e("sms","获取验证码成功");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表

                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };
    private LoginActivityPresenter mLoginActivityPresenter;
    private String mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mLoginActivityPresenter = new LoginActivityPresenter(this);

        SMSSDK.initSDK(this, "1cfd8fdbf1d00", "f35256dbe2e17fa453af8738b3d6534f");  //短信验证

        SMSSDK.registerEventHandler(mEventHandler); //注册短信回调
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mEventHandler!=null){
            SMSSDK.unregisterEventHandler(mEventHandler); //注册短信回调
        }
    }

    @OnClick({R.id.tv_user_code, R.id.login})
    public void onClick(View view) {
        mPhone = mEtUserPhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_user_code:
                //1.获取验证码
                if(SMSUtil.judgePhoneNums(LoginActivity.this, mPhone)) {
                    SMSSDK.getVerificationCode("86", mPhone);
                    //2.开启子线程，倒计时
                    mTvUserCode.setText("剩余时间(" + time + ")秒");
                    mTvUserCode.setEnabled(false);
                    new Thread(new CutdownTask()) {
                    }.start();
                }
                break;
            case R.id.login:
                //2.提交验证码
//                if(SMSUtil.judgePhoneNums(LoginActivity.this, mPhone)) {
//                    String code = mEtUserCode.getText().toString().trim();
//                    SMSSDK.submitVerificationCode("86", mPhone, code);
//                }
                mLoginActivityPresenter.loginByPhone(mPhone, -2);
                break;
        }
    }

    int time = 60;
    private class CutdownTask implements Runnable{
        @Override
        public void run() {
            for(;time>0;time--){
                try {
                    Thread.sleep(999);
                    mHandler.sendEmptyMessage(TIME_MINUS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mHandler.sendEmptyMessage(TIME_IS_OUT);
        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TIME_MINUS:
                    mTvUserCode.setText("剩余时间(" + time + ")秒");
                    break;
                case TIME_IS_OUT:
                    mTvUserCode.setEnabled(true);
                    mTvUserCode.setText("重新发送");
                    time = 60;
                    break;
            }
        }
    };
}
