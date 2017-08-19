package com.szxb.module.bill;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.szxb.R;
import com.szxb.adapter.adapter.BIllAdapter;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.entity.BillEntity;
import com.szxb.utils.Config;
import com.szxb.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 作者: Tangren on 2017/8/18
 * 包名：com.szxb.module.bill
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BillActivity2 extends BaseMvpActivity<BillPresenter> implements DatePickerDialog.OnDateSetListener, BillView<BillEntity.VarListBean> {

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

    private List<BillEntity.VarListBean> listBeanList = new ArrayList<>();

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
        startTime.setText(DateUtil.getCurrentDate("yyyy-MM-dd"));
        endTime.setText(DateUtil.getCurrentDate("yyyy-MM-dd"));
        calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);

        map.put("MCHID", FetchAppConfig.mchId());

//        map = new BIllAdapter(getContext(), )
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        map.put("BEGINTIME", DateUtil.getCurrentDate("yyyy-MM-dd"));
        map.put("ENDTIME", DateUtil.getCurrentDate("yyyy-MM-dd"));
        mPresenter.requestData(Config.BILLNORMAL, map, Config.QUERY_URL);
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
    public void loadSuccess(List<BillEntity.VarListBean> billLists) {

    }

    @Override
    public void loadRefreshSuccess(List<BillEntity.VarListBean> billLists) {

    }

    @Override
    public void loadMoreSuccess(List<BillEntity.VarListBean> billLists) {

    }
}
