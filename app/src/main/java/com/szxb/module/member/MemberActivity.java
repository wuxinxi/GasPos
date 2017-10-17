package com.szxb.module.member;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.db.sp.FetchAppConfig;
import com.szxb.entity.SeriaInformation;
import com.szxb.utils.Util;
import com.szxb.utils.comm.Constant;
import com.szxb.utils.comm.UrlComm;
import com.szxb.utils.tip.Tip;
import com.szxb.widget.WaitDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: Tangren on 2017/8/3
 * 包名：com.szxb.module.member
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/member")
public class MemberActivity extends BaseMvpActivity<MemberPresenter> implements View.OnLongClickListener, WaitDialog.OnDialogListener {

    @BindView(R.id.num_del)
    Button numDel;
    @BindView(R.id.num_determine)
    Button numDetermine;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userPsw)
    TextView userPsw;

    private boolean selectUserName = true;
    private String temUserName;
    private String temUserPsw;
    private WaitDialog mDialog;

    @Autowired
    SeriaInformation infoEntity;

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};

    @Override
    protected int layoutID() {
        return R.layout.activity_member;
    }

    @Override
    protected Map<String, Object> getRequestParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("mchid", FetchAppConfig.mchId());
        map.put("tel", temUserName);
        return map;
    }

    @Override
    protected MemberPresenter getChildPresenter() {
        return new MemberPresenter(this);
    }


    @Override
    protected void initView() {
        super.initView();
        ARouter.getInstance().inject(this);
        title.setText("会员验证");
        temp.setVisibility(View.VISIBLE);
        temp.setText("跳过");
        temp.setTextColor(getResources().getColor(R.color.colorAccent));
        for (int button : buttons) {
            Button tempButton = (Button) findViewById(button);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectUserName) {
                        temUserName = userName.getText().toString().trim();
                        temUserName = temUserName + String.valueOf(((Button) v).getText());
                        userName.setText(temUserName);
                    } else {
                        temUserPsw = userPsw.getText().toString().trim();
                        temUserPsw = temUserPsw + String.valueOf(((Button) v).getText());
                        userPsw.setText(temUserPsw);
                    }
                }
            });
        }
        numDel.setOnLongClickListener(this);
    }

    @Override
    protected void initData() {
        mDialog = new WaitDialog();
        mDialog.setCloseListener(this);
    }

    @OnClick({R.id.num_del, R.id.num_determine, R.id.userName, R.id.userPsw, R.id.temp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.num_del:
                if (selectUserName) Util.delNum(userName);
                else Util.delNum(userPsw);
                break;
            case R.id.num_determine:
                String name = userName.getText().toString().trim();
                if (TextUtils.isEmpty(name))
                    Tip.show(getApplicationContext(), "手机号不能为空!", false);
                else {
                    numDetermine.setEnabled(false);
                    showDialog();
                    mPresenter.requestData(Constant.MEMBER_LOGIN_WHAT, getRequestParams(), UrlComm.getInstance().MEMBERLOGINURL());
                }
                break;
            case R.id.userName:
                selectUserName = true;
                userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, R.mipmap.hline, 0);
                userPsw.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userpsw, 0, 0, 0);
                break;
            case R.id.userPsw:
                selectUserName = false;
                userName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_username, 0, 0, 0);
                userPsw.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_userpsw, 0, R.mipmap.hline, 0);
                break;
            case R.id.temp:
                infoEntity.setMemnerNo("1");
                next("1");
                break;
            default:
                break;
        }
    }


    @Override
    public void onSuccess(int what, String str) {
        //会员价格字段改为会员账号
        infoEntity.setMemnerNo(temUserName);
        next("0");
    }

    @Override
    public void onFail(int what, boolean isOK, String str) {
        numDetermine.setEnabled(true);
        closeDialog();
        Tip.show(getApplicationContext(), str, false);
    }

    private void next(String status) {
        ARouter.getInstance().build("/gas/order")
                .greenChannel()
                .withString("status", status)
                .withParcelable("infoEntity", infoEntity)
                .navigation();
        finish();
    }

    @Override
    public boolean onLongClick(View v) {
        if (selectUserName) userName.setText("");
        else userPsw.setText("");
        return true;
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
            mPresenter.cancelBySign(Constant.MEMBER_LOGIN_WHAT);
            mDialog.disDialog();
        }
    }

    @Override
    public void close() {
        numDetermine.setEnabled(true);
        closeDialog();
    }
}
