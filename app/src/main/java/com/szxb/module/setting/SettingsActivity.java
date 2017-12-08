package com.szxb.module.setting;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.szxb.App;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.manager.DBManager;
import com.szxb.db.sp.CommonSharedPreferences;
import com.szxb.interfaces.OnAlertListener;
import com.szxb.utils.Alert;
import com.szxb.utils.AppUtil;
import com.szxb.utils.MD5;
import com.szxb.utils.comm.Constant;
import com.szxb.utils.comm.UrlComm;
import com.szxb.utils.tip.Tip;
import com.szxb.widget.WaitDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import szxb.com.poslibrary.manager.MyActivityLifecycleCallbacks;

/**
 * 作者: Tangren on 2017/8/3
 * 包名：com.szxb.module.setting
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/settings")
public class SettingsActivity extends BaseMvpActivity<SettingPresenter> implements
        CompoundButton.OnCheckedChangeListener, OnAlertListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.exit)
    TextView exit;
    @BindView(R.id.common_settings)
    TextView commonSettings;
    @BindView(R.id.wifi)
    TextView wifi;
    @BindView(R.id.switchCompat)
    SwitchCompat switchCompat;
    @BindView(R.id.switchMember)
    SwitchCompat switchMember;
    @BindView(R.id.update_psw)
    TextView updatePsw;
    @BindView(R.id.check_update)
    TextView checkUpdate;
    @BindView(R.id.check_emp_info)
    TextView checkEmpInfo;
    @BindView(R.id.clear_cache)
    TextView clearCache;
    @BindView(R.id.setting_title)
    TextView settingTitle;
    @BindView(R.id.old_psw)
    EditText oldPsw;
    @BindView(R.id.new_psw)
    EditText newPsw;
    @BindView(R.id.re_new_psw)
    EditText reNewPsw;
    @BindView(R.id.determine_update)
    Button determineUpdate;
    @BindView(R.id.current_tip)
    TextView currentTip;
    @BindView(R.id.current_version)
    TextView currentVersion;
    @BindView(R.id.mch_id)
    TextView mchId;
    @BindView(R.id.emp_num)
    TextView empNum;
    @BindView(R.id.ip)
    EditText IP;
    @BindView(R.id.seri_0)
    EditText seriO;
    @BindView(R.id.seri_1)
    EditText seri1;
    @BindView(R.id.determin)
    Button determin;
    @BindView(R.id.view_check_layout)
    View check_layout;
    @BindView(R.id.view_comm_layout)
    View comm_layout;
    @BindView(R.id.view_update_layout)
    View update_layout;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.radio_wechat)
    RadioButton radioWechat;
    @BindView(R.id.radio_ali)
    RadioButton radioAli;
    @BindView(R.id.radio_none)
    RadioButton radioNone;


    private Alert alert;
    private WaitDialog mDialog;

    @Override
    protected SettingPresenter getChildPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected int layoutID() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        super.initView();
        title.setText("系统设置");
    }

    @Override
    protected void initData() {

        mchId.setText(App.getPosManager().getMchID());
        empNum.setText(App.getPosManager().getUserNo());
        IP.setText(App.getPosManager().getURlIP());
        seriO.setText(App.getPosManager().getSeri0());
        seri1.setText(App.getPosManager().getSeri1());

        switchCompat.setChecked(App.getPosManager().getClearZero());
        switchMember.setChecked(App.getPosManager().getSupportMember());

        radioGroupInit();

        alert = new Alert();
        mDialog = WaitDialog.newInstance(false);

        switchCompat.setOnCheckedChangeListener(this);
        switchMember.setOnCheckedChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        alert.setAlertListener(this);
    }

    //设置默认支付方式
    private void radioGroupInit() {
        switch (App.getPosManager().getDefaultPay()) {
            case 0:
                radioGroup.check(R.id.radio_wechat);
                break;
            case 1:
                radioGroup.check(R.id.radio_ali);
                break;
            default:
                radioGroup.check(R.id.radio_none);
                break;
        }
    }

    @Override
    protected Map<String, Object> getRequestParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("appname", "gasPos");
        map.put("mchid", App.getPosManager().getMchID());
        return map;
    }

    @OnClick({R.id.exit, R.id.common_settings, R.id.wifi, R.id.update_psw, R.id.check_update,
            R.id.determine_update, R.id.determin, R.id.check_emp_info, R.id.clear_cache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_settings:
                settingTitle.setText("通用设置");
                check_layout.setVisibility(View.GONE);
                update_layout.setVisibility(View.GONE);
                comm_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.wifi:
                settingTitle.setText("网络设置");
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                break;
            case R.id.update_psw:
                settingTitle.setText("修改安全码");
                check_layout.setVisibility(View.GONE);
                update_layout.setVisibility(View.VISIBLE);
                comm_layout.setVisibility(View.GONE);
                break;
            case R.id.check_update:
                currentVersion.setText(AppUtil.getVersionName(getApplicationContext()));
                mPresenter.requestData(Constant.CHECK_WHAT, getRequestParams(), UrlComm.getInstance().CHECKVERSIONURL());
                showDialog();
                settingTitle.setText("检查更新");
                check_layout.setVisibility(View.VISIBLE);
                update_layout.setVisibility(View.GONE);
                comm_layout.setVisibility(View.GONE);
                break;
            case R.id.determine_update:
                String oldP = oldPsw.getText().toString().trim();
                String newP = newPsw.getText().toString().trim();
                String renewP = reNewPsw.getText().toString().trim();
                if (TextUtils.isEmpty(oldP) || TextUtils.isEmpty(newP) || TextUtils.isEmpty(renewP))
                    Tip.show(getApplicationContext(), "参数不能缺省!", false);
                else {
                    if (TextUtils.equals(MD5.md5(oldP), App.getPosManager().getVerifyCode())) {
                        if (TextUtils.equals(newP, renewP)) {
                            App.getPosManager().setVerifyCode(MD5.md5(newP));
                            Tip.show(getApplicationContext(), "修改成功!", true);
                        } else {
                            reNewPsw.setText("");
                            Tip.show(getApplicationContext(), "两次密码不一致!", false);
                        }
                    } else {
                        oldPsw.setText("");
                        Tip.show(getApplicationContext(), "原密码错误!", false);
                    }
                }
                break;
            case R.id.exit:
                alert.show(this, 0, "提示", "真的要退出吗?", "确定", "取消");
                break;
            case R.id.determin:
                String urlIP = IP.getText().toString();
                String seri_0 = seriO.getText().toString();
                String seri_1 = seri1.getText().toString();
                if (TextUtils.isEmpty(urlIP) || TextUtils.isEmpty(seri_0) || TextUtils.isEmpty(seri_1)) {
                    Tip.show(getApplicationContext(), "参数缺省!", false);
                    return;
                }
                CommonSharedPreferences.put("ip", urlIP);
                App.getPosManager().setURLIP(urlIP);
                CommonSharedPreferences.put("seri_0", seri_0);
                App.getPosManager().setSeri0(seri_0);
                CommonSharedPreferences.put("seri_1", seri_1);
                App.getPosManager().setSeri1(seri_1);
                Tip.show(getApplicationContext(), "修改成功", true);
                break;
            case R.id.check_emp_info:
                showDialog();
                Map<String, Object> map = new HashMap<>();
                map.put("mchid", App.getPosManager().getMchID());
                mPresenter.requestData(Constant.UPDATE_EMP, map, UrlComm.getInstance().EMPLIST());
                break;
            case R.id.clear_cache:
                Tip.show(getApplicationContext(), "开始清除", true);
                DBManager.deleteAll();
                break;
            default:

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchCompat:
                App.getPosManager().setClearZero(isChecked);
                break;
            case R.id.switchMember:
                App.getPosManager().setSupportMember(isChecked);
                break;
            default:

                break;
        }
    }

    @Override
    public void setPositiveButton(int what) {
        MyActivityLifecycleCallbacks.appExit();
    }

    @Override
    public void setNegativeButton(int what) {

    }

    @Override
    public void onSuccess(int what, String str) {
        closeDialog();
        switch (what) {
            case Constant.CHECK_WHAT:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + str), "application/vnd.android.package-archive");
                startActivity(intent);
                break;
            case Constant.UPDATE_EMP:
                Tip.show(getApplicationContext(), "员工信息更新成功", true);
                break;
            default:

                break;
        }

    }

    @Override
    public void onFail(int what, boolean isOK, String str) {
        closeDialog();
        switch (what) {
            case Constant.CHECK_WHAT:
                Tip.show(getApplicationContext(), str, false);
                currentTip.setText(str);
                currentVersion.setText(AppUtil.getVersionName(getApplicationContext()));
                break;
            case Constant.UPDATE_EMP:
                Tip.show(getApplicationContext(), "员工信息更新失败\n请重试\n[" + str + "]", false);
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
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_wechat:
                App.getPosManager().setDefaultPay(0);
                break;
            case R.id.radio_ali:
                App.getPosManager().setDefaultPay(1);
                break;
            case R.id.radio_none:
                App.getPosManager().setDefaultPay(2);
                break;
            default:

                break;
        }
    }
}
