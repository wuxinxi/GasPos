package com.szxb.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.szxb.R;
import com.szxb.base.BaseDialogFragment;
import com.szxb.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者: Tangren on 2017/8/15
 * 包名：com.szxb.widget
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class MemberLogin extends BaseDialogFragment implements View.OnLongClickListener {
    @BindView(R.id.home)
    Button home;
    @BindView(R.id.toolBarTitle)
    TextView title;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.num_del)
    Button numDel;
    @BindView(R.id.num_determine)
    Button numDetermine;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userPsw)
    TextView userPsw;
    Unbinder unbinder;

    private boolean selectUserName = true;
    private String temUserName;
    private String temUserPsw;

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};

    public MemberLogin() {
    }

    public static MemberLogin getLoginDialog() {
        return new MemberLogin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.mipmap.ic_bg);
        View view = inflater.inflate(R.layout.activity_member, container, false);
        unbinder = ButterKnife.bind(this, view);
        title.setText("会员验证");
        temp.setVisibility(View.VISIBLE);
        temp.setText("跳过");
        temp.setTextColor(getResources().getColor(R.color.colorAccent));
        for (int button : buttons) {
            Button tempButton = (Button) view.findViewById(button);
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

        return view;
    }


    @Override
    public boolean onLongClick(View v) {
        if (selectUserName) userName.setText("");
        else userPsw.setText("");
        return true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.home, R.id.num_del, R.id.userName, R.id.userPsw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home:
                this.dismiss();
                break;
            case R.id.num_del:
                if (selectUserName) Util.delNum(userName);
                else Util.delNum(userPsw);
                break;
            case R.id.num_determine:

                String name = userName.getText().toString().trim();
                String psw = userPsw.getText().toString().trim();


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
            default:
                break;
        }
    }

}
