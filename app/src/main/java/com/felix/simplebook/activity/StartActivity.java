package com.felix.simplebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.felix.simplebook.R;
import com.felix.simplebook.activity.sign.SignInActivity;
import com.felix.simplebook.callback.ITimerListener;
import com.felix.simplebook.callback.IUserChecker;
import com.felix.simplebook.activity.sign.AccountManager;
import com.felix.simplebook.utils.timer.BaseTimerTask;
import com.felix.simplebook.utils.MyPreference;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StartActivity extends Activity implements ITimerListener {
    @BindView(R.id.tv_launcher_timer)
    TextView mTvTimer = null;

    private Timer mTimer = null;
    private int mCount = 3;

    public final String HAS_FIRST_LAUNCHER_APP = "HAS_FIRST_LAUNCHER_APP";

    @OnClick(R.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    private void initTime() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bind = ButterKnife.bind(StartActivity.this);
        initTime();
    }

    //判断是否显示滑动启动页
    private void checkIsShowScroll() {
        final Intent intent = new Intent();
        //是否初次启动
        if (!MyPreference.getAppFlag(HAS_FIRST_LAUNCHER_APP)) {
            //可以做一些操作
        }
        //检查用户是否登录了App
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                intent.setClass(StartActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(StartActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoSignIn() {
                intent.setClass(StartActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(StartActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTimer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
