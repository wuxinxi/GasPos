package com.szxb.module.login;

import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.szxb.App;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.jni.libszxb;
import com.szxb.utils.AppUtil;
import com.szxb.utils.Util;
import com.szxb.utils.comm.Constant;
import com.szxb.utils.comm.UrlComm;
import com.szxb.utils.router.Router;
import com.szxb.utils.tip.Tip;
import com.szxb.widget.WaitDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 作者: Tangren on 2017/6/27
 * 包名：com.szxb.module.login
 * 邮箱：996489865@qq.com.com
 * TODO:登录
 */
@Route(path = "/gas/login")
public class LoginZipActivity extends BaseMvpActivity<LoginPresenter> {

    @BindView(R.id.num_del)
    Button numDel;
    @BindView(R.id.num_determine)
    Button numDetermine;
    @BindView(R.id.userNo)
    TextView userNo;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userPsw)
    TextView userPsw;
    @BindView(R.id.ip)
    TextView mchIp;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.next)
    ImageView next;

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};
    private int selectPostion;
    private String ip;
    private String user;
    private String psw;
    private String no;

    private WaitDialog mDialog;
    //是否显示IP
    private boolean isShow = false;

    @Override
    protected LoginPresenter getChildPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_login;
    }


    @Override
    protected void initView() {
        //没有toolbar所以要注释掉父类的initView
        super.initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        title.setText("登录");
        numDetermine.setText(".");
        for (int button : buttons) {
            Button tempButton = (Button) findViewById(button);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initEdit((Button) v);
                }
            });
        }
        select1();
