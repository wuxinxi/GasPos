package com.szxb.module.bill;

import android.os.Bundle;
import android.widget.TextView;

import com.szxb.R;
import com.szxb.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * 作者: Tangren on 2017/8/2
 * 包名：com.szxb.module.bill
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class LocalBIllFragment extends BaseFragment {

    static String ARG_PAGE = "ARG_PAGE";
    @BindView(R.id.text)
    TextView text;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static LocalBIllFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        LocalBIllFragment localBIllFragment = new LocalBIllFragment();
        localBIllFragment.setArguments(args);
        return localBIllFragment;
    }

    @Override
    protected int layoutID() {
        return R.layout.fragment_network;
    }

    @Override
    protected void initData() {
        text.setText(format.format(new Date()));
    }
}
