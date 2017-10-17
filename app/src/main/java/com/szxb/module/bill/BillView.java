package com.szxb.module.bill;

import com.szxb.base.BaseView;

import java.util.List;

/**
 * 作者: Tangren on 2017/8/5
 * 包名：com.szxb.module.bill
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface BillView<T> extends BaseView {

    void loadSuccess(List<T> billLists);

}
