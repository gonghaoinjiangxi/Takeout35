package com.heima.takeout35.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

/**
 * 班长
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //自定义消息发送到这里，从intent里取出

        //TODO:收到消息后更新view层


        Bundle bundle = intent.getExtras();
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e("jpush", "自定义消息：" + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e("jpush", "额外消息：" + extras);

        //通知adapter去刷新
        //1.拿到adapter，找到第二个订单，修改type就可以了(需要activity的引用）

        //2.使用观察者模式： 预先绑定adapter和MyReceiver，新消息来以后，adapter自动刷新


        //a.通知数据源新消息来了（班长把新任务贴到黑板上）
        //转换数据格式
        HashMap<String,String> data =  processExtra(extras);

        OrderObservable.getInstance().newMsgComing(data);
    }

    private HashMap<String,String> processExtra(String extras) {
        if(TextUtils.isEmpty(extras)){
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(extras);
            String orderId = jsonObject.getString("orderId");
            String type = jsonObject.getString("type");

            HashMap<String,String> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("type", type);
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}
