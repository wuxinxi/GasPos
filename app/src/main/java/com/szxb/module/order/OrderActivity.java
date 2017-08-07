package com.szxb.module.order;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.interfaces.OnCloseDialogListener;
import com.szxb.utils.Config;
import com.szxb.utils.Util;
import com.szxb.widget.ImageDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: Tangren on 2017/8/1
 * 包名：com.szxb.module.order
 * 邮箱：996489865@qq.com
 * TODO:订单Activity
 */
@Route(path = "/gas/order")
public class OrderActivity extends BaseMvpActivity<TransactionPresenter> implements OnCloseDialogListener {

    @BindView(R.id.gasNo)
    TextView gasNo;
    @BindView(R.id.gasOils)
    TextView gasOils;
    @BindView(R.id.transactionFlow)
    TextView transactionFlow;
    @BindView(R.id.gasCapacity)
    TextView gasCapacity;
    @BindView(R.id.gasPrice)
    TextView gasPrice;
    @BindView(R.id.memberPrice)
    TextView memberPrice;
    @BindView(R.id.gasTotal)
    TextView gasTotal;
    @BindView(R.id.memberTotal)
    TextView memberTotal;
    @BindView(R.id.wechatPay)
    Button wechatPay;
    @BindView(R.id.aliPay)
    Button aliPay;

    private ImageDialog imageDialog;
    private String paytype = "1";
    private String orderNo = null;

    @Override
    protected TransactionPresenter getChildPresenter() {
        return new TransactionPresenter(this);
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        super.initView();
        title.setText("订单页");
    }

    @Override
    protected void initData() {
        imageDialog = ImageDialog.getImageDialog();
        imageDialog.setCloseDialogListener(this);
        orderNo = Util.getOrderNo();
    }


    @Override
    protected Map<String, Object> getRequestParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("mch_id", FetchAppConfig.mchId());
        map.put("paytype", paytype);
        map.put("total_fee", "1");
        map.put("trantype", "1");
        map.put("orderid", orderNo);
        return map;
    }


    @Override
    protected Map<String, Object> getLoopRequestParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("MCHID", FetchAppConfig.mchId());
        map.put("ORDERID", orderNo);
        return map;
    }

    @OnClick({R.id.wechatPay, R.id.aliPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wechatPay:
                fetchRequest("1");
                break;
            case R.id.aliPay:
                break;
        }
    }

    //请求
    private void fetchRequest(String paytype) {
        this.paytype = paytype;
        FragmentTransaction mFragTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("img");
        if (fragment != null) {
            Toast.makeText(this, "remove", Toast.LENGTH_SHORT).show();
            mFragTransaction.remove(fragment);
        }
        imageDialog.show(mFragTransaction, "img");
        mPresenter.requestData(Config.REQUESTQRCODE_WHAT, getRequestParams(), Config.SCANPAY_URL);
    }

    @Override
    public void onSuccess(String str) {
        imageDialog.setImage(getApplicationContext(), str);
        mPresenter.requestData(Config.LOOP_WHAT, getLoopRequestParams(), Config.LOOP_URL);
    }

    @Override
    public void onFail(String str) {
        imageDialog.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnCloseDialog(Dialog mDialog) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mPresenter.cancelTimerTask();
        }
    }
}
