package com.heima.takeout35.presenter;

import android.util.Log;

import com.heima.takeout35.model.net.ResponseInfo;
import com.heima.takeout35.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请求网络基类
 */

public abstract class BasePresenter {

    protected TakeoutService mService;
    protected Retrofit mRetrofit;

    public BasePresenter() {
        //1.初始化retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2.构建service
        mService = mRetrofit.create(TakeoutService.class);

    }

    protected Callback<ResponseInfo> mCallback = new Callback<ResponseInfo>() {
        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            //response是Retrofit的
            ResponseInfo responseInfo = response.body();
            if("0".equals(responseInfo.getCode())){
                //请求成功,去解析
                String json = responseInfo.getData();
                parserJson(json);

            }else if("-1".equals(responseInfo.getCode())){
                //-1表示空数据
                Log.e("home","空数据");
            }else if("-2".equals(responseInfo.getCode())){
                //-2表示服务器500错误
                Log.e("home","服务器500错误");
            }else if("-3".equals(responseInfo.getCode())){
                //-3表示数据为缓存数据
                Log.e("home","缓存数据");
            }
        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            //没有连上网，网络异常导致的失败
            Log.e("home","网络异常");
        }
    };

    protected abstract void parserJson(String json);
}
