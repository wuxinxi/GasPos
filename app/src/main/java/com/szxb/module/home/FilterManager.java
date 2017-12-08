package com.szxb.module.home;

import com.szxb.db.manager.DBManager;
import com.szxb.entity.SeriaInformation;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: Tangren on 2017-12-06
 * 包名：com.szxb.module.home
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class FilterManager {

    private volatile static FilterManager instance = null;

    private FilterManager() {
    }

    public static FilterManager getInstance() {
        if (instance == null) {
            synchronized (FilterManager.class) {
                if (instance == null) {
                    instance = new FilterManager();
                }
            }
        }
        return instance;
    }

    public void filter(SeriaInformation information) {
        if (information == null) return;
        boolean b = DBManager.checkEmpNo(information);
        if (b) {
            PushICManager.getInstance().push(getMap());
        }
    }


    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        return null;
    }
}
