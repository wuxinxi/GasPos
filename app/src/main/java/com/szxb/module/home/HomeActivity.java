package com.szxb.module.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.szxb.App;
import com.szxb.R;
import com.szxb.adapter.adapter.HomeAdapter;
import com.szxb.base.BaseActivity;
import com.szxb.db.dao.SeriaInformationDao;
import com.szxb.db.manager.DBCore;
import com.szxb.db.manager.DBManager;
import com.szxb.entity.SeriaInformation;
import com.szxb.interfaces.OnItemClick;
import com.szxb.module.bill.BillActivity2;
import com.szxb.module.login.LoginZipActivity;
import com.szxb.module.setting.SettingsActivity;
import com.szxb.task.TaskRotationService;
import com.szxb.utils.TestUtil;
import com.szxb.utils.rx.RxBus;
import com.szxb.utils.tip.Tip;
import com.szxb.xblog.XBLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import szxb.com.poslibrary.manager.MyActivityLifecycleCallbacks;

/**
 * 作者: Tangren on 2017/7/30
 * 包名：com.szxb.module.home
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/home")
public class HomeActivity extends BaseActivity implements OnItemClick, View.OnLongClickListener {

    Intent intent;
    Subscription sub;
    @BindView(R.id.queryRecord)
    Button queryRecord;
    @BindView(R.id.setting)
    Button setting;
    @BindView(R.id.scanNoMember)
    Button scanNoMember;
    @BindView(R.id.scanMember)
    Button scanMember;

    @BindView(R.id.currentCheck)
    Button currentCheck;
    @BindView(R.id.checkBack)
    Button checkBack;
    @BindView(R.id.first_short_code)
    TextView firstShortCode;
    @BindView(R.id.second_short_code)
    TextView secondShortCode;
    @BindView(R.id.three_short_code)
    TextView threeShortCode;

    @BindView(R.id.currentCheckView)
    Button currentCheckView;
    @BindView(R.id.checkBackView)
    Button checkBackView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.short_layout)
    LinearLayout shortLayout;

    private HomeAdapter mAdapter;

    private List<SeriaInformation> infoEntitiesList = new ArrayList<>();

    private int selectPosition = 0;
    private int mCurrentPosition = -1;

    //Activity的状态，是否在前台
    private boolean isVisible = true;
    private SeriaInformation infoEntity = null;
    private static Activity activity;

    //短码付选择，1,2,3
    private int SHORT_TEXT_SELECT = -1;

    @Override
    protected int layoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        //没有toolbar所以要注释掉父类的initView
        //super.initView();
        intent = new Intent(HomeActivity.this, TaskRotationService.class);
        startService(intent);
        mAdapter = new HomeAdapter(getApplicationContext(), R.layout.view_item_home, infoEntitiesList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setItemClick(this);
        query(3);

        firstShortCode.setOnLongClickListener(this);
        secondShortCode.setOnLongClickListener(this);
        threeShortCode.setOnLongClickListener(this);

        activity = this;

        checkeMemberOpen();

        MyActivityLifecycleCallbacks.finishActivityClass(LoginZipActivity.class);
    }

    //检查是否开启二维码会员支付功能
    private void checkeMemberOpen() {
        if (App.getPosManager().getSupportMember())
            scanMember.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        else scanMember.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.mipmap.ic_lock);
    }

    @Override
    protected void initData() {
        sub = RxBus.getInstance().toObservable(SeriaInformation.class)
                .filter(new Func1<SeriaInformation, Boolean>() {
                    @Override
                    public Boolean call(SeriaInformation infoEntity) {
                        //如果数据库不存在则往下走储存到数据库，否则直接过滤
                        Log.d("HomeActivity",
                                "call(HomeActivity.java:143)" + infoEntity.toString());
                        return DBManager.query(infoEntity);
                    }
                }).flatMap(new Func1<SeriaInformation, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(SeriaInformation infoEntity) {
                        SeriaInformationDao homeInfoEntityDao = DBCore.getDaoSession().getSeriaInformationDao();
                        return Observable.just(homeInfoEntityDao.insert(infoEntity));
                    }
                }).map(new Func1<Long, List<SeriaInformation>>() {
                    @Override
                    public List<SeriaInformation> call(Long aLong) {
                        return DBManager.query();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SeriaInformation>>() {
                    @Override
                    public void call(List<SeriaInformation> infoEntities) {
                        //当前选择为实时时方可实时更新UI
                        mCurrentPosition = -1;
                        mAdapter.setItemChecked(-1);
                        infoEntity = null;
                        //如果选择的是实时记录并且当前Activity在前台显示则更新UI

                        Log.d("HomeActivity",
                                "call(HomeActivity.java:168)selectPosition=" + selectPosition);

                        Log.d("HomeActivity",
                                "call(HomeActivity.java:171)isVisible=" + isVisible);

                        if (selectPosition == 0 && isVisible) {
                            Log.d("HomeActivity",
                                    "call(HomeActivity.java:168)更新UI");
                            infoEntitiesList.clear();
                            infoEntitiesList.addAll(0, infoEntities);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("HomeActivity",
                                "call(HomeActivity.java:185)" + throwable.toString());
                    }
                });

    }

    /**
     * queryRecord:交易记录
     * setting：设置
     * qrCodePay：扫码支付
     * currentCheck：实时记录
     * checkBack：本地回查
     *
     * @param view
     */
    @OnClick({R.id.queryRecord, R.id.setting, R.id.scanNoMember, R.id.scanMember, R.id.currentCheck,
            R.id.checkBack, R.id.first_short_code, R.id.second_short_code, R.id.three_short_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting:
                ARouter.getInstance()
                        .build("/gas/verify")
                        .withString("activity", SettingsActivity.class.getSimpleName())
                        .withTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain)
                        .navigation();
                break;
            case R.id.scanNoMember:
                TestUtil.getInstance().send();
                break;
            case R.id.scanMember:
                //会员
                if (App.getPosManager().getSupportMember())
                    nextActivity("/gas/member", 0x11);
                else Tip.show(getApplicationContext(), "请先开启会员支付功能", false);
                break;
            case R.id.currentCheck:
                selectPosition(0);
                query(3);
                break;
            case R.id.checkBack:
                selectPosition(1);
                query(20);
                break;
            case R.id.queryRecord:
                ARouter.getInstance()
                        .build("/gas/verify")
                        .withString("activity", BillActivity2.class.getSimpleName())
                        .withTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain)
                        .navigation();
                break;
            case R.id.first_short_code:
                SHORT_TEXT_SELECT = 1;
                nextActivity("/gas/short", 0x12, firstShortCode.getText().toString());
                break;
            case R.id.second_short_code:
                SHORT_TEXT_SELECT = 2;
                nextActivity("/gas/short", 0x12, secondShortCode.getText().toString());
                break;
            case R.id.three_short_code:
                SHORT_TEXT_SELECT = 3;
                nextActivity("/gas/short", 0x12, threeShortCode.getText().toString());
                break;
            default:
                break;
        }
    }

    private void nextActivity(String url, int requestCode) {
        if (infoEntity == null) {
            Tip.show(getApplicationContext(), getResources().getString(R.string.chose_tip), false);
            return;
        }
        ARouter.getInstance().build(url)
                .withParcelable("infoEntity", infoEntity)
                .navigation(HomeActivity.this, requestCode);
    }

    private void nextActivity(String url, int requestCode, String shortCode) {
        ARouter.getInstance().build(url)
                .withString("short_code", shortCode)
                .withParcelable("infoEntity", infoEntity)
                .navigation(HomeActivity.this, requestCode);
        Log.d("HomeActivity",
                "nextActivity(HomeActivity.java:242)" + SHORT_TEXT_SELECT);
    }

    /**
     * limit=20:回查20本地条记录
     * limit=5:实时信息
     */
    private void query(final int limit) {
        Observable.create(new Observable.OnSubscribe<List<SeriaInformation>>() {
            @Override
            public void call(Subscriber<? super List<SeriaInformation>> subscriber) {
                SeriaInformationDao dao = DBCore.getDaoSession().getSeriaInformationDao();
                List<SeriaInformation> list = dao.queryBuilder()
                        .orderDesc(SeriaInformationDao.Properties.Id)
                        .limit(limit)
                        .build()
                        .list();
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SeriaInformation>>() {
                    @Override
                    public void call(List<SeriaInformation> infoEntities) {
                        infoEntitiesList.clear();
                        infoEntitiesList.addAll(0, infoEntities);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Tip.show(getApplicationContext(), throwable.toString(), false);
                        XBLog.d("call(HomeActivity.java:237)" + throwable.getMessage());
                    }
                });
    }

    private void selectPosition(int position) {
        mAdapter.setItemChecked(-1);
        mCurrentPosition = -1;
        infoEntity = null;
        switch (position) {
            case 0:
                shortLayout.setVisibility(View.VISIBLE);
                selectPosition = 0;
                currentCheckView.setBackgroundResource(R.drawable.button_selected_left_shape);
                checkBackView.setBackgroundResource(R.drawable.button_selected_left_shape_normal);

                break;
            case 1:
                shortLayout.setVisibility(View.GONE);
                selectPosition = 1;
                currentCheckView.setBackgroundResource(R.drawable.button_selected_left_shape_normal);
                checkBackView.setBackgroundResource(R.drawable.button_selected_left_shape);
                break;
            default:
                currentCheckView.setBackgroundResource(R.drawable.button_selected_left_shape);
                checkBackView.setBackgroundResource(R.drawable.button_selected_left_shape_normal);
                break;
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == mCurrentPosition) {
            return;
        }
        mCurrentPosition = position;
        mAdapter.setItemChecked(position);
        infoEntity = infoEntitiesList.get(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x11:
                selectPosition(0);
                mAdapter.notifyDataSetChanged();
                break;
            case 0x12:
                selectPosition(0);
                mAdapter.notifyDataSetChanged();
                if (data != null) {
                    switch (SHORT_TEXT_SELECT) {
                        case 1:
                            firstShortCode.setText(data.getStringExtra("short_code"));
                            break;
                        case 2:
                            secondShortCode.setText(data.getStringExtra("short_code"));
                            break;
                        case 3:
                            threeShortCode.setText(data.getStringExtra("short_code"));
                            break;
                        default:

                            break;
                    }
                }
                break;
            default:
                break;

        }
    }

    public static Activity getThis() {
        return activity;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("HomeActivity",
                "onPause(HomeActivity.java:362)onPause");
        mCurrentPosition = -1;
        isVisible = false;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("HomeActivity",
                "onDestroy(HomeActivity.java:372)onRestart");
        isVisible = true;
        selectPosition(0);
        checkeMemberOpen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("HomeActivity",
                "onDestroy(HomeActivity.java:376)onDestroy");
        if (intent != null) {
            stopService(intent);
        }
        if (!sub.isUnsubscribed()) {
            sub.unsubscribe();
        }
    }


    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.first_short_code:
                firstShortCode.setText("");
                break;
            case R.id.second_short_code:
                secondShortCode.setText("");
                break;
            case R.id.three_short_code:
                threeShortCode.setText("");
                break;
            default:

                break;
        }
        return true;
    }
}