////
//        String str = "1a002d000000010000003b0088002017120615050411000419901000001629005ef8cc00138800138867000004006813130272102100031f0005192e20171206150534";
//        byte[] recv = com.szxb.module.login.Util.hexStringToBytes(str);
//
//        UnpackUtil.unpack(recv);
    }


    private void updateK21() {
        final String versionName = AppUtil.getVersionName(getApplicationContext());
        if (!TextUtils.equals(versionName, App.getPosManager().getVersionName())) {
            showDialog();
            Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Tip.show(getApplicationContext(), "固件更新中……", true);
                        }
                    });
                    AssetManager ass = App.getInstance().getAssets();
                    int k = libszxb.ymodemUpdate(ass, "Q6_K21_171014163008.bin");
                    subscriber.onNext(k);
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            mDialog.disDialog();
                            App.getPosManager().setVersionName(versionName);
                            Tip.show(getApplicationContext(), "固件更新成功", true);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mDialog.disDialog();
                            Tip.show(getApplicationContext(), "固件更新失败", false);
                        }
                    });
        }
    }

    private void initEdit(Button v) {
        switch (selectPostion) {
            case 0:
                ip = mchIp.getText().toString().trim();
                ip = ip + String.valueOf(v.getText());
                mchIp.setText(ip);
                break;
            case 1:
                user = userName.getText().toString().trim();
                user = user + String.valueOf(v.getText());
                userName.setText(user);
                break;
            case 2:
                no = userNo.getText().toString().trim();
                no = no + String.valueOf(v.getText());
                userNo.setText(no);
                break;
            case 3:
                psw = userPsw.getText().toString().trim();
                psw = psw + String.valueOf(v.getText());
                userPsw.setText(psw);
                break;
        }
    }

    @Override
    protected void initData() {
        userName.setText(App.getPosManager().getMchID());
        mchIp.setText(App.getPosManager().getURlIP());
        userNo.setText(App.getPosManager().getUserNo());

        mDialog = WaitDialog.newInstance(false);
        updateK21();
    }


    @OnClick({R.id.ip, R.id.userName, R.id.userNo,
            R.id.userPsw, R.id.login, R.id.num_determine,
            R.id.num_del, R.id.next})
    public void onViewClicked(View view) {

        ip = mchIp.getText().toString();
        no = userNo.getText().toString();
        user = userName.getText().toString();
        psw = userPsw.getText().toString();

        switch (view.getId()) {
            case R.id.ip:
                select0();
                break;
            case R.id.userName:
                select1();
                break;
            case R.id.userNo:
                selectPostion = 2;
                mchIp.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ip, 0, 0, 0);
                userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, 0, 0);
                userNo.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userno, 0, R.mipmap.hline, 0);
                userPsw.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userpsw, 0, 0, 0);
                break;
            case R.id.userPsw:
                selectPostion = 3;
                mchIp.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ip, 0, 0, 0);
                userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, 0, 0);
                userNo.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userno, 0, 0, 0);
                userPsw.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userpsw, 0, R.mipmap.hline, 0);
                break;
            case R.id.login:
                if (App.getPosManager().getFirstStart()) {
                    if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(no) || TextUtils.isEmpty(user) || TextUtils.isEmpty(psw)) {
                        Tip.show(getApplicationContext(), "参数缺省!", false);
                        return;
                    } else {
                        App.getPosManager().setURLIP(mchIp.getText().toString());
                    }
                } else {
                    if (TextUtils.isEmpty(no) || TextUtils.isEmpty(user) || TextUtils.isEmpty(psw)) {
                        Tip.show(getApplicationContext(), "参数缺省!", false);
                        return;
                    }
                }
                showDialog();
                App.getPosManager().setURLIP(mchIp.getText().toString());
                Map<String, Object> map = new HashMap<>();
                map.put("mchid", user);
                map.put("operaterno", no);
                map.put("password", psw);
                mPresenter.requestData(Constant.LOGIN_WHAT, map, UrlComm.getInstance().LOGINURL());
                break;
            case R.id.num_determine:
                if (selectPostion == 0) {
                    ip = mchIp.getText().toString() + ".";
                    mchIp.setText(ip);
                }
                break;
            case R.id.num_del:
                switch (selectPostion) {
                    case 0:
                        Util.delNum(mchIp);
                        break;
                    case 1:
                        Util.delNum(userName);
                        break;
                    case 2:
                        Util.delNum(userNo);
                        break;
                    case 3:
                        Util.delNum(userPsw);
                        break;
                }
                break;
            case R.id.next:
                isShow = !isShow;
                mchIp.setVisibility(isShow ? View.VISIBLE : View.GONE);
                if (!isShow) {
                    select1();
                } else {
                    select0();
                }
                break;
            default:
                break;
        }
    }

    private void select1() {
        selectPostion = 1;
        mchIp.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ip, 0, 0, 0);
        userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, R.mipmap.hline, 0);
        userNo.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userno, 0, 0, 0);
        userPsw.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userpsw, 0, 0, 0);
    }

    private void select0() {
        selectPostion = 0;
        mchIp.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ip, 0, R.mipmap.hline, 0);
        userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, 0, 0);
        userNo.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userno, 0, 0, 0);
        userPsw.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userpsw, 0, 0, 0);
    }


    @Override
    public void onSuccess(int what, String str) {

        switch (what) {
            case Constant.LOGIN_WHAT:
                Tip.show(getApplicationContext(), "登录成功\n正在更新参数", true);
                App.getPosManager().setMchID(user);
                App.getPosManager().setUserNo(no);
                App.getPosManager().setURLIP(ip);
                App.getPosManager().setFirstStart(false);

                Map<String, Object> map = new HashMap<>();
                map.put("mchid", user);
                mPresenter.requestData(Constant.UPDATE_EMP, map, UrlComm.getInstance().EMPLIST());
                break;
            case Constant.UPDATE_EMP:
                Tip.show(getApplicationContext(), "更新参数成功", true);
                Router.jumpL("/gas/home");
                break;
            default:

                break;
        }

    }

    @Override
    public void onFail(int what, boolean isOK, String str) {
        switch (what) {
            case Constant.LOGIN_WHAT:
                userPsw.setText("");
                Tip.show(getApplicationContext(), str, false);
                break;
            case Constant.UPDATE_EMP:
                Tip.show(getApplicationContext(), "参数更新失败", false);
                Router.jumpL("/gas/home");
                break;
            default:

                break;
        }

        closeDialog();
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
            mPresenter.cancelBySign(Constant.LOGIN_WHAT);
            mDialog.disDialog();
        }
    }

}
