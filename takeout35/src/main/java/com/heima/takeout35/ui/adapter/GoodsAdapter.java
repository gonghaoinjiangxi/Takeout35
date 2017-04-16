package com.heima.takeout35.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
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
import com.heima.takeout35.model.net.GoodsTypeInfo;
import com.heima.takeout35.ui.activity.BusinessActivity;
import com.heima.takeout35.ui.fragment.GoodsFragment;
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
    private GoodsFragment mGoodsFragment;
    private Context mContext;

    public GoodsAdapter(Context context, GoodsFragment goodsFragment) {
        mContext = context;
        this.mGoodsFragment = goodsFragment;
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
        private GoodsInfo mGoodsInfo;

        @OnClick({R.id.ib_add,R.id.ib_minus})
       public void onAddOrMinusClick(View view){
            boolean isAdd = false;
            switch (view.getId()){
                case R.id.ib_add:
                    isAdd = true;
                    doAddOperation();
                    break;
                case R.id.ib_minus:
                    doMinusOperation();
                    break;
            }
            //3.处理红点
            processRedDot(isAdd);
            //4.处理底部的购物栏（必须筛选哪些商品属于购物车 数量大于0）
            List<GoodsInfo> cartList = mGoodsFragment.mGoodsFragmentPresenter.getCartList();
            //让activity刷新数量以及总价
            ((BusinessActivity) mGoodsFragment.getActivity()).updateCartUi(cartList);
        }

        private void processRedDot(boolean isAdd) {
            //点击增加或者减少馒头
            int typeId = mGoodsInfo.getTypeId(); //粗粮主食
            //2.根据粗粮主食id找到它的排序position
            int position = mGoodsFragment.mGoodsFragmentPresenter.getTypePositionByTypeId(typeId);
            //3.从左侧列表中找到第postion个GoodstypeInfo
            GoodsTypeInfo goodsTypeInfo = mGoodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfoList.get(position);
            int count = goodsTypeInfo.getCount();
            if(isAdd){
                count++;
            }else{
                count --;
            }
            goodsTypeInfo.setCount(count);
            //4.刷新左侧列表
            mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();
        }

        private void doMinusOperation() {
            //1.执行动画
            AnimationSet hideAnimation = getHideAnimation();
            int count = mGoodsInfo.getCount();
            if(count == 1){
                //从1到0，隐藏view
//
                mTvCount.setVisibility(View.INVISIBLE);
                mIbMinus.setVisibility(View.INVISIBLE);

                mTvCount.startAnimation(hideAnimation);
                mIbMinus.startAnimation(hideAnimation);


            }else{
                //只改变数量

            }

            count --;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();
        }

        private void doAddOperation() {
            //1.执行动画
            AnimationSet animationSet = getShowAnimation();
            int count = mGoodsInfo.getCount();
            if(count == 0){
                //从0到1，
                mTvCount.setVisibility(View.VISIBLE);
                mIbMinus.setVisibility(View.VISIBLE);

                mTvCount.startAnimation(animationSet);
                mIbMinus.startAnimation(animationSet);
            }else{
                //只改变数量

            }

            count ++;
            mGoodsInfo.setCount(count);
            notifyDataSetChanged();

            //执行抛物线动画
            //1.在原来+号的位置，添加一个新的+号，新的+号和原来的样式一样
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mIbAdd.getLayoutParams();

            //getX/getY  event.getRawX/Y
            ImageButton ib = new ImageButton(mContext);
//            mIbAdd.getX(); //相对于父view的位置
//            mIbAdd.getY();
//            Log.e("donghua", "x:" + mIbAdd.getX() + ",y:" + mIbAdd.getY());
            int[] srcLocation = new int[2];
            mIbAdd.getLocationInWindow(srcLocation);  //输入参数也是输出参数
            Log.e("donghua", "x:" + srcLocation[0] + ",y:" + srcLocation[1]);
            ib.setX(srcLocation[0]);
            ib.setY(srcLocation[1]);
            ib.setBackgroundResource(R.drawable.button_add);

            ((BusinessActivity) mGoodsFragment.getActivity()).addImageButton(ib, mIbAdd.getWidth(),mIbAdd.getHeight());

            //2.画抛物线动画轨迹
            int[] destLocation = ((BusinessActivity) mGoodsFragment.getActivity()).getCartLocation();
            AnimationSet parabolaAnimation = getParabolaAnimation(ib, srcLocation, destLocation);
            ib.startAnimation(parabolaAnimation);
        }

        private AnimationSet getParabolaAnimation(final ImageButton ib, int[] srcLocation, int[] destLocation) {
            //先做斜线
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.setDuration(SHOW_DURATION);
            TranslateAnimation translateX = new TranslateAnimation(
                    Animation.ABSOLUTE, srcLocation[0],
                    Animation.ABSOLUTE, destLocation[0],
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 0
            );
            translateX.setDuration(SHOW_DURATION);
            animationSet.addAnimation(translateX);

            TranslateAnimation translateY = new TranslateAnimation(
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, srcLocation[1],
                    Animation.ABSOLUTE, destLocation[1]
            );
            translateY.setInterpolator(new AccelerateInterpolator());
            translateY.setDuration(SHOW_DURATION);
            animationSet.addAnimation(translateY);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //3.投入购物栏以后回收
                    ViewParent viewParent = ib.getParent();
                    if(viewParent instanceof ViewGroup){
                        ViewGroup viewGroup = (ViewGroup) viewParent;
                        viewGroup.removeView(ib);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            return animationSet;
        }

        private AnimationSet getHideAnimation() {
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.setDuration(SHOW_DURATION);

            AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
            alphaAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(alphaAnimation);

            RotateAnimation rotateAnimation = new RotateAnimation(720,0, Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(rotateAnimation);

            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,0,
                    Animation.RELATIVE_TO_SELF,2,
                    Animation.RELATIVE_TO_SELF,0,
                    Animation.RELATIVE_TO_SELF,0
            );
            translateAnimation.setDuration(SHOW_DURATION);
            animationSet.addAnimation(translateAnimation);

            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //隐藏掉-号还有数量
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            return animationSet;
        }


        private AnimationSet getShowAnimation() {
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
            this.mGoodsInfo = goodsInfo;
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

            mTvCount.setText(goodsInfo.getCount() + "");

            if(goodsInfo.getCount() > 0){
                mTvCount.setVisibility(View.VISIBLE);
                mIbMinus.setVisibility(View.VISIBLE);
            }else{
                //INVIABLE和GONE的区别
                mTvCount.setVisibility(View.INVISIBLE);
                mIbMinus.setVisibility(View.INVISIBLE);
            }


        }
    }
}
