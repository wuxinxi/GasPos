package com.szxb.module.order;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: Tangren on 2017-10-11
 * 包名：com.szxb.module.order
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/order")
public class OrderZipActivity extends BaseMvpActivity<TransactionPresenter> implements OnAlertListener {
    @BindView(R.id.toolBarTitle)
    TextView toolBarTitle;
    @BindView(R.id.home)
    Button home;
    @BindView(R.id.temp)
    TextView temp;
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
    @BindView(R.id.gasTotal)
    TextView gasTotal;
    @BindView(R.id.qr_code)
    ImageView qrCode;
    @BindView(R.id.wechat_pay)
    Button wechatPay;
    @BindView(R.id.ali_pay)
    Button aliPay;
    @BindView(R.id.cash_pay)
    Button cashPay;
    @BindView(R.id.progress)
    CircleProgressBar progress;
    @BindView(R.id.select_pay_type)
    TextView selectPayType;
    @BindView(R.id.pay_result)
    ImageView payRsult;
    @BindView(R.id.tip)
    TextView tip;

    @Autowired
    SeriaInformation infoEntity;

    private String orderNo = null;
    private String paytype = "1";
    @Autowired
    String status;//会员状态

    private boolean payFinish = false;//是否支付完成
    private boolean orderFinish = false;//下单是否完成
    //支付类型，0：微信，1:支付宝，2:现金支付
    private int payPostion = -1;
    //当前是否正在请求二维码
    private boolean currentISFetchQR = false;

    //当前油站的订单号
    //一笔加油信息一笔记录
    private String currentGasOrder;
    private Alert alert;

    @Override
    protected int layoutID() {
        return R.layout.activity_order_new;
    }

    @Override
    protected TransactionPresenter getChildPresenter() {
        return new TransactionPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ARouter.getInstance().inject(this);
        title.setText("订单页");
        temp.setVisibility(View.VISIBLE);
        temp.setText("已完成支付?");
        temp.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void initData() {
        if (infoEntity == null) finishActivityFromRight();
        gasNo.setText("油枪号:" + infoEntity.getGasNo());
        gasPrice.setText("油品单价:" + Util.getMoney(infoEntity.getPrices()));
        gasCapacity.setText("加油总量:" + Util.getFuelingUp(infoEntity.getFuelingUp()) + "L");
        gasOils.setText("油品编码:" + infoEntity.getOilCode());
        transactionFlow.setText("交易流水:" + infoEntity.getSeriaOrderNo());
        gasTotal.setText("加油总价:" + Util.getMoney(infoEntity.getGasMoney()));
        currentGasOrder = infoEntity.getXbOrderNo();

        alert = new Alert();
        alert.setAlertListener(this);
    }


    @Override
    protected Map<String, Object> getRequestParams() {
        orderNo = Util.getOrderNo();
        Map<String, Object> map = new HashMap<>();
        map.put("orderid", orderNo);
        map.put("trantype", "0");//0-商品销售 1-充值
        map.put("paytype", paytype);//0-现金 1-微信 2-支付宝 3-ic卡 4-银行卡
        map.put("devno", "000000");
        map.put("memno", infoEntity.getMemnerNo());//会员卡号,如果不是会员传1
        map.put("member_status", status);//会员0,不是会员传1
        map.put("mchid", App.getPosManager().getMchID());
        map.put("mername", App.getPosManager().getOrderDec());
        map.put("goodname", infoEntity.getOilCode());//油品名称
        map.put("goodcode", "0092");//商品代码,线上使用infoEntity.getOilCode()
        map.put("qty", "1");//加油升数,infoEntity.getFuelingUp()
        map.put("total_fee", "0.01");//总金额
        if (status.equals("1"))
            map.put("amount", "0.01");//实际扣款
        map.put("trantime", DateUtil.getCurrentDate());//订单时间
        map.put("saletime", DateUtil.getCurrentDate());//销售时间
        map.put("class", "0001");//班次
        map.put("operaterno", App.getPosManager().getUserNo());//操作员编号
        map.put("salerno", "00001");//销售员编号
        map.put("mchineno", "00002");//加油机号
        map.put("gmchineno", infoEntity.getGasNo());//加油枪号
        map.put("rmk", "无");//备注
        return map;
    }

    @Override
    protected Map<String, Object> getLoopRequestParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("mchid", FetchAppConfig.mchId());
        map.put("orderid", orderNo);
        return map;
    }


