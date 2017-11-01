package com.szxb.module.member;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.szxb.base.BasePresenter;

import java.lang.ref.WeakReference;

/**
 * 作者: Tangren on 2017/8/30
 * 包名：com.szxb.module.member
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class MemberPresenter extends BasePresenter {

    private WeakReference<MemberActivity> weakReference;

    public MemberPresenter(MemberActivity activity) {
        weakReference = new WeakReference<MemberActivity>(activity);
    }

    @Override
    protected void onAllSuccess(int what, JSONObject result) {
        MemberActivity activity = weakReference.get();
        if (activity != null) {
            String rescode = result.getString("rescode");
            if (rescode.equals("0000")) {
                activity.onSuccess(what, result.toJSONString());
            } else {
                String resultString = result.getString("result");
                if (!TextUtils.isEmpty(resultString))
                    activity.onFail(what, false, resultString);
                else activity.onFail(what, false, result.toJSONString());
            }
        }
    }

    @Override
    protected void onFail(int what, boolean isOK, String failStr) {
        MemberActivity activity = weakReference.get();
        if (activity != null)
            activity.onFail(what, false, "网络或服务器异常!");
    }
}
