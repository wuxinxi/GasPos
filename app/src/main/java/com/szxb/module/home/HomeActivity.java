package com.szxb.module.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.szxb.R;
import com.szxb.adapter.adapter.HomeAdapter;
import com.szxb.base.BaseActivity;
import com.szxb.db.dao.HomeInfoEntityDao;
import com.szxb.db.manager.DBCore;
import com.szxb.db.manager.DBManager;
import com.szxb.entity.HomeInfoEntity;
import com.szxb.interfaces.OnItemClick;
import com.szxb.module.bill.BillActivity;
import com.szxb.module.setting.SettingsActivity;
import com.szxb.task.TaskRotationService;
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

/**
 * 作者: Tangren on 2017/7/30
 * 包名：com.szxb.module.home
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/home")
public class HomeActivity extends BaseActivity implements OnItemClick {

    Intent intent;
    Subscription sub;
    Subscription subscription;
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

    @BindView(R.id.currentCheckView)
    Button currentCheckView;
    @BindView(R.id.checkBackView)
    Button checkBackView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private HomeAdapter mAdapter;

    private List<HomeInfoEntity> infoEntitiesList = new ArrayList<>();

    private int selectPosition = 0;

    private int mCurrentPosition = -1;

    //Activity的状态，是否在前台
    private boolean isVisible = true;

    private HomeInfoEntity infoEntity = null;


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
        query(5);

    }

    @Override
    protected void initData() {

        sub = RxBus.getInstance().toObservable(HomeInfoEntity.class)
                .filter(new Func1<HomeInfoEntity, Boolean>() {
                    @Override
                    public Boolean call(HomeInfoEntity infoEntity) {
                        //如果数据库不存在则往下走储存到数据库，否则直接过滤
                        return DBManager.query(infoEntity);
                    }
                }).flatMap(new Func1<HomeInfoEntity, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(HomeInfoEntity infoEntity) {
                        HomeInfoEntityDao homeInfoEntityDao = DBCore.getDaoSession().getHomeInfoEntityDao();
                        return Observable.just(homeInfoEntityDao.insert(infoEntity));
                    }
                }).map(new Func1<Long, List<HomeInfoEntity>>() {
                    @Override
                    public List<HomeInfoEntity> call(Long aLong) {
                        return DBManager.query();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HomeInfoEntity>>() {
                    @Override
                    public void call(List<HomeInfoEntity> infoEntities) {
                        //当前选择为实时时方可实时更新UI
                        mAdapter.setItemChecked(-1);
                        infoEntity = null;
                        //如果选择的是实时记录并且当前Activity在前台显示则更新UI
                        if (selectPosition == 0 && isVisible) {
                            infoEntitiesList.clear();
                            infoEntitiesList.addAll(0, infoEntities);
                            mAdapter.notifyDataSetChanged();
                        }
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
    @OnClick({R.id.queryRecord, R.id.setting, R.id.scanNoMember, R.id.scanMember, R.id.currentCheck, R.id.checkBack})
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
                if (infoEntity == null)
                    Tip.show(getApplicationContext(), getResources().getString(R.string.chose_tip), false);
                else {
                    ARouter.getInstance().build("/gas/order")
                            .withBoolean("status", false)
                            .withParcelable("infoEntity", infoEntity)
                            .navigation(this, 0x11);
                }
                break;
            case R.id.scanMember:
                if (infoEntity == null)
                    Tip.show(getApplicationContext(), getResources().getString(R.string.chose_tip), false);
                else {
                    ARouter.getInstance().build("/gas/member")
                            .withBoolean("status", true)
                            .withParcelable("infoEntity", infoEntity)
                            .navigation(this, 0x11);
                }
                break;
            case R.id.currentCheck:
                selectPosition(0);
                query(5);
                break;
            case R.id.checkBack:
                selectPosition(1);
                query(20);
                break;
            case R.id.queryRecord:
                ARouter.getInstance()
                        .build("/gas/verify")
                        .withString("activity", BillActivity.class.getSimpleName())
                        .withTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain)
                        .navigation();
                break;
            default:
                break;
        }
    }

    /**
     * limit=20:回查20本地条记录
     * limit=5:实时信息
     */
    private void query(final int limit) {
        Observable.create(new Observable.OnSubscribe<List<HomeInfoEntity>>() {
            @Override
            public void call(Subscriber<? super List<HomeInfoEntity>> subscriber) {
                HomeInfoEntityDao dao = DBCore.getDaoSession().getHomeInfoEntityDao();
                List<HomeInfoEntity> list = dao.queryBuilder()
                        .orderDesc(HomeInfoEntityDao.Properties.Id)
                        .limit(limit)
                        .build()
                        .list();
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HomeInfoEntity>>() {
                    @Override
                    public void call(List<HomeInfoEntity> infoEntities) {
                        infoEntitiesList.clear();
                        infoEntitiesList.addAll(0, infoEntities);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        XBLog.d("call(HomeActivity.java:228)" + throwable.getMessage());
                    }
                });
    }

    private void selectPosition(int position) {
        switch (position) {
            case 0:
                selectPosition = 0;
                currentCheckView.setBackgroundResource(R.drawable.button_selected_left_shape);
                checkBackView.setBackgroundResource(R.drawable.button_selected_left_shape_normal);
                break;
            case 1:
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
                query(5);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isVisible = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (intent != null) {
            stopService(intent);
        }

        if (!sub.isUnsubscribed()&&!sub.isUnsubscribed()) {
            sub.unsubscribe();
        }
    }


}
