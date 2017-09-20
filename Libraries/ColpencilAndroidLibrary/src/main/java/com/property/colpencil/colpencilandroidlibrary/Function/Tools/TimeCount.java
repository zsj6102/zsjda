package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author 汪 亮
 * @Description: 获取验证码倒计时
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public class TimeCount extends CountDownTimer {
    private TextView tv_get_yanzheng;

    public TimeCount(long millisInFuture, long countDownInterval, TextView tv_get_yanzheng) {
        super(millisInFuture, countDownInterval);
        this.tv_get_yanzheng = tv_get_yanzheng;
    }

    @Override
    public void onFinish() {
        tv_get_yanzheng.setText("重新获取");
    }

    @Override
    public void onTick(long time) {
        // TODO Auto-generated method stub
        tv_get_yanzheng.setText(time / 1000 + "秒");
    }
}
