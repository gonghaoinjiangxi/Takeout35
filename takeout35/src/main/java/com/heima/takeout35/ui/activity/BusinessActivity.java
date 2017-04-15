package com.heima.takeout35.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.heima.takeout35.R;
import com.heima.takeout35.model.net.Seller;
import com.heima.takeout35.ui.adapter.BusinessFragmentPagerAdapter;
import com.heima.takeout35.ui.fragment.CommentsFragment;
import com.heima.takeout35.ui.fragment.GoodsFragment;
import com.heima.takeout35.ui.fragment.SellerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/15.
 */
public class BusinessActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.ib_menu)
    ImageButton mIbMenu;
    @InjectView(R.id.vp)
    ViewPager mVp;
    @InjectView(R.id.bottomSheetLayout)
    BottomSheetLayout mBottomSheetLayout;
    @InjectView(R.id.imgCart)
    ImageView mImgCart;
    @InjectView(R.id.tvSelectNum)
    TextView mTvSelectNum;
    @InjectView(R.id.tvCountPrice)
    TextView mTvCountPrice;
    @InjectView(R.id.tvSendPrice)
    TextView mTvSendPrice;
    @InjectView(R.id.tvDeliveryFee)
    TextView mTvDeliveryFee;
    @InjectView(R.id.tvSubmit)
    TextView mTvSubmit;
    @InjectView(R.id.bottom)
    LinearLayout mBottom;
    @InjectView(R.id.fl_Container)
    FrameLayout mFlContainer;
    @InjectView(R.id.tabs)
    TabLayout mTabs;
    private BusinessFragmentPagerAdapter mPagerAdapter;
    public Seller mSeller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.inject(this);
        processIntent();
        initFragments();
    }

    private void processIntent() {
        if(getIntent()!=null){
            mSeller = (Seller) getIntent().getSerializableExtra("seller");
        }
    }

    private List<Fragment> mFragmentList = new ArrayList<>();

    private void initFragments() {
        mFragmentList.add(new GoodsFragment());
        mFragmentList.add(new CommentsFragment());
        mFragmentList.add(new SellerFragment());

        mPagerAdapter = new BusinessFragmentPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setFragmentList(mFragmentList);
        mVp.setAdapter(mPagerAdapter);

        mTabs.setupWithViewPager(mVp);
    }

    @OnClick({R.id.ib_back, R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tvSubmit:
                break;
        }
    }
}
