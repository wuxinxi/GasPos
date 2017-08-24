package com.szxb.module.bill;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.szxb.R;
import com.szxb.adapter.adapter.BIllAdapter;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.entity.BillEntity;
import com.szxb.interfaces.OnBillListener;
import com.szxb.utils.DateUtil;
import com.szxb.utils.comm.Constant;
import com.szxb.utils.tip.Tip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.yanzhenjie.nohttp.NoHttp.getContext;

/**
 * 作者: Tangren on 2017/8/18
 * 包名：com.szxb.module.bill
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/bill")
public class BillActivity2 extends BaseMvpActivity<BillPresenter> implements DatePickerDialog.OnDateSetListener,
        BillView<BillEntity.JourListBean>,  OnBillListener {

    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.startTime)
    TextView startTime;
    @BindView(R.id.endTime)
    TextView endTime;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private boolean selectStart = false;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    private Map<String, Object> map = new HashMap<>();

    private BIllAdapter mAdapter;

    private List<BillEntity.JourListBean> listBeanList = new ArrayList<>();

    @Override
    protected BillPresenter getChildPresenter() {
        return new BillPresenter(this);
    }

    @Override
    protected int layoutID() {
        return R.layout.fragment_network;
    }

    @Override
    protected void initView() {
        super.initView();
        title.setText("账单查询");
        startTime.setText(DateUtil.getCurrentDate("yyyy-MM-dd"));
        endTime.setText(DateUtil.getCurrentDate("yyyy-MM-dd"));
        calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        mAdapter = new BIllAdapter(getContext(), R.layout.view_item_bill, getList());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setPrint(this);
        mAdapter.setQueryRefund(this);
        mAdapter.setRefund(this);
//
//        BGAMoocStyleRefreshViewHolder refreshViewHolde = new BGAMoocStyleRefreshViewHolder(getApplicationContext(), true);
//        refreshViewHolde.setOriginalImage(R.mipmap.ic_launcher_round);
//        refreshViewHolde.setUltimateColor(R.color.colorPrimary);
//        refreshViewHolde.setLoadingMoreText("加载中");
//        bgaRefreshLayout.setRefreshViewHolder(refreshViewHolde);
//        bgaRefreshLayout.setDelegate(this);
    }

    @Override
    protected void initData() {
        mPresenter.requestData(Constant.BILLNORMAL, getRequestParams(), Constant.ORDER_URL);
    }

    @Override
    protected Map<String, Object> getRequestParams() {
        map.put("mchid", FetchAppConfig.mchId());
        map.put("begintime", "2017-08-21 23:00:00");
        map.put("endtime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        return map;
    }

    @OnClick({R.id.startTime, R.id.endTime, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startTime:
                selectStart = true;
                selectTime();
                break;
            case R.id.endTime:
                selectStart = false;
                selectTime();
                break;
            case R.id.search:

                break;
            default:
                break;
        }
    }

    private void selectTime() {
        datePickerDialog.setVibrate(true);
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.setCloseOnSingleTapDay(false);
        datePickerDialog.show(getSupportFragmentManager(), "TAG");
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        month = month + 1;
        if (selectStart) {
            startTime.setText(year + "-" + month + "-" + day);
        } else {
            endTime.setText(year + "-" + month + "-" + day);
        }
    }


    @Override
    public void loadSuccess(List<BillEntity.JourListBean> billLists) {
        listBeanList.addAll(billLists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadRefreshSuccess(List<BillEntity.JourListBean> billLists) {

    }

    @Override
    public void loadMoreSuccess(List<BillEntity.JourListBean> billLists) {

    }

    public List<BillEntity.JourListBean> getList() {
        List<BillEntity.JourListBean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            BillEntity.JourListBean be = new BillEntity.JourListBean();
            be.setAMOUNT(i);
            be.setHSORDERID("gas1254855585555587" + 1);
            be.setTRANTIME("2017-08-22 15:22:36");
            list.add(be);
        }
        return list;
    }


    @Override
    public void onRefund(BillEntity.JourListBean varListBean,int position) {
        Tip.show(getApplicationContext(), "position=" + position+"\n"+varListBean.toString(), true);
    }

    @Override
    public void onQueryRefund(BillEntity.JourListBean varListBean,int position) {
        Tip.show(getApplicationContext(), "position=" + position+"\n"+varListBean.toString(), true);
    }

    @Override
    public void onPrint(BillEntity.JourListBean varListBean,int position) {
        Tip.show(getApplicationContext(), "position=" + position+"\n"+varListBean.toString(), true);
    }
}
