package com.szxb.module.member;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.szxb.R;
import com.szxb.base.BaseMvpActivity;
import com.szxb.utils.Util;
import com.szxb.utils.router.Router;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: Tangren on 2017/8/3
 * 包名：com.szxb.module.member
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Route(path = "/gas/member")
public class MemberActivity extends BaseMvpActivity implements View.OnLongClickListener {

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

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};

    @Override
    protected int layoutID() {
        return R.layout.activity_member;
    }

    @Override
    protected void initView() {
        super.initView();
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
                String psw = userPsw.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psw))
                    Toast.makeText(this, "手机号或密码不能为空!", Toast.LENGTH_SHORT).show();
                else {
                    Router.jumpL("/gas/order");
                    finish();
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
                Router.jumpL("/gas/order");
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (selectUserName) userName.setText("");
        else userPsw.setText("");
        return true;
    }
}
