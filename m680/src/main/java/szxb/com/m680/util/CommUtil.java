package szxb.com.m680.util;

import android.widget.TextView;

/**
 * 作者: Tangren on 2017/8/17
 * 包名：szxb.com.m680.util
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class CommUtil {

    /**
     * 删除
     *
     * @param textView te
     */
    public static void delNum(TextView textView) {
        String result = textView.getText().toString();
        if (result.equals(""))
            return;
        result = result.substring(0, result.length() - 1);
        textView.setText(result);
    }
}
