package com.heima.takeout35.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.heima.takeout35.R;
import com.heima.takeout35.model.net.Seller;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2017/4/12.
 */

public class HomeRvAdapter extends RecyclerView.Adapter {

    public static final int TYPE_TITLE = 0;  //头布局
    public static final int TYPE_DIVISION = 1; //分割线，即广告
    public static final int TYPE_SELLER = 2;  //商家，分为附近商家  其他商家

    public static final int GROUP_SIZE = 10;  //分页大小10个

    /**
     * type必须是自然排序 0/1/2/3
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //商家数量比较多，进行分组，每组之间展示广告
        //TODO: 头布局 （附近0-附近9) 广告 （其他0-其他9) 广告 （其他10-其他19) 广告 （其他20-其他24)
        if (position == 0) {
            return TYPE_TITLE;
        } else if (position >= (1 + mNearby.size()) && (position - 1 - mNearby.size()) % (GROUP_SIZE + 1) == 0) {
            //TODO:附近商家个数被写死了？？？
            //找出广告的规律，第一个广告是11
            return TYPE_DIVISION;
        } else {
            return TYPE_SELLER;
        }
    }

    private Context mContext;

    public HomeRvAdapter(Context context) {
        mContext = context;
    }

    private List<Seller> mNearby = new ArrayList<>();
    private List<Seller> mOther = new ArrayList<>();

    public void setDatas(List<Seller> nearby, List<Seller> other) {
        mNearby = nearby;
        mOther = other;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //做三种不同的holder
        View itemView;
        switch (viewType) {
            case TYPE_TITLE:
                itemView = View.inflate(mContext, R.layout.item_title, null);
                TitleHolder titleHolder = new TitleHolder(itemView);
                return titleHolder;
            case TYPE_DIVISION:
                itemView = View.inflate(mContext, R.layout.item_division, null);
                DivisionHolder divisionHolder = new DivisionHolder(itemView);
                return divisionHolder;

            case TYPE_SELLER:
                itemView = View.inflate(mContext, R.layout.item_seller, null);
                SellerHolder sellerHolder = new SellerHolder(itemView);
                return sellerHolder;
            default:
                Log.e("home", "竟然还有第四种holder");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_TITLE:
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.setData("我是大哥，后面小弟跟好");
                break;
            case TYPE_DIVISION:
                DivisionHolder divisionHolder = (DivisionHolder) holder;
                divisionHolder.setData("----------------我是华丽的分割线--------------------");
                break;
            case TYPE_SELLER:
                SellerHolder sellerHolder = (SellerHolder) holder;
                //区分是附近商家还是其他商家，展示第几个商家index
                //TODO: 头布局 （附近0-附近9) 广告 （其他0-其他9) 广告 （其他10-其他19) 广告 （其他20-其他24)
                int newPostion;
                if (position < mNearby.size() + 1) {
                    newPostion = position - 1;
                    sellerHolder.setData(mNearby.get(newPostion));
                } else {
                    newPostion = position - 1 - mNearby.size() - 1;
                    newPostion -= newPostion / (GROUP_SIZE + 1);
                    sellerHolder.setData(mOther.get(newPostion));
                }
                break;

        }
    }

    @Override
    public int getItemCount() {
        //计算条目数
        //TODO: 头布局 （附近0-附近9) 广告 （其他0-其他9) 广告 （其他10-其他19) 广告 （其他20-其他29)
        if (mNearby.size() == 0 && mOther.size() == 0) {
            //百度外卖破产了，程序异常数据为空，没有网络，用户旅游进入无人区
            Log.e("home", "百度外卖破产了，程序异常数据为空，没有网络，用户旅游进入无人区");
        }
        int count = 1; //头布局展示促销信息
        if (mNearby.size() > 0) {
            count += mNearby.size(); //附近商家
        } else {
            Log.e("home", "附近商家为空");
        }

        if (mOther.size() > 0) {
            count += 1;  //第一个广告条
            count += mOther.size();
            //计算广告分割线的个数
            //如果其他商家是25个，2个广告；如果其他商家是30个，显示2个广告；如果31个，3个广告
            count += mOther.size() / GROUP_SIZE;
            //如果刚好整除，反向减去一个
            if (mOther.size() % GROUP_SIZE == 0) {
                count -= 1;
            }
        } else {
            Log.e("home", "其他商家为空");
        }

        return count;
    }

    class TitleHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.slider)
        SliderLayout mSliderLayout;

        TitleHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setData(String data) {
            testData(mContext);
        }

        private void testData(Context context) {
            HashMap<String, String> url_maps = new HashMap<String, String>();
            url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
            url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
            url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

            for (String desc : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(itemView.getContext());
                textSliderView
                        .description(desc)
                        .image(url_maps.get(desc));
                mSliderLayout.addSlider(textSliderView);
            }
        }
    }

    class DivisionHolder extends RecyclerView.ViewHolder {

        DivisionHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setData(String data) {

        }
    }

    class SellerHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.seller_logo)
        ImageView mSellerLogo;
        @InjectView(R.id.tvCount)
        TextView mTvCount;  //点了多少餐
        @InjectView(R.id.tv_title)
        TextView mTvTitle;
        @InjectView(R.id.ratingBar)
        RatingBar mRatingBar;
        @InjectView(R.id.tv_home_sale)
        TextView mTvHomeSale;
        @InjectView(R.id.tv_home_send_price)
        TextView mTvHomeSendPrice;
        @InjectView(R.id.tv_home_distance)
        TextView mTvHomeDistance;

        SellerHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setData(Seller seller) {
            Picasso.with(mContext).load(seller.getIcon()).into(mSellerLogo);
            mTvTitle.setText(seller.getName());
            mRatingBar.setRating(Float.parseFloat(seller.getScore()));
            mTvHomeSale.setText("月售" + seller.getSale() + "单");
            mTvHomeSendPrice.setText("￥" + seller.getSendPrice() + "起送/配送费￥" + seller.getDeliveryFee());
            mTvHomeDistance.setText(seller.getDistance());


        }
    }
}
