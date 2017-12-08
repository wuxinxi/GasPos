package com.szxb.module.shortpay;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.szxb.App;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.manager.DBManager;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.entity.SeriaInformation;
import com.szxb.interfaces.OnAlertListener;
import com.szxb.utils.Alert;
import com.szxb.utils.DateUtil;
import com.szxb.utils.Util;
import com.szxb.utils.comm.Constant;
import com.szxb.utils.comm.UrlComm;
import com.szxb.utils.tip.Tip;
import com.szxb.widget.WaitDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: Tangren on 2017-09-21
 * 包名：com.szxb.module.shortpay
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/short")
public class ShortPayActivity extends BaseMvpActivity<ShortPresenter> implements WaitDialog.OnDialogListener, OnAlertListener {
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.num_del)
    Button numDel;
    @BindView(R.id.num_determine)
    Button numDetermine;
    @BindView(R.id.short_code)
    TextView shortCode;
    @BindView(R.id.guaqi)
    Button guaqi;
    @BindView(R.id.goPay)
    Button goPay;
    @BindView(R.id.tip)
    TextView tip;

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3,
            R.id.num_4, R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};

    private String shortcodeString;
    private boolean payFinish = false;//是否支付完成
    private boolean orderFinish = false;//下单是否完成

    //短码是是否可以编辑
    private boolean SHORT_EDIT = true;
    private String orderNo = null;

    @Autowired
    SeriaInformation infoEntity;
    @Autowired
    String short_code;
    //当前油站的订单号
    //一笔加油信息一笔记录
    private String currentGasOrder;

    private WaitDialog mDialog;
    private Alert alert;

    @Override
    protected int layoutID() {
        return R.layout.activity_short;
    }

    @Override
    protected ShortPresenter getChildPresenter() {
        return new ShortPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ARouter.getInstance().inject(this);
        for (int button : buttons) {
            Button tempButton = (Button) findViewById(button);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SHORT_EDIT) {
                        shortcodeString = shortCode.getText().toString();
                        shortcodeString = shortcodeString + String.valueOf(((Button) v).getText());
                        shortCode.setText(shortcodeString);
                    } else Tip.show(getApplicationContext(), "短码处于不可编辑状态", false);
                }
            });
        }

        numDetermine.setText("查询");
    }

    @Override
    protected void initData() {
        if (short_code != null)
            shortCode.setText(short_code);
        if (infoEntity != null)
            currentGasOrder = infoEntity.getXbOrderNo();
        mDialog = WaitDialog.newInstance(true);
        mDialog.setCloseListener(this);
        alert = new Alert();
        alert.setAlertListener(this);
    }

    @Override
    protected Map<String, Object> getRequestParams() {
        orderNo = Util.getOrderNo();
        Map<String, Object> map = new HashMap<>();
        map.put("orderid", orderNo);
        map.put("trantype", "0");
        map.put("paytype", "9");
        map.put("devno", "0001");
        map.put("mchid", App.getPosManager().getMchID());
        map.put("mername", App.getPosManager().getOrderDec());
        map.put("goodname", infoEntity.getOilCode());//油品名称
        map.put("goodcode", infoEntity.getOilCode());//商品代码,线上使用infoEntity.getOilCode()
        map.put("qty", infoEntity.getFuelingUp());//加油升数
        map.put("total_fee", infoEntity.getGasMoney());//总金额 infoEntity.getGasMoney()
        map.put("price", infoEntity.getPrices());//油品单价infoEntity.getPrices()
        map.put("shortcode", shortCode.getText().toString());//短码
        map.put("trantime", DateUtil.getCurrentDate());//订单时间
        map.put("saletime", DateUtil.getCurrentDate());//销售时间
        map.put("class", infoEntity.getLogicalCardNo());//班次
        map.put("operaterno", App.getPosManager().getUserNo());//操作员编号
        map.put("salerno", App.getPosManager().getUserNo());//销售员编号,逻辑卡号
        map.put("mchineno", infoEntity.getRemark_1());//加油机号
        map.put("gmchineno",infoEntity.getGasMoney());//加油枪号
        map.put("rmk", "无");//备注
        return map;
    }

    @OnClick({R.id.num_del, R.id.num_determine, R.id.guaqi, R.id.goPay})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.num_del:
                SHORT_EDIT = true;
                Util.delNum(shortCode);
                break;
            case R.id.num_determine:
                if (shortCode.getText().toString().length() < 4) {
                    Tip.show(getApplicationContext(), "订单不存在", false);
                    return;
                }
                if (!mPresenter.isCancleTask()) {
                    Tip.show(getApplicationContext(), "暂未完成支付", false);
                    return;
                }
                showDialog();
                mPresenter.requestData(Constant.SHORT_QUERY_WHAT, getLoopRequestParams(), UrlComm.getInstance().SHORTORDERQUERYURL());
                break;
            case R.id.guaqi:
                if (shortCode.getText().toString().length() < 4) {
                    Tip.show(getApplicationContext(), "短码不能少于4位", false);
                    return;
                }
                if (!payFinish && orderFinish) {
                    Tip.show(getApplicationContext(), "已下单无法挂起,正在撤销", false);
                    //撤销操作
                    showDialog();
                    mPresenter.requestData(Constant.SHORT_CANCEL_WHAT, getLoopRequestParams(), UrlComm.getInstance().ORDERCANCEURL());
                    return;
                }

                intent.putExtra("short_code", shortCode.getText().toString());
                this.setResult(0x12, intent);
                finishActivityFromRight();
                break;
            case R.id.goPay:
                if (shortCode.getText().toString().length() < 4) {
                    Tip.show(getApplicationContext(), "短码不能少于4位", false);
                    return;
                }
                if (infoEntity == null) {
                    Tip.show(getApplicationContext(), getResources().getString(R.string.chose_tip), false);
                    intent.putExtra("short_code", shortCode.getText().toString());
                    this.setResult(0x12, intent);
                    finishActivityFromRight();
                } else {
                    showDialog();
                    goPay.setEnabled(false);
                    mPresenter.requestData(Constant.SHORT_WHAT, getRequestParams(), UrlComm.getInstance().SHORTURL());
                }
                break;
        }
    }


    @Override
    protected Map<String, Object> getLoopRequestParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("mchid", FetchAppConfig.mchId());
        map.put("orderid", orderNo);
        return map;
    }


    @Override
    public void onSuccess(int what, String str) {
        switch (what) {
            case Constant.SHORT_WHAT://下单成功
                orderFinish = true;
                Tip.show(getApplicationContext(), "下单成功,请尽快完成支付", true);
                SHORT_EDIT = false;
                mPresenter.requestData(Constant.LOOP_WHAT, getLoopRequestParams(), UrlComm.getInstance().SHORTORDERQUERYURL(), tip);
                break;
            case Constant.LOOP_WHAT://支付成功
                tip.setText("支付成功");
                payFinish = true;
                Tip.show(getApplicationContext(), "支付成功", true);
                DBManager.updatePayState(currentGasOrder);
                //支付成功
                closeDialog();
                goPay.setText("支付完成");
                break;
            case Constant.SHORT_QUERY_WHAT: //单次查询支付成功
                tip.setText("支付成功");
                payFinish = true;
                mPresenter.cancelTimerTask();//并且取消轮训
                DBManager.updatePayState(currentGasOrder);
                Tip.show(getApplicationContext(), str, true);
                goPay.setText("支付完成");
                closeDialog();
                break;
            case Constant.SHORT_CANCEL_WHAT://取消订单
                tip.setText("撤销成功");
                orderFinish = false;
                Tip.show(getApplicationContext(), str, true);
                closeDialog();
                finishActivityFromRight();
                break;
            default:

                break;
        }
    }

    private void showDialog() {
        FragmentTransaction mFragTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("img");
        if (fragment != null) {
            mFragTransaction.remove(fragment);
        }
        mDialog.show(mFragTransaction, "img");
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isAdded()) {
            mPresenter.cancelTimerTask();
            mDialog.disDialog();
            SHORT_EDIT = false;
        }

    }

    @Override
    public void onFail(int what, boolean isOK, String str) {
        Log.d("ShortPayActivity",
                "onSuccess(ShortPayActivity.java:233)短码付what=" + what);
        switch (what) {
            case Constant.LOOP_WHAT:
                if (isOK) {
                    tip.setText("暂未完成支付");
                    Tip.show(getApplicationContext(), str + "\n可手动查询支付结果", false);
                    closeDialog();
                }
                break;
            case Constant.SHORT_WHAT://短码下单失败
                tip.setText(str);
                goPay.setEnabled(true);
                closeDialog();
                Tip.show(getApplicationContext(), str, false);
                break;
            default://短码订单查询、短码撤销
                closeDialog();
                tip.setText(str);
                Tip.show(getApplicationContext(), str, false);
                break;
        }
    }

    @Override
    public void back() {
        Log.d("OrderZipActivity",
                "success(ShortPayActivity.java:266)BACK…………………………………………");
        //paFinish=true,orderFinish=false
        if (!payFinish && orderFinish) {
            alert.show(this, 0, "提示", "页面暂未完成支付,确认是否离开?", "撤销订单", "强制退出", "否");
        } else {
            finishActivityFromRight();
        }

    }

    @Override
    public void close() {
        Tip.show(getApplicationContext(), "撤销中", true);
        //取消轮训任务
        mPresenter.cancelTimerTask();
        //撤销操作
        showDialog();
        mPresenter.requestData(Constant.SHORT_CANCEL_WHAT, getLoopRequestParams(), UrlComm.getInstance().ORDERCANCEURL());
    }

    @Override
    public void setPositiveButton(int what) {
        //如果未完成支付,取消轮训任务并且做撤销操作
        mPresenter.cancelTimerTask();
        //撤销操作
        showDialog();
        mPresenter.requestData(Constant.SHORT_CANCEL_WHAT, getLoopRequestParams(), UrlComm.getInstance().ORDERCANCEURL());
    }

    @Override
    public void setNegativeButton(int what) {
        finishActivityFromRight();
    }
}
