package com.felix.simplebook.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.felix.simplebook.R;
import com.felix.simplebook.activity.sign.SignInActivity;
import com.felix.simplebook.callback.IUserChecker;
import com.felix.simplebook.activity.sign.AccountManager;
import com.felix.simplebook.utils.MyToast;
import com.felix.simplebook.utils.MyPreference;


import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StartActivity extends Activity  {

    @BindView(R.id.btn_ok_activity_start)
    Button btnOk;
    @BindView(R.id.et_password_activity_start)
    EditText etPassword;

    private String password;
    private SharedPreferences preferences;
    private Unbinder bind;

    public final String HAS_FIRST_LAUNCHER_APP = "HAS_FIRST_LAUNCHER_APP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bind = ButterKnife.bind(StartActivity.this);

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
    protected void onResume() {
        super.onResume();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPassword.getText().toString().trim().equals(password)) {
                    checkIsShowScroll();
                    finish();
                } else {
                    MyToast.makeText(StartActivity.this, "密码输入错误", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        preferences = getSharedPreferences("config.sb",
                                Context.MODE_PRIVATE);
                        password = preferences.getString("password", "no_password");
                        if (password.equals("no_password")) {
                            checkIsShowScroll();
                            finish();
                        } else {
                            etPassword.setVisibility(View.VISIBLE);
                            btnOk.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