    @OnClick({R.id.wechat_pay, R.id.ali_pay, R.id.cash_pay, R.id.temp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wechat_pay:

                if (payFinish) {
                    Tip.show(getApplicationContext(), "已经支付完成", false);
                    return;
                }
                //如果当前正在请求二维码则提示操作过快
                if (currentISFetchQR) {
                    Tip.show(getApplicationContext(), "操作过快", false);
                    return;
                }
                payRsult.setVisibility(View.GONE);
                title.setText("订单页-微信");
                if (payPostion == 1) {
                    //如果正在使用支付宝支付需要切换到微信支付,则停止支付宝轮训任务
                    Log.d("OrderZipActivity",
                            "success(OrderZipActivity.java:166)停止支付宝轮训任务");
                    mPresenter.cancelTimerTask();
                }
                payPostion = 0;
                fetchRequest(Constant.REQUESTQRCODE_WHAT, "1", UrlComm.getInstance().PAYURL());
                currentISFetchQR = true;
                break;
            case R.id.ali_pay:
                if (payFinish) {
                    Tip.show(getApplicationContext(), "已经支付完成", false);
                    return;
                }
                if (currentISFetchQR) {
                    Tip.show(getApplicationContext(), "操作过快", false);
                    return;
                }
                payRsult.setVisibility(View.GONE);
                title.setText("订单页-支付宝");
                if (payPostion == 0) {
                    //如果正在使用微信支付需要切换到支付宝支付,则停止微信轮训任务
                    Log.d("OrderZipActivity",
                            "success(OrderZipActivity.java:177)停止微信轮训任务");
                    mPresenter.cancelTimerTask();
                }
                payPostion = 1;
                fetchRequest(Constant.REQUESTQRCODE_WHAT, "2", UrlComm.getInstance().PAYURL());
                currentISFetchQR = true;
                break;
            case R.id.cash_pay:
                if (payFinish) {
                    Tip.show(getApplicationContext(), "已经支付完成", false);
                    return;
                }
                title.setText("订单页-现金");
                if (currentISFetchQR) {
                    Tip.show(getApplicationContext(), "操作过快", false);
                    return;
                }
                if (payPostion == 0 || payPostion == 1) {
                    //如果正在使用微信支付或者支付宝支付需要切换到现金支付,则停止轮训任务
                    Log.d("OrderZipActivity",
                            "success(OrderZipActivity.java:177)停止轮训任务");
                    mPresenter.cancelTimerTask();
                }
                payPostion = 2;
                qrCode.setImageResource(0);
                selectPayType.setVisibility(View.VISIBLE);
                fetchRequest(Constant.CASH_WHAT, "0", UrlComm.getInstance().CASHURL());
                break;
            case R.id.temp:
                checkOrder();
                break;
        }
    }

    //请求
    private void fetchRequest(int what, String paytype, String url) {
        mPresenter.cancelTimerTask();
        this.paytype = paytype;
        progress.setVisibility(View.VISIBLE);
        selectPayType.setVisibility(View.GONE);
        mPresenter.requestData(what, getRequestParams(), url);
    }

    @Override
    public void onSuccess(int what, String str) {
        currentISFetchQR = false;
        progress.setVisibility(View.GONE);
        //如果当前是现金支付
        if (what == Constant.CASH_WHAT) {
            payFinish();
            return;
        }
        orderFinish = true;
        selectPayType.setVisibility(View.GONE);
        qrCode.setImageBitmap(Util.Create2DCode(str, 600, 600));
        mPresenter.requestData(Constant.LOOP_WHAT, getLoopRequestParams(), UrlComm.getInstance().QUERYURL(), tip);
    }

    @Override
    public void onPaySuccess() {
        payFinish();
    }

    //支付完成的操作
    private void payFinish() {
        payFinish = true;
        payRsult.setVisibility(View.VISIBLE);
        payRsult.setBackgroundResource(R.mipmap.ic_pay_success);
        selectPayType.setText("支付成功");
        DBManager.updatePayState(currentGasOrder);
        Tip.show(getApplicationContext(), "支付完成", true);
    }


    @Override
    public void onFail(int what, boolean isOK, String str) {
        Log.d("OrderZipActivity",
                "success(OrderZipActivity.java:244)" + what);
        currentISFetchQR = false;
        progress.setVisibility(View.GONE);
        Tip.show(getApplicationContext(), str, false);
        if (what == Constant.LOOP_WHAT && isOK) {
            tip.setText("暂未完成支付");
            qrCode.setImageResource(0);
            selectPayType.setVisibility(View.VISIBLE);
            payRsult.setVisibility(View.VISIBLE);
            payRsult.setBackgroundResource(R.mipmap.ic_pay_fail);
        } else if (what == Constant.CASH_WHAT || what == Constant.REQUESTQRCODE_WHAT) {
            selectPayType.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void back() {
        Log.d("OrderZipActivity",
                "success(OrderZipActivity.java:297)BACK…………………………………………");
        if (!payFinish && orderFinish) {
            alert.show(this, Constant.ALERT_BACK_WHAT, "提示", "页面暂未完成支付,确认是否离开?", "是", "否");
        } else
            super.back();
    }

    private void checkOrder() {
        alert.show(this, Constant.ALERT_CHECK_WHAT, "提示", "已完成支付,页面无提示?", "查询", "取消");
    }


    @Override
    public void setPositiveButton(int what) {
        switch (what) {
            case Constant.ALERT_BACK_WHAT:
                mPresenter.cancelTimerTask();
                finishActivityFromRight();
                break;
            case Constant.ALERT_CHECK_WHAT:
                mPresenter.requestData(Constant.QUERY_WHAT, getLoopRequestParams(), UrlComm.getInstance().QUERYURL());
                break;
            default:

                break;
        }

    }

}
