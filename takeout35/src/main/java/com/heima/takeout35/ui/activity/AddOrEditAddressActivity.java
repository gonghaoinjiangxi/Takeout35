package com.heima.takeout35.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heima.takeout35.R;
import com.heima.takeout35.model.dao.AddressDao;
import com.heima.takeout35.model.dao.RecepitAddress;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/16.
 */
public class AddOrEditAddressActivity extends AppCompatActivity {
    private static final int REQUEST_SELECT_ADDRESS = 1003;
    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.ib_delete)
    ImageButton mIbDelete;
    @InjectView(R.id.tv_name)
    TextView mTvName;
    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.rb_man)
    RadioButton mRbMan;
    @InjectView(R.id.rb_women)
    RadioButton mRbWomen;
    @InjectView(R.id.rg_sex)
    RadioGroup mRgSex;
    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.ib_delete_phone)
    ImageButton mIbDeletePhone;
    @InjectView(R.id.ib_add_phone_other)
    ImageButton mIbAddPhoneOther;
    @InjectView(R.id.et_phone_other)
    EditText mEtPhoneOther;
    @InjectView(R.id.ib_delete_phone_other)
    ImageButton mIbDeletePhoneOther;
    @InjectView(R.id.rl_phone_other)
    RelativeLayout mRlPhoneOther;
    @InjectView(R.id.et_receipt_address)
    EditText mEtReceiptAddress;
    @InjectView(R.id.et_detail_address)
    EditText mEtDetailAddress;
    @InjectView(R.id.tv_label)
    TextView mTvLabel;
    @InjectView(R.id.ib_select_label)
    ImageView mIbSelectLabel;
    @InjectView(R.id.bt_ok)
    Button mBtOk;
    @InjectView(R.id.btn_map_location)
    Button mBtnMapLocation;
    private AddressDao mAddressDao;
    private RecepitAddress mAddress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            String title = data.getStringExtra("title");
            String address = data.getStringExtra("address");
            mEtReceiptAddress.setText(title);
            mEtDetailAddress.setText(address);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_receipt_address);
        ButterKnife.inject(this);
        processIntent();
        mAddressDao = new AddressDao(this);

        mEtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //有焦点

                }
            }
        });

        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mIbDeletePhone.setVisibility(View.INVISIBLE);
                } else {
                    mIbDeletePhone.setVisibility(View.VISIBLE);
                }
            }
        });

        mEtPhoneOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mIbDeletePhoneOther.setVisibility(View.INVISIBLE);
                } else {
                    mIbDeletePhoneOther.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void processIntent() {
        if (getIntent() != null) {
            mAddress = (RecepitAddress) getIntent().getSerializableExtra("address");
            if (mAddress != null) {
                //展示删除按钮
                mIbDelete.setVisibility(View.VISIBLE);
                mIbDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAddress();
                    }
                });
                //修改地址
                mTvTitle.setText("修改地址");
                mEtName.setText(mAddress.getName());
                String sex = mAddress.getSex();
                if ("先生".equals(sex)) {
                    mRbMan.setChecked(true);
                } else {
                    mRbWomen.setChecked(true);
                }
                mEtPhone.setText(mAddress.getPhone());
                mEtPhoneOther.setText(mAddress.getPhoneOther());
                mEtReceiptAddress.setText(mAddress.getAddress());
                mEtDetailAddress.setText(mAddress.getDetailAddress());
                mTvLabel.setText(mAddress.getLabel());
            }
        }
    }

    private void deleteAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除这个地址么？");
        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isOk = mAddressDao.deleteAddress(mAddress);
                if (isOk) {
                    Toast.makeText(AddOrEditAddressActivity.this, "删除地址成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddOrEditAddressActivity.this, "删除地址失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("不，保留", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    @OnClick({R.id.btn_map_location,R.id.ib_back, R.id.ib_delete, R.id.ib_delete_phone, R.id.ib_add_phone_other, R.id.ib_delete_phone_other, R.id.ib_select_label, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_map_location:
                //TODO:地图选择地址
                Intent intent = new Intent(this, MapLocationActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_ADDRESS);
                break;
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_delete:
                break;
            case R.id.ib_delete_phone:
                //删除电话
                mEtPhone.setText("");
                break;
            case R.id.ib_add_phone_other:
                //显示备选电话
                mRlPhoneOther.setVisibility(View.VISIBLE);
                break;
            case R.id.ib_delete_phone_other:
                //删除备用电话
                mEtPhoneOther.setText("");
                break;
            case R.id.ib_select_label:
                showSelectLabelDialog();
                break;
            case R.id.bt_ok:
                //线校验数据
                boolean isOk = checkReceiptAddressInfo();
                if (isOk) {
                    if (mAddress != null) {
                        updateAddress();
                    } else {
                        insertAddress();
                    }
                }
                break;
        }
    }

    private void updateAddress() {
        //更新
        String name = mEtName.getText().toString().trim();
        String sex = "女士";
        if (mRbMan.isChecked()) {
            sex = "先生";
        }
        String phone = mEtPhone.getText().toString().trim();
        String phoneOther = mEtPhoneOther.getText().toString().trim();
        String address = mEtReceiptAddress.getText().toString().trim();
        String detailAddress = mEtDetailAddress.getText().toString().trim();
        String label = mTvLabel.getText().toString().trim();
        //把新的数据set到mAddressBean里
        mAddress.setName(name);
        mAddress.setSex(sex);
        mAddress.setPhone(phone);
        mAddress.setPhoneOther(phoneOther);
        mAddress.setAddress(address);
        mAddress.setDetailAddress(detailAddress);
        mAddress.setLabel(label);
        boolean isUpdateSuccess = mAddressDao.updateAddress(mAddress);
        if (isUpdateSuccess) {
            finish();
            Toast.makeText(this, "更新地址成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "更新地址失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertAddress() {
        //保存当前收货地址，sqlite
        String name = mEtName.getText().toString().trim();
        String sex = "女士";
        if (mRbMan.isChecked()) {
            sex = "先生";
        }
        String phone = mEtPhone.getText().toString().trim();
        String phoneOther = mEtPhoneOther.getText().toString().trim();
        String address = mEtReceiptAddress.getText().toString().trim();
        String detailAddress = mEtDetailAddress.getText().toString().trim();
        String label = mTvLabel.getText().toString().trim();
        RecepitAddress recepitAddress = new RecepitAddress(999, name, sex, phone, phoneOther, address, detailAddress, label, "35");
        boolean isInsertSuccess = mAddressDao.insertAddress(recepitAddress);
        if (isInsertSuccess) {
            finish();
            Toast.makeText(this, "新增地址成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "新增地址失败", Toast.LENGTH_SHORT).show();
        }
    }

    String[] items = new String[]{"无", "家", "学校", "公司"};
    String[] colors = new String[]{"#ff4567", "#eeeeaa", "#bb9977", "#aa7563"};

    private void showSelectLabelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择地址标签");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTvLabel.setTextColor(Color.BLACK);
                mTvLabel.setBackgroundColor(Color.parseColor(colors[which]));
                mTvLabel.setText(items[which]);
            }
        });
        builder.show();
    }

    public boolean checkReceiptAddressInfo() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        String receiptAddress = mEtReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        String address = mEtDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isMobileNO(String phone) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return phone.matches(telRegex);
    }
}
