package com.szxb.db.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.szxb.db.dao.DaoMaster;
import com.szxb.db.dao.DaoSession;

import org.greenrobot.greendao.async.AsyncSession;


/**
 * 作者：Tangren on 2017/6/13 17:22
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class DBCore {

    private static final String DEFAULT_DB_NAME = "xb_bus_.db";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static AsyncSession asyncSession;

    private static Context mContext;
    private static String DB_NAME;

    public static void init(Context context) {
        init(context, DEFAULT_DB_NAME);
    }

    public static void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context.getApplicationContext();
        DB_NAME = dbName;
    }


    private static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DBHelper helper = new DBHelper(mContext, DB_NAME);
            SQLiteDatabase db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
        }
        return daoMaster;
    }

    //同步操作
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    //异步操作
   public static AsyncSession getASyncDaoSession() {
        if (asyncSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            asyncSession = daoMaster.newSession().startAsyncSession();
        }
        return asyncSession;
    }

}
