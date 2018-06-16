package com.felix.simplebook.activity.sign;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.widget.Toast;


import com.felix.simplebook.R;
import com.felix.simplebook.activity.HomeActivity;
import com.felix.simplebook.activity.StartActivity;
import com.felix.simplebook.base.SignBaseActivity;
import com.felix.simplebook.net.RestClient;
import com.felix.simplebook.net.callback.IFailure;
import com.felix.simplebook.net.callback.ISuccess;
import com.felix.simplebook.net.rx.RxRestClient;
import com.felix.simplebook.utils.MyPreference;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by android on 17-12-8.
 */

public class SignInActivity extends SignBaseActivity {

    @BindView(R.id.edit_sign_in_phone)
    TextInputEditText mPhone;
    @BindView(R.id.edit_sign_in_password)
    TextInputEditText mPassword;


    //登录按钮
    @OnClick(R.id.btn_sign_in)
    void OnClickSignIn() {
        if (checkForm()) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", mPhone.getText().toString())
                    .add("password", mPassword.getText().toString())
                    .build();
            Request request = new Request.Builder()
                    .url("http://120.78.138.94:8080/server/Login")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {


                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String result = jsonObject.getString("result");
                        if (result.equals("successful")) {
                            String userName = jsonObject.getString("user_name");
                            MyPreference.addCustomAppProfile("username",userName);
                            MyPreference.addCustomAppProfile("photos","http://120.78.138.94:8080/server/Restore?username="+userName+".jpg");

                            onSignInSuccess();
                        } else {
                            onSignInFailed();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });

        }

        /**
         * 用RxJava发送网络请求
         */
//        if (checkForm()) {
//            RxRestClient.builder()
//                    .url("sign_up")
//                    .params("phone", mPhone.getText().toString())
//                    .params("password", mPassword.getText().toString())
//                    .build()
//                    .get()
//                    .doOnNext(new Consumer<String>() {
//                        @Override
//                        public void accept(String s) throws Exception {
//                            //可以提前做一些操作
//                        }
//                    })
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<String>() {
//                        @Override
//                        public void accept(String response) throws Exception {
//                            //成功获取数据
//                            SignHandler.onSignIn(response, mISignListener);
//                            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//                            //获取数据失败
//                            Toast.makeText(getContext(), "登录失败,请重新输入", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//        }
    }

    //注册按钮
    @OnClick(R.id.tv_link_sign_up)
    void OnClickLink() {
        final Intent intent = new Intent();
        intent.setClass(SignInActivity.this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /**
     * 确认登录的格式是否正确
     */
    private boolean checkForm() {
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public int initLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     * 登录成功调用接口
     */
    @Override
    public void onSignInSuccess() {
        super.onSignInSuccess();
        //登录成功
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignInActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            }
        });

        final Intent intent = new Intent();
        intent.setClass(SignInActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        AccountManager.setSignState(true);
    }

    @Override
    public void onSignInFailed() {
        super.onSignInFailed();
        //登陆失败
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignInActivity.this, "用户名或者密码错误,请重新登陆", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
