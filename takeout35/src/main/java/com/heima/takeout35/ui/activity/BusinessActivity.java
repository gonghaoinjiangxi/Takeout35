package com.heima.takeout35.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.heima.takeout35.R;
import com.heima.takeout35.model.net.GoodsInfo;
import com.heima.takeout35.model.net.GoodsTypeInfo;
import com.heima.takeout35.model.net.Seller;
import com.heima.takeout35.ui.adapter.BusinessFragmentPagerAdapter;
import com.heima.takeout35.ui.adapter.CartRvAdapter;
import com.heima.takeout35.ui.fragment.CommentsFragment;
import com.heima.takeout35.ui.fragment.GoodsFragment;
import com.heima.takeout35.ui.fragment.SellerFragment;
import com.heima.takeout35.utils.PriceFormater;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.heima.takeout35.R.id.bottomSheetLayout;

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
    @InjectView(bottomSheetLayout)
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
    private View bottomSheetView;
    private RecyclerView mRvCart;
    private CartRvAdapter mCartRvAdapter;

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
            mTvDeliveryFee.setText("另需配送费￥" + mSeller.getDeliveryFee());
            mTvSendPrice.setText("￥" + mSeller.getSendPrice() + "元起送");
        }
    }

    public List<Fragment> mFragmentList = new ArrayList<>();

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

            case R.id.bottom:
                //点击底部按钮
                showOrCloseCart();
                break;
        }
    }

    public void showOrCloseCart() {
            if (bottomSheetView == null) {
                //加载要显示的布局
                bottomSheetView = LayoutInflater.from(this).inflate(R.layout.cart_list, (ViewGroup) getWindow().getDecorView(), false);
                mRvCart = (RecyclerView) bottomSheetView.findViewById(R.id.rvCart);
                TextView tvClear = (TextView) bottomSheetView.findViewById(R.id.tvClear);
                tvClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearCart();
                    }
                });
                mRvCart.setHasFixedSize(true);
                mRvCart.setLayoutManager(new LinearLayoutManager(this));
                mCartRvAdapter = new CartRvAdapter(this);
                mRvCart.setAdapter(mCartRvAdapter);
            }
            //判断BottomSheetLayout内容是否显示
            if (mBottomSheetLayout.isSheetShowing()) {
                //关闭内容显示
                mBottomSheetLayout.dismissSheet();
            } else {
                //显示BottomSheetLayout里面的内容
                //要给mCartRvAdapter设置数据
                GoodsFragment goodsFragment = (GoodsFragment) mFragmentList.get(0);
                List<GoodsInfo> cartList = goodsFragment.mGoodsFragmentPresenter.getCartList();
                if(cartList!=null && cartList.size() > 0) {
                    mCartRvAdapter.setCartList(cartList);
                    mBottomSheetLayout.showWithSheetView(bottomSheetView);
                }
            }
    }
//
//    private void processRedDot(boolean isAdd) {
//        //点击增加或者减少馒头
//        int typeId = mGoodsInfo.getTypeId(); //粗粮主食
//        //2.根据粗粮主食id找到它的排序position
//        int position = mGoodsFragment.mGoodsFragmentPresenter.getTypePositionByTypeId(typeId);
//        //3.从左侧列表中找到第postion个GoodstypeInfo
//        GoodsTypeInfo goodsTypeInfo = mGoodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfoList.get(position);
//        int redDotCount = goodsTypeInfo.getCount();
//        if(isAdd){
//            redDotCount++;
//        }else{
//            redDotCount --;
//        }
//        goodsTypeInfo.setCount(redDotCount);
//        //4.刷新左侧列表
//        mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();
//    }

    private void clearCart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认不吃了么？");
        builder.setPositiveButton("是，我要减肥", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GoodsFragment goodsFragment = (GoodsFragment) mFragmentList.get(0);
                goodsFragment.mGoodsFragmentPresenter.clearCart();
                //分别刷新 购物车内部、左右侧列表、下方购物栏
                mCartRvAdapter.notifyDataSetChanged();

                //TODO:左侧红点值需要手动清空（计算出来的)
                //循环左侧列表，清空全部类别的红点数
                List<GoodsTypeInfo> goodsTypeInfoList = goodsFragment.mGoodsTypeRvAdapter.getGoodsTypeInfoList();
                if(goodsTypeInfoList!=null && goodsTypeInfoList.size()>0){
                    for(GoodsTypeInfo goodsTypeInfo : goodsTypeInfoList){
                        goodsTypeInfo.setCount(0);
                    }
                }
                goodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();

                goodsFragment.mGoodsAdapter.notifyDataSetChanged();

                updateCartUi(goodsFragment.mGoodsFragmentPresenter.getCartList());

                //关闭购物车
                showOrCloseCart();
            }
        });
        builder.setNegativeButton("不，还要吃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void addImageButton(ImageButton ib, int width, int height) {
        mFlContainer.addView(ib,width,height);
    }

    public int[] getCartLocation() {
        int[] destLocation =  new int[2];
        mImgCart.getLocationInWindow(destLocation);
        return destLocation;
    }

    public void updateCartUi(List<GoodsInfo> cartList) {
        int count = 0;
        float countPrice = 0.0f;
        for(GoodsInfo goodsInfo :cartList){
            count += goodsInfo.getCount();
            countPrice += goodsInfo.getCount() * goodsInfo.getNewPrice();
        }
        if(count >0){
            mTvSelectNum.setVisibility(VISIBLE);

        }else{
            mTvSelectNum.setVisibility(GONE);
        }
        mTvSelectNum.setText(count + "");
        mTvCountPrice.setText(PriceFormater.format(countPrice));

        //处理配送（当价格超出起送价格，显示下单按钮）
        if(countPrice > Float.parseFloat(mSeller.getSendPrice())){
            //显示下单按钮
            mTvSubmit.setVisibility(VISIBLE);
            mTvSendPrice.setVisibility(GONE);
        }else{
            mTvSubmit.setVisibility(GONE);
            mTvSendPrice.setVisibility(VISIBLE);
        }
    }
}
