package com.szxb.module.login;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szxb.App;
import com.szxb.base.BasePresenter;
import com.szxb.db.manager.DBManager;
import com.szxb.entity.EmpEntity;
import com.szxb.utils.comm.Constant;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Tangren on 2017/6/28
 * 包名：com.szxb.module.login
 * 邮箱：996489865@qq.com.com
 * TODO:一句话描述
 */

public class LoginPresenter extends BasePresenter {

    private WeakReference<LoginZipActivity> weakReference;

    public LoginPresenter(LoginZipActivity activity) {
        weakReference = new WeakReference<LoginZipActivity>(activity);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        LoginZipActivity activity = weakReference.get();
        if (activity != null) {
            String rescode = result.getString("rescode");
            switch (what) {
                case Constant.LOGIN_WHAT:
                    if (rescode != null && TextUtils.equals(rescode, "0000")) {
                        activity.onSuccess(what, "登录成功!");
                    } else {
                        activity.onFail(what, false, result.toString());
                    }
                    break;
                case Constant.UPDATE_EMP:
                    if (rescode != null && TextUtils.equals(rescode, "0000")) {
                        JSONArray list = result.getJSONArray("list");
                        List<EmpEntity> empEntitys = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            JSONObject empNo = list.getJSONObject(i);
                            String empNoStr = empNo.getString("EMPCARDNO");
                            EmpEntity empEntity = new EmpEntity();
                            empEntity.setEmpNo(empNoStr);
                            empEntitys.add(empEntity);
                        }
                        DBManager.updateEmp(empEntitys);
                        activity.onSuccess(what, "员工信息更新成功!");
                    } else {
                        activity.onFail(what, false, result.toString());
                    }
                    break;
                default:

                    break;
            }

        }
    }

    @Override
    protected void onFail(int what, boolean isOK, String failStr) {
        LoginZipActivity activity = weakReference.get();
        if (activity != null)
            activity.onFail(what, isOK, failStr);
    }

    public void chechK21() {
        if (!App.getPosManager().getInitK21()) {

        }
    }
}
