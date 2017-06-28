package com.cxg.blueboothpinter2.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cxg.blueboothpinter2.pojo.Ztwm004;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * 数据映射帮助类
 * Created by Administrator on 2017/5/5.
 */

public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "xpp.db";
    private static final int DATABASE_VERSION = 1;

    private static OrmHelper instance;

    private Dao<Ztwm004, Integer> ztwm004Dao = null;

    private OrmHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static OrmHelper getInstance() {
        return instance;
    }

    public static void createInstance(Context context) {
        if (instance == null)
            instance = new OrmHelper(context);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Ztwm004.class);
        } catch (SQLException e) {
            Log.e(OrmHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        Log.i(OrmHelper.class.getName(), "onUpgrade");
        try {
            TableUtils.dropTable(connectionSource, Ztwm004.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        ztwm004Dao = null;
    }

    public Dao<Ztwm004, Integer> getZtwm004Dao() throws SQLException{
        if (ztwm004Dao == null) {
            ztwm004Dao = getDao(Ztwm004.class);
        }
        return ztwm004Dao;
    }
}
