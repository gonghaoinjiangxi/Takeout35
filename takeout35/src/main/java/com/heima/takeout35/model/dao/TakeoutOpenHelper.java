package com.heima.takeout35.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.heima.takeout35.model.net.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 *   1.0  基础功能  1
 *   1.1  支付功能  1
 *   1.2  地图功能  1
 *   。。。
 *   1.7  地址功能  2
 */

public class TakeoutOpenHelper extends OrmLiteSqliteOpenHelper {
    
    public TakeoutOpenHelper(Context context) {
        super(context, "takeout35.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建数据库的时候需要创建表
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, RecepitAddress.class);  //针对新用户有效
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新旧表的字段，增加新的表 adb install -r覆盖安装
        try {
            TableUtils.createTable(connectionSource, RecepitAddress.class);  //针对老用户升级
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //创建收藏表
    }
}
