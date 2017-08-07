package com.szxb.module.psw;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.szxb.R;
import com.szxb.base.BaseActivity;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.module.bill.BillActivity;
import com.szxb.module.setting.SettingsActivity;
import com.szxb.utils.MD5;
import com.szxb.utils.Util;
import com.szxb.utils.router.Router;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: Tangren on 2017/7/31
 * 包名：com.szxb.module.psw
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/verify")
public class VerifyActivity extends BaseActivity {

    @BindView(R.id.num_del)
    Button numDel;
    @BindView(R.id.num_determine)
    Button numDetermine;
    @BindView(R.id.psw)
    EditText psw;

    private String pswString;

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4, R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};

    @Autowired
    String activity;

    @Override
    protected int layoutID() {
        return R.layout.activity_verify;
    }

    @Override
    protected void initView() {
        super.initView();
        ARouter.getInstance().inject(this);
        title.setText("安全效验");
        for (int button : buttons) {
            Button tempButton = (Button) findViewById(button);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pswString = psw.getText().toString();
                    pswString = pswString + String.valueOf(((Button) v).getText());
                    psw.setText(pswString);
                }
            });

        }
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.home, R.id.num_del, R.id.num_determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home:
                finishActivityFromRight();
                break;
            case R.id.num_del:
                Util.delNum(psw);
                break;
            case R.id.num_determine:
                String pswString = psw.getText().toString().trim();
                if (TextUtils.equals(MD5.md5(pswString), FetchAppConfig.verfrityCode())) {
                    if (TextUtils.equals(activity, BillActivity.class.getSimpleName())) {
                        Router.jumpL("/gas/bill");
                        finish();
                    } else if (TextUtils.equals(activity, SettingsActivity.class.getSimpleName())) {
                        Router.jumpL("/gas/settings");
                        finish();
                    }
                } else Toast.makeText(this, "安全码错误", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
