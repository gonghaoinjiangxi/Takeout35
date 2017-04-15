package com.heima.takeout35.presenter;

import com.heima.takeout35.model.net.ResponseInfo;
import com.heima.takeout35.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 打包所有接口
 */
public interface TakeoutService {
//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);

    //首页
    @GET(Constants.HOME)
    Call<ResponseInfo> getHomeInfo();

    @GET(Constants.ORDER)
    Call<ResponseInfo> getOrderList(@Query("userId") String userId);


    //登录
    @GET(Constants.LOGIN)
    Call<ResponseInfo> loginByPhone(@Query("phone") String phone, @Query("type") int type);

    //商品详情页
    @GET(Constants.BUSINESS)
    Call<ResponseInfo> getBusinessInfo(@Query("sellerId") int sellerId);
}
