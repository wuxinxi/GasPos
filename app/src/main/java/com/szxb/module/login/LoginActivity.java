package com.szxb.module.login;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.sp.CommonSharedPreferences;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.utils.MD5;
import com.szxb.utils.comm.Constant;
import com.szxb.utils.router.Router;
import com.szxb.utils.tip.Tip;
import com.szxb.widget.MemberLoginDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.szxb.utils.Config.REQUESTQRCODE_WHAT;

/**
 * 作者: Tangren on 2017/6/27
 * 包名：com.szxb.module.login
 * 邮箱：996489865@qq.com.com
 * TODO:登录
 */
@Route(path = "/gas/login")
public class LoginActivity extends BaseMvpActivity<LoginPresenter> {

    @BindView(R.id.userNo)
    EditText userNo;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userPsw)
    EditText userPsw;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.buttonClose)
    Button buttonClose;
    @BindView(R.id.progress1)
    CircleProgressBar progressBar;

    private String user;
    private String psw;
    private String no;

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
        //super.initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    protected void initData() {

        userName.setText(FetchAppConfig.mchId());
        mDialog = new MemberLoginDialog(this);
    }

    MemberLoginDialog mDialog;

    @OnClick({R.id.buttonLogin, R.id.buttonClose})
    public void onViewClicked(View view) {

        no = userNo.getText().toString();
        user = userName.getText().toString();
        psw = userPsw.getText().toString();

        switch (view.getId()) {
            case R.id.buttonLogin:
                if (TextUtils.isEmpty(no) || TextUtils.isEmpty(user) || TextUtils.isEmpty(psw)) {
                    Tip.show(getApplicationContext(), "编号、商户号或密码不能为空", false);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    userPsw.setText(MD5.md5(psw));
                    userPsw.setSelection(userPsw.getText().length());
                    setEnable(false);
                    Map<String, Object> map = new HashMap<>();
                    map.put("mchid", user);
                    map.put("password", psw);
                    map.put("operaterno", no);
                    mPresenter.requestData(REQUESTQRCODE_WHAT, map, Constant.LOGIN_URL);
                }
                break;
            case R.id.buttonClose:
                finishActivityFromRight();
                break;
            default:
                break;
        }
    }

    private void setEnable(boolean enable) {
        buttonLogin.setEnabled(enable);
        userPsw.setEnabled(enable);
        userName.setEnabled(enable);
    }

    @Override
    public void onSuccess(int what,String str) {
        CommonSharedPreferences.put("mch_id", user);
        CommonSharedPreferences.put("user_no", no);
        progressBar.setVisibility(View.GONE);

        Router.jumpL("/gas/home");
        finish();
    }

    @Override
    public void onFail(int what,String str) {
        userPsw.setText("");
        setEnable(true);
        progressBar.setVisibility(View.GONE);
        Tip.show(getApplicationContext(), str, false);
    }

}
