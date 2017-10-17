package com.szxb.module.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.szxb.App;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.utils.MD5;
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

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};
    private int selectPostion;
    private String ip;
    private String user;
    private String psw;
    private String no;

    private WaitDialog mDialog;

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
        if (App.getPosManager().getFirstStart()) {
            selectPostion = 0;
            mchIp.setVisibility(View.VISIBLE);
            mchIp.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ip, 0, R.mipmap.hline, 0);
        } else {
            selectPostion = 1;
            mchIp.setVisibility(View.GONE);
            userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, R.mipmap.hline, 0);
        }
        for (int button : buttons) {
            Button tempButton = (Button) findViewById(button);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initEdit((Button) v);
                }
            });
        }


//        if (!App.getPosManager().getInitK21()) {
//            Observable.create(new Observable.OnSubscribe<Integer>() {
//                @Override
//                public void call(Subscriber<? super Integer> subscriber) {
//                    AssetManager ass = App.getInstance().getAssets();
//                    int k = libszxb.ymodemUpdate(ass, "Q6_K21_171009110026.bin");
//                    subscriber.onNext(k);
//                }
//            }).observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Action1<Integer>() {
//                        @Override
//                        public void call(Integer integer) {
//                            App.getPosManager().initK21(true);
//                            Toast.makeText(LoginZipActivity.this, "固件更新成功", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//        }
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
        userName.setText(FetchAppConfig.mchId());
        mDialog = new WaitDialog();
    }

    @OnClick({R.id.ip, R.id.userName, R.id.userNo, R.id.userPsw, R.id.login, R.id.num_determine, R.id.num_del})
    public void onViewClicked(View view) {

        ip = mchIp.getText().toString();
        no = userNo.getText().toString();
        user = userName.getText().toString();
        psw = userPsw.getText().toString();

        switch (view.getId()) {
            case R.id.ip:
                selectPostion = 0;
                mchIp.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ip, 0, R.mipmap.hline, 0);
                userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, 0, 0);
                userNo.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userno, 0, 0, 0);
                userPsw.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userpsw, 0, 0, 0);
                break;
            case R.id.userName:
                selectPostion = 1;
                mchIp.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ip, 0, 0, 0);
                userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, R.mipmap.hline, 0);
                userNo.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userno, 0, 0, 0);
                userPsw.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userpsw, 0, 0, 0);
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

                        Log.d("LoginZipActivity",
                                "onViewClicked(LoginZipActivity.java:180)" + UrlComm.getInstance().LOGINURL());
                        Log.d("LoginZipActivity",
                                "onViewClicked(LoginZipActivity.java:183)" + mchIp.getText().toString());
                    }
                } else {
                    if (TextUtils.isEmpty(no) || TextUtils.isEmpty(user) || TextUtils.isEmpty(psw)) {
                        Tip.show(getApplicationContext(), "参数缺省!", false);
                        return;
                    }
                }
                showDialog();
                userPsw.setText(MD5.md5(psw));
                Map<String, Object> map = new HashMap<>();
                map.put("mchid", user);
                map.put("operaterno", no);
                map.put("password", psw);
                mPresenter.requestData(Constant.LOGIN_WHAT, map, UrlComm.getInstance().LOGINURL());

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
            default:
                break;
        }
    }


    @Override
    public void onSuccess(int what, String str) {
        closeDialog();
        App.getPosManager().setMchID(user);
        App.getPosManager().setUserNo(no);
        App.getPosManager().setFirstStart(false);
        Router.jumpL("/gas/home");
    }

    @Override
    public void onFail(int what, boolean isOK, String str) {
        userPsw.setText("");
        closeDialog();
        Tip.show(getApplicationContext(), str, false);
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
