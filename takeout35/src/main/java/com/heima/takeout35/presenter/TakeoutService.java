package com.heima.takeout35.presenter;

import com.heima.takeout35.model.net.ResponseInfo;
import com.heima.takeout35.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 打包所有接口
 */
public interface TakeoutService {
//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);


    @GET(Constants.HOME)
    Call<ResponseInfo> getHomeInfo();

//    @GET("users/{user}/repos")
//    Call<List<Repo>> listOrder(@Path("user") String user);
//
//    @GET("users/{user}/repos")
//    Call<List<Repo>> login(@Path("user") String user);
}
