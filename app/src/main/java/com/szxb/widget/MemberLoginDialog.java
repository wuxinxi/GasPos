package com.szxb.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szxb.R;
import com.szxb.utils.Util;

/**
 * 作者: Tangren on 2017/8/16
 * 包名：com.szxb.widget
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class MemberLoginDialog extends Dialog implements View.OnLongClickListener, View.OnClickListener {

    private TextView toolBarTitle;
    private Button home;
    private TextView temp;
    private Button numDel;
    private Button numDetermine;
    private TextView userName;
    private TextView userPsw;


    private boolean selectUserName = true;
    private String temUserName;
    private String temUserPsw;

    private int[] buttons = {R.id.num_0, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_7, R.id.num_8, R.id.num_9};


    public MemberLoginDialog(@NonNull Context context) {
        super(context, R.style.testStyle);
        setDialog();
    }

    private void setDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_member, null);

        toolBarTitle = (TextView) view.findViewById(R.id.toolBarTitle);
        temp = (TextView) view.findViewById(R.id.temp);
        userName = (TextView) view.findViewById(R.id.userName);
        userPsw = (TextView) view.findViewById(R.id.userPsw);
        home = (Button) view.findViewById(R.id.home);
        numDel = (Button) view.findViewById(R.id.num_del);
        numDetermine = (Button) view.findViewById(R.id.num_determine);

        toolBarTitle.setText("会员验证");
        temp.setVisibility(View.VISIBLE);
        temp.setText("跳过");
        temp.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
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
        home.setOnClickListener(this);
        userName.setOnClickListener(this);
        userPsw.setOnClickListener(this);
        numDel.setOnClickListener(this);

        setContentView(view);
    }


    @Override
    public boolean onLongClick(View v) {
        if (selectUserName) userName.setText("");
        else userPsw.setText("");
        return true;
    }


    public View getEditUserPsw() {
        return userPsw;
    }

    public View getEditUserName() {
        return userName;
    }


    public void setPostListener(View.OnClickListener listener) {
        numDetermine.setOnClickListener(listener);
    }

    public void setJump(View.OnClickListener listener) {
        temp.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                dismiss();
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
            case R.id.num_del:
                if (selectUserName) Util.delNum(userName);
                else Util.delNum(userPsw);
                break;
            default:
                break;
        }
    }
}
