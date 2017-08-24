package com.szxb.module.order;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.manager.DBManager;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.entity.HomeInfoEntity;
import com.szxb.interfaces.OnCloseDialogListener;
import com.szxb.utils.DateUtil;
import com.szxb.utils.Util;
import com.szxb.utils.comm.Constant;
import com.szxb.utils.tip.Tip;
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
public class OrderActivity extends BaseMvpActivity<TransactionPresenter> implements OnCloseDialogListener, OrderView {

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
    @BindView(R.id.layout)
    LinearLayout layout;

    private ImageDialog imageDialog;
    private String paytype = "1";
    private String orderNo = null;

    @Autowired
    HomeInfoEntity infoEntity;

    //当前油站的订单号
    //一笔加油信息一笔记录
    private String currentGasOrder;

    private Bitmap bitmap;

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
        gasPrice.setText("油品单价:" + infoEntity.getGasUnitPrice());
        gasCapacity.setText("加油总量:" + infoEntity.getGasCapacity() + "L");
        gasOils.setText("油品:未知");
        memberPrice.setText("会员价:无");
        transactionFlow.setText("交易流水:" + infoEntity.getGasOrderNo());
        gasTotal.setText("加油总价:" + infoEntity.getGasMoney());
        memberTotal.setText("会员价格:无");
        imageDialog = ImageDialog.getImageDialog();
        imageDialog.setCloseDialogListener(this);

        currentGasOrder = infoEntity.getGasOrderNo();

    }


    @Override
    protected Map<String, Object> getRequestParams() {
        Map<String, Object> map = new HashMap<>();
//        if (orderNo == null)
        Log.d("OrderActivity",
                "getRequestParams(OrderActivity.java:121)" + infoEntity.getGasOrderNo());

        orderNo = Util.getOrderNo();
        map.put("orderid", orderNo);
        map.put("trantype", paytype);
        map.put("paytype", paytype);
        map.put("devno", "001");
        map.put("memno", "1");//会员卡号,如果不是会员传1
        map.put("member_status", "1");//会员卡号,如果不是会员传1
        map.put("mchid", FetchAppConfig.mchId());
        map.put("mername", "小兵智能科技有限公司");
        map.put("goodname", "90#");//油品名称
        map.put("goodcode", "0092");//商品代码
        map.put("qty", "1");//加油升数
        map.put("total_fee", "0.01");//总金额
        map.put("amount", "0.01");//实际扣款
        map.put("trantime", DateUtil.getCurrentDate());//订单时间
        map.put("saletime", DateUtil.getCurrentDate());//销售时间
        map.put("class", "001");//班次
        map.put("operaterno", FetchAppConfig.userNo());//操作员编号
        map.put("salerno", "001");//销售员编号
        map.put("mchineno", "12");//加油机号
        map.put("gmchineno", "1");//加油枪号
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

    @OnClick({R.id.wechatPay, R.id.aliPay, R.id.temp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wechatPay:
                fetchRequest("1");
                break;
            case R.id.aliPay:
                fetchRequest("2");
                break;
            case R.id.temp:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("已完成支付,页面无显示?")
                        .setPositiveButton("查询", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("mch_id", FetchAppConfig.mchId());
                                map.put("out_trade_no", orderNo);
                                mPresenter.requestData(Constant.QUERY_WHAT, map, Constant.QUERY_URL);
                            }
                        })
                        .setNegativeButton("退出", null)
                        .show();
                break;
        }
    }

    //请求
    private void fetchRequest(String paytype) {
        this.paytype = paytype;
        FragmentTransaction mFragTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("img");
        if (fragment != null) {
            mFragTransaction.remove(fragment);
        }
        imageDialog.show(mFragTransaction, "img");
        mPresenter.requestData(Constant.REQUESTQRCODE_WHAT, getRequestParams(), Constant.PAY_URL);
    }

    @Override
    public void onSuccess(int what, String str) {

        bitmap = Util.CreateCode(str);
        imageDialog.setImage(bitmap);
        mPresenter.requestData(Constant.LOOP_WHAT, getLoopRequestParams(), Constant.QUERY_URL);
    }

    @Override
    public void onPaySuccess() {
        disDialog();
        DBManager.updatePayState(currentGasOrder);
        Tip.show(getApplicationContext(), "支付成功!", true);
        this.setResult(0x11);
        finishActivityFromRight();
    }

    @Override
    public void onFail(int what, String str) {
        Tip.show(getApplicationContext(), str, false);
    }

    @Override
    public void onCloseDialog(Dialog mDialog) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mPresenter.cancelTimerTask();
        }
    }

    @Override
    public void onQueryCurrentOrder(Dialog mDialog) {
        //当支付状态不明确时
        //查询此订单支付状态
        if (mPresenter.isCancleTask()) {
            //如果已经取消了轮训任务则可以手动查询
            mPresenter.requestData(Constant.QUERY_WHAT, getLoopRequestParams(), Constant.QUERY_URL);
        } else {
            Tip.show(getApplicationContext(), "正在查询中!", false);
        }

    }


    @Override
    public void onQueryResult(int code, String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null && !bitmap.isRecycled())
            bitmap.recycle();
    }

    private void disDialog() {
        if (imageDialog != null && imageDialog.isAdded()) {
            imageDialog.dismiss();
        }
    }
}
