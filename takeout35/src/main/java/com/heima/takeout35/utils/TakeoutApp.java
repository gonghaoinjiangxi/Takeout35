package com.heima.takeout35.utils;

import android.app.Application;

import com.heima.takeout35.model.dao.CacheSelectedInfo;
import com.heima.takeout35.model.net.User;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lidongzhi on 2017/4/13.
 */

public class TakeoutApp extends Application {

    ArrayList<CacheSelectedInfo> infos = new ArrayList<>();
    public static TakeoutApp sInstance;


    /**
     * 根据商品id查数量
     * @param goodsId
     * @return
     */
    public int queryCacheSelectedInfoByGoodsId(int goodsId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getGoodsId() == goodsId){
                count = info.getCount();
                break;
            }
        }
        return count;
    }

    public int queryCacheSelectedInfoByTypeId(int typeId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getTypeId() == typeId){
                count = count + info.getCount();
            }
        }
        return count;
    }

    /**
     * 查询在此家店铺所有已点商品的数量
     * @param sellerId
     * @return
     */
    public int queryCacheSelectedInfoBySellerId(int sellerId){
        int count = 0;
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getSellerId() == sellerId){
                count = count + info.getCount();
            }
        }
        return count;
    }

    public void addCacheSelectedInfo(CacheSelectedInfo info) {
        infos.add(info);
    }

    public void clearCacheSelectedInfo(int sellerId){
        ArrayList<CacheSelectedInfo> temp = new ArrayList<>();
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if(info.getSellerId() == sellerId){
//                infos.remove(info);  //集合在遍历的时候是不能进行删除操作，需要标记出来，以后再移除
                temp.add(info);
            }
        }
        //循环结束了
        infos.removeAll(temp);
    }

    public void deleteCacheSelectedInfo(int goodsId) {
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if (info.getGoodsId() == goodsId) {
                infos.remove(info);
                break;
            }
        }
    }

    public void updateCacheSelectedInfo(int goodsId, int operation) {
        for (int i = 0; i < infos.size(); i++) {
            CacheSelectedInfo info = infos.get(i);
            if (info.getGoodsId() == goodsId) {
                switch (operation) {
                    case Constants.ADD:
                        info.setCount(info.getCount() + 1);
                        break;
                    case Constants.MINUS:
                        info.setCount(info.getCount() - 1);
                        break;
                }

            }
        }
    }

    //生命周期很长,栈内存释放不掉，原因是app这个类释放不掉
    public static User sUser;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        sUser = new User();
        sUser.setId(-1); //空用户，未登录

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
