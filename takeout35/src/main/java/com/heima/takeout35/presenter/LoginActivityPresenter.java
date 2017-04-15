package com.heima.takeout35.presenter;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.heima.takeout35.model.dao.TakeoutOpenHelper;
import com.heima.takeout35.model.net.ResponseInfo;
import com.heima.takeout35.model.net.User;
import com.heima.takeout35.ui.activity.LoginActivity;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Savepoint;

import retrofit2.Call;

import static com.heima.takeout35.utils.TakeoutApp.sUser;

/**
 * Created by lidongzhi on 2017/4/13.
 */

public class LoginActivityPresenter extends BasePresenter{

    private LoginActivity mLoginActivity;

    public LoginActivityPresenter(LoginActivity loginActivity) {
        this.mLoginActivity = loginActivity;
    }

    public void loginByPhone(String phone, int type){
        //18912345566，用type区别到底普通账还是手机账号，type=-2表示是手机账号，-1表示是普通账号
        Call<ResponseInfo> loginCall = mService.loginByPhone(phone,type);
        loginCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String json) {
        boolean isLoginOk = false;
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        Log.e("login", "id:" + user.getId());
        //保存用户信息，内存、sqlite(文件、sp)
        //1.内存缓存
        sUser = user;
        //2.sqlite缓存，sqliteopenHelper
        TakeoutOpenHelper takeoutOpenHelper = new TakeoutOpenHelper(mLoginActivity);
        SQLiteDatabase db = takeoutOpenHelper.getWritableDatabase();
        //操作数据库需要connection，有连接才能进行CRUD
//        AndroidConnectionSource
        AndroidDatabaseConnection connection = new AndroidDatabaseConnection(db, true);
        //保存点,操纵数据库的事务
        Savepoint startPoint = null;
        try {
            startPoint = connection.setSavePoint("start");
            connection.setAutoCommit(false); //手动提交事务，注意顺序
            //步骤A:查找历史
            Dao<User,Integer> userDao = takeoutOpenHelper.getDao(User.class);
            //步骤B:更新或者创建
            User oldUser = userDao.queryForId(35);
            if(oldUser!=null){
                //已经有35这个用户，更新用户信息（老用户）
                userDao.update(user);
                //TecentStasticSDK.userAction(ACTION_LOGIN, -1);
                Log.e("login", "老用户登录");
            }else{
                //新建一个用户,新用户登录
                userDao.create(user);
                //TecentStasticSDK.userAction(ACTION_LOGIN, 0);
                Log.e("login", "新用户登录");
            }
//
            //防重复操作，先判断是否已经存在id=35的用户
//            userDao.createIfNotExists(user);
            Log.e("login", "保存用户缓存信息成功");
            connection.commit(startPoint);
            isLoginOk = true;
        } catch (SQLException e) {
            e.printStackTrace();
            isLoginOk = false;
            Log.e("login", "保存用户缓存信息失败");
            try {
                connection.rollback(startPoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        mLoginActivity.afterLogin(isLoginOk);
    }
}
