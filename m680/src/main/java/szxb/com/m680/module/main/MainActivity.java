package szxb.com.m680.module.main;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.OnClick;
import szxb.com.m680.R;
import szxb.com.m680.base.BaseActivity;

@Route(path = "/m680/main")
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolBarTitle)
    TextView toolBarTitle;
    @BindView(R.id.home)
    Button home;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.button_1)
    Button button1;
    @BindView(R.id.button_2)
    Button button2;
    @BindView(R.id.button_3)
    Button button3;
    @BindView(R.id.button_4)
    Button button4;

    @Override
    protected int layoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        toolBarTitle.setText("M680销售系统");
        toolBarTitle.setTextSize(35);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                break;
            case R.id.button_2:
                break;
            case R.id.button_3:
                break;
            case R.id.button_4:
                break;
        }
    }
}
