package com.heima.takeout35.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.ui.activity.LoginActivity;
import com.heima.takeout35.utils.TakeoutApp;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/12.
 */

public class UserFragment extends Fragment {

    @InjectView(R.id.tv_user_setting)
    ImageView mTvUserSetting;
    @InjectView(R.id.iv_user_notice)
    ImageView mIvUserNotice;
    @InjectView(R.id.login)
    ImageView mLogin;
    @InjectView(R.id.username)
    TextView mUsername;
    @InjectView(R.id.phone)
    TextView mPhone;
    @InjectView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
    @InjectView(R.id.iv_address_manager)
    ImageView mIvAddressManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.fragment_user, null);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //登录成功后更新Ui
        int userId = TakeoutApp.sUser.getId();
        if(userId!=-1){
            mLogin.setVisibility(View.GONE);
            mLlUserinfo.setVisibility(View.VISIBLE);
            //展示用户名和电话号码
            mUsername.setText("欢迎你，" + TakeoutApp.sUser.getName());
            mPhone.setText(TakeoutApp.sUser.getPhone());
        }else{
            mLogin.setVisibility(View.VISIBLE);
            mLlUserinfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.login)
    public void onClick() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getContext().startActivity(intent);
    }
}
