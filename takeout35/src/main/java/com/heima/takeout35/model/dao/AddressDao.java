package com.heima.takeout35.model.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD
 */

public class AddressDao {

    private Dao<RecepitAddress, Integer> mAddressDao;

    public AddressDao(Context context) {
        TakeoutOpenHelper takeoutOpenHelper = new TakeoutOpenHelper(context);
        try {
            mAddressDao = takeoutOpenHelper.getDao(RecepitAddress.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertAddress(RecepitAddress recepitAddress){
        try {
            mAddressDao.create(recepitAddress);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAddress(RecepitAddress recepitAddress){
        try {
            mAddressDao.update(recepitAddress);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAddress(RecepitAddress recepitAddress){
        try {
            mAddressDao.delete(recepitAddress);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RecepitAddress> queryAllAddres(){
        try {
           return mAddressDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public RecepitAddress queryAddressById(String userId){
        try {
          RecepitAddress address =  mAddressDao.queryForId(Integer.parseInt(userId));
            //TODO:
            return address;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
