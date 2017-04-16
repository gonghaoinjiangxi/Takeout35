package com.heima.takeout35.model.dao;

/**
 * 点餐的记录
 */

public class CacheSelectInfo {

    private int sellerId; //田老师红烧肉
    private int typeId;  //粗粮主食
    private int goodsId; //馒头
    private int count; //几个馒头

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
