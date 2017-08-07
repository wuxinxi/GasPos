package com.szxb.module.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.szxb.R;
import com.szxb.base.BaseActivity;

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

    }

    @OnClick({R.id.exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
