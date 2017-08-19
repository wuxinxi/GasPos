package com.szxb.module.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.szxb.R;
import com.szxb.base.BaseActivity;
import com.szxb.db.sp.CommonSharedPreferences;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.utils.MD5;
import com.szxb.utils.tip.Tip;

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
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.exit)
    TextView exit;
    @BindView(R.id.common_settings)
    TextView commonSettings;
    @BindView(R.id.switchCompat)
    SwitchCompat switchCompat;
    @BindView(R.id.update_psw)
    TextView updatePsw;
    @BindView(R.id.check_update)
    TextView checkUpdate;
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
    @BindView(R.id.view_check_layout)
    View check_layout;
    @BindView(R.id.view_comm_layout)
    View comm_layout;
    @BindView(R.id.view_update_layout)
    View update_layout;

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
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Tip.show(getApplicationContext(), isChecked + "", true);
            }
        });
    }

    @OnClick({R.id.exit, R.id.common_settings, R.id.update_psw, R.id.check_update, R.id.determine_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_settings:
                settingTitle.setText("通用设置");
                check_layout.setVisibility(View.GONE);
                update_layout.setVisibility(View.GONE);
                comm_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.update_psw:
                settingTitle.setText("修改安全码");
                check_layout.setVisibility(View.GONE);
                update_layout.setVisibility(View.VISIBLE);
                comm_layout.setVisibility(View.GONE);
                break;
            case R.id.check_update:
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
                    if (TextUtils.equals(MD5.md5(oldP), FetchAppConfig.verifyCode())) {
                        if (TextUtils.equals(newP, renewP)) {
                            CommonSharedPreferences.put("verifyCode", MD5.md5(newP));
                            Tip.show(getApplicationContext(), "修改成功!", true);
                        } else {
                            reNewPsw.setText("");
                            Tip.show(getApplicationContext(), "两次密码不一致!", false);
                        }
                    }else   Tip.show(getApplicationContext(), "原密码错误!", false);
                }
                break;
            case R.id.exit:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("真的要退出吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyActivityLifecycleCallbacks.appExit();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            default:

                break;
        }
    }

}
