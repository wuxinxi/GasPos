package szxb.com.m680.module.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;
import szxb.com.m680.R;
import szxb.com.m680.base.BaseMvpActivity;
import szxb.com.m680.util.CommUtil;

/**
 * 作者: Tangren on 2017/8/17
 * 包名：szxb.com.m680.module.login
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter> {
    @BindView(R.id.toolBarTitle)
    TextView toolBarTitle;
    @BindView(R.id.home)
    Button home;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.user)
    TextView user;
    @BindView(R.id.psw)
    TextView psw;
    @BindView(R.id.out_activity)
    Button outActivity;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.num_7)
    Button num7;
    @BindView(R.id.num_8)
    Button num8;
    @BindView(R.id.num_9)
    Button num9;
    @BindView(R.id.num_4)
    Button num4;
    @BindView(R.id.num_5)
    Button num5;
    @BindView(R.id.num_6)
    Button num6;
    @BindView(R.id.num_1)
    Button num1;
    @BindView(R.id.num_2)
    Button num2;
    @BindView(R.id.num_3)
    Button num3;
    @BindView(R.id.num_0)
    Button num0;
    @BindView(R.id.num_del)
    Button numDel;

    private boolean selectUserName = true;
    private String temUserName;
    private String temUserPsw;

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};

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
        for (int button : buttons) {
            Button tempButton = (Button) findViewById(button);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectUserName) {
                        temUserName = user.getText().toString().trim();
                        temUserName = temUserName + String.valueOf(((Button) v).getText());
                        user.setText(temUserName);
                    } else {
                        temUserPsw = psw.getText().toString().trim();
                        temUserPsw = temUserPsw + String.valueOf(((Button) v).getText());
                        psw.setText(temUserPsw);
                    }
                }
            });

        }
    }

    @Override
    protected void initData() {
        toolBarTitle.setText("M680销售系统");
        toolBarTitle.setTextSize(35);
    }


    @OnClick({R.id.user, R.id.psw, R.id.out_activity, R.id.login, R.id.num_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user:
                selectUserName = true;
                user.setBackgroundResource(R.drawable.login_edit_shape_click);
                psw.setBackgroundResource(R.drawable.login_edit_shape);
                break;
            case R.id.psw:
                selectUserName = false;
                user.setBackgroundResource(R.drawable.login_edit_shape);
                psw.setBackgroundResource(R.drawable.login_edit_shape_click);
                break;
            case R.id.out_activity:
                finishActivityFromRight();
                break;
            case R.id.login:
                String name = user.getText().toString().trim();
                String p = psw.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(p))
                    Toast.makeText(this, "手机号或密码不能为空!", Toast.LENGTH_SHORT).show();
                else {
                    ARouter.getInstance().build("/m680/main")
                            .navigation();
                }
                break;
            case R.id.num_del:
                if (selectUserName) CommUtil.delNum(user);
                else CommUtil.delNum(psw);
                break;

        }
    }

    @Override
    public void onSuccess(String str) {

    }

    @Override
    public void onFail(String str) {

    }
}
