package com.heima.takeout35.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.net.GoodsInfo;
import com.heima.takeout35.utils.PriceFormater;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by lidongzhi on 2017/4/15.
 */

public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private Context mContext;

    public GoodsAdapter(Context context) {
        mContext = context;
    }

    private List<GoodsInfo> mGoodsInfoList = new ArrayList<>();

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);  //馒头
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.item_type_header,parent,false);
        //从商品类别中取出名字
        //TODO:服务器没有对type字段赋值，只能手动赋值

        //1.加头， 2.固定第一个可视的位置（view图层）  3.y轴偏移量


        Log.e("typename", "type:" + goodsInfo.getTypeName());
        ((TextView) headerView).setText(goodsInfo.getTypeName());
        return headerView;
    }

    @Override
    public long getHeaderId(int position) {
        GoodsInfo goodsInfo = mGoodsInfoList.get(position);  //馒头
        return goodsInfo.getTypeId();
    }


    @Override
    public int getCount() {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mGoodsInfoList != null) {
            return mGoodsInfoList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(mGoodsInfoList.get(position));
        return convertView;
    }


    class ViewHolder {
        private static final long SHOW_DURATION = 1000;
        @InjectView(R.id.iv_icon)
        ImageView mIvIcon;
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_zucheng)
        TextView mTvZucheng;
        @InjectView(R.id.tv_yueshaoshounum)
        TextView mTvYueshaoshounum;
        @InjectView(R.id.tv_newprice)
        TextView mTvNewprice;
        @InjectView(R.id.tv_oldprice)
        TextView mTvOldprice;
        @InjectView(R.id.ib_minus)
        ImageButton mIbMinus;
        @InjectView(R.id.tv_count)
        TextView mTvCount;
        @InjectView(R.id.ib_add)
        ImageButton mIbAdd;

        @OnClick({R.id.ib_add,R.id.ib_minus})
                public void onAddOrMinusClick(View view){
            switch (view.getId()){
                case R.id.ib_add:
                    doAddOperation();
                    break;
                case R.id.ib_minus:
                    doMinusOperation();
                    break;
            }
        }

        private void doMinusOperation() {

        }

        private void doAddOperation() {
            //1.执行动画
            Animation animation = getShowAnimation();
        }

        private Animation getShowAnimation() {
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.setDuration(SHOW_DURATION);

            AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
            animationSet.setDuration(SHOW_DURATION);
            animationSet.addAnimation(alphaAnimation);

            RotateAnimation rotateAnimation = new RotateAnimation(0,720, Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(rotateAnimation);

            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,2,
                    Animation.RELATIVE_TO_SELF,0,
                    Animation.RELATIVE_TO_SELF,0,
                    Animation.RELATIVE_TO_SELF,0
                    );
            translateAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(translateAnimation);
            return animationSet;
        }

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void setData(GoodsInfo goodsInfo) {
            mTvName.setText(goodsInfo.getName());
            Picasso.with(mContext).load(goodsInfo.getIcon()).into(mIvIcon);
            mTvZucheng.setText(goodsInfo.getForm());
            mTvYueshaoshounum.setText("月售" + goodsInfo.getMonthSaleNum() + "份");
            mTvNewprice.setText(PriceFormater.format(goodsInfo.getNewPrice()));
            if(goodsInfo.getOldPrice() > 0){
                mTvOldprice.setVisibility(View.VISIBLE);
                //画线效果
                mTvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                mTvOldprice.setVisibility(View.GONE);
            }
            mTvOldprice.setText(PriceFormater.format(goodsInfo.getOldPrice()));


        }
    }
}
