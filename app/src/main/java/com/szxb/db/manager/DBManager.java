package com.szxb.db.manager;

import android.text.TextUtils;
import android.util.Log;

import com.szxb.db.dao.SeriaInformationDao;
import com.szxb.entity.SeriaInformation;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.query.CountQuery;
import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * 作者：Tangren on 2017/6/21 10:02
 * 邮箱：wu_tangren@163.com
 * TODO:数据库操作类
 */
public class DBManager<T> {

    /**
     * 异步插入单条数据
     *
     * @param entity 要插入的对象
     * @param <T>    泛型对象
     */
    public static <T> AsyncOperation Asyncinsert(T entity) {
        return DBCore.getASyncDaoSession().insert(entity);
    }

    /**
     * 同步插入单条数据
     *
     * @param entity 要插入的对象
     * @param <T>    泛型对象
     */
    public static <T> void syncInsert(T entity) {
        DBCore.getDaoSession().insert(entity);
    }

    /**
     * 插入或替换
     *
     * @param entity 要插入的对象
     * @param <T>    泛型对象
     */
    public static <T> void insertOrReplease(T entity) {
        DBCore.getASyncDaoSession().insertOrReplace(entity);
    }


    /**
     * 修改
     *
     * @param entity 要修改的对象
     * @param <T>    泛型对象
     */
    public static <T> void update(T entity) {
        DBCore.getASyncDaoSession().update(entity);
    }

    /**
     * 查询所有数据
     *
     * @param <T> 泛型对象
     * @return List集合
     * Query<T> query = DBCore.getDaoSession().queryBuilder(entity).orderDesc(...Dao.Properties.Id).build();
     */
    public static <T> List<T> query(Query<T> query) {
        AsyncOperation asyncOperation = DBCore.getASyncDaoSession().queryList(query);
        return (List<T>) asyncOperation.getResult();
    }

    /**
     * 查询扫码的对象
     *
     * @param <T> 泛型对象
     * @return 查询出单个对象
     */
    public static <T> T queryEntity(Query<T> query) {
        AsyncOperation asyncOperation = DBCore.getASyncDaoSession().queryUnique(query);
        return (T) asyncOperation.getResult();
    }

    /**
     * 删除
     *
     * @param entity 删除某个对象
     * @param <T>    泛型对象
     */
    public static <T> void delete(T entity) {
        DBCore.getASyncDaoSession().delete(entity);
    }

    /**
     * 删除所有数据
     *
     * @param clazz 删除所有数据
     * @param <T>   泛型，对象名即表名
     */
    public static <T> void deleteAll(Class<T> clazz) {
        DBCore.getASyncDaoSession().deleteAll(clazz);
    }


    public static boolean query(SeriaInformation infoEntity) {
        SeriaInformationDao dao = DBCore.getDaoSession().getSeriaInformationDao();
        CountQuery<SeriaInformation> homeInfoEntityCountQuery = dao.queryBuilder()
                .where(SeriaInformationDao.Properties.MachineOrderNo.eq(infoEntity.getMachineOrderNo()))
                .buildCount();
        return homeInfoEntityCountQuery.count() <= 0;
    }

    public static void updatePayState(String orderNo) {
        if (TextUtils.isEmpty(orderNo)) return;
        SeriaInformation data = DBCore.getDaoSession().queryBuilder(SeriaInformation.class).where(SeriaInformationDao.Properties.XbOrderNo.eq(orderNo)).build().unique();
        if (data == null) return;
        data.setGasPayStatus("已支付");
        DBCore.getASyncDaoSession().update(data);
        Log.d("DBManager",
                "updatePayState(DBManager.java:123)修改成功");
    }

    public static List<SeriaInformation> query() {
        SeriaInformationDao dao = DBCore.getDaoSession().getSeriaInformationDao();
        return dao.queryBuilder()
                .orderDesc(SeriaInformationDao.Properties.Id)
                .limit(3)
                .list();
    }

}
