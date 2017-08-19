package szxb.com.m680.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import szxb.com.m680.R;

/**
 * 作者: Tangren on 2017/8/17
 * 包名：szxb.com.m680.base
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int layoutID();

    protected void initView() {
    }

    protected abstract void initData();

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutID());
        StatusBarUtil.setTransparent(this);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    protected void startActivityFromRight(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    protected void startActivityFromRight(Intent intent, int flag) {
        startActivityForResult(intent, flag);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    protected void finishActivityFromRight() {
        finish();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }


}
