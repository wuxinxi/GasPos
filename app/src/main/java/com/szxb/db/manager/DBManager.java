package com.szxb.db.manager;

import android.text.TextUtils;
import android.util.Log;

import com.szxb.App;
import com.szxb.db.dao.EmpEntityDao;
import com.szxb.db.dao.SeriaInformationDao;
import com.szxb.entity.EmpEntity;
import com.szxb.entity.SeriaInformation;
import com.szxb.utils.tip.Tip;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.CountQuery;

import java.util.List;

/**
 * 作者：Tangren on 2017/6/21 10:02
 * 邮箱：wu_tangren@163.com
 * TODO:数据库操作类
 */
public class DBManager {


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

    /**
     * 查询最后3条数据
     *
     * @return .
     */
    public static List<SeriaInformation> query() {
        SeriaInformationDao dao = DBCore.getDaoSession().getSeriaInformationDao();
        return dao.queryBuilder()
                .orderDesc(SeriaInformationDao.Properties.Id)
                .limit(3)
                .list();
    }

    /**
     * 删除所有加油记录
     */
    public static void deleteAll() {
        AsyncSession session = new AsyncSession(DBCore.getDaoSession());
        session.setListener(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                Tip.show(App.getInstance(), "清除成功!", true);
            }
        });
        session.deleteAll(SeriaInformation.class);
    }


    /**
     * 更新员工信息
     *
     * @param entity .
     */
    public static void updateEmp(final List<EmpEntity> entity) {
        EmpEntityDao dao = DBCore.getDaoSession().getEmpEntityDao();
        dao.deleteAll();
        dao.insertOrReplaceInTx(entity);
    }

    /**
     * 检查是否是员工卡
     *
     * @param information
     * @return
     */
    public static boolean checkEmpNo(SeriaInformation information) {
        EmpEntityDao dao = DBCore.getDaoSession().getEmpEntityDao();
        EmpEntity unique = dao.queryBuilder().where(EmpEntityDao.Properties.EmpNo.eq(information.getLogicalCardNo())).build().unique();
        return unique != null;
    }


}
