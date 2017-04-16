package com.heima.takeout35.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.dao.AddressDao;
import com.heima.takeout35.model.dao.RecepitAddress;
import com.heima.takeout35.ui.adapter.AddressRvAdapter;
import com.heima.takeout35.ui.views.RecycleViewDivider;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/16.
 */

public class RecepitAddressActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.rv_receipt_address)
    RecyclerView mRvReceiptAddress;
    @InjectView(R.id.tv_add_address)
    TextView mTvAddAddress;
    private AddressDao mAddressDao;
    private AddressRvAdapter mAddressRvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        ButterKnife.inject(this);
        mAddressDao = new AddressDao(this);
        mRvReceiptAddress.setHasFixedSize(true);
        mRvReceiptAddress.setLayoutManager(new LinearLayoutManager(this));
        mRvReceiptAddress.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL)); //装饰
        mAddressRvAdapter = new AddressRvAdapter(this);
        mRvReceiptAddress.setAdapter(mAddressRvAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //读出所有地址
        List<RecepitAddress> addressList =  mAddressDao.queryAllAddres();
        if(addressList!=null && addressList.size()>0){
            mAddressRvAdapter.setAddressList(addressList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_address:
                //新增地址
                Intent intent = new Intent(this, AddOrEditAddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
