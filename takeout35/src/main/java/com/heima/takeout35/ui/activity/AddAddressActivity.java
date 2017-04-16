package com.heima.takeout35.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/16.
 */
public class AddAddressActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_receipt_address);
        ButterKnife.inject(this);

        mEtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
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
                if(TextUtils.isEmpty(s)){
                    mIbDeletePhone.setVisibility(View.INVISIBLE);
                }else{
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
                if(TextUtils.isEmpty(s)){
                    mIbDeletePhoneOther.setVisibility(View.INVISIBLE);
                }else{
                    mIbDeletePhoneOther.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.ib_back, R.id.ib_delete, R.id.ib_delete_phone, R.id.ib_add_phone_other, R.id.ib_delete_phone_other, R.id.ib_select_label, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
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
                if(isOk){
                    //保存当前收货地址，sqlite


                }
                break;
        }
    }

    String[] items = new String[]{"无", "家", "学校", "公司"};
    String[] colors = new String[]{"#ff4567","#eeeeaa","#bb9977","#aa7563"};
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
