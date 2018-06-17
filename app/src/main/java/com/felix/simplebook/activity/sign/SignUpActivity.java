package com.felix.simplebook.activity.sign;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.widget.Toast;


import com.felix.simplebook.R;
import com.felix.simplebook.activity.HomeActivity;
import com.felix.simplebook.base.BaseActivity;
import com.felix.simplebook.base.SignBaseActivity;
import com.felix.simplebook.net.RestClient;
import com.felix.simplebook.net.callback.IError;
import com.felix.simplebook.net.callback.IFailure;
import com.felix.simplebook.net.callback.ISuccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by android on 17-12-7.
 * 登录界面
 */

public class SignUpActivity extends SignBaseActivity {


    @BindView(R.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;

    private ISignListener mISignListener = (ISignListener) this;

    @OnClick(R.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", mPhone.getText().toString())
                    .add("password", mPassword.getText().toString())
                    .add("phone",mPhone.getText().toString())
                    .add("email",mEmail.getText().toString())
                    .build();
            Request request = new Request.Builder()
                    .url("http://120.78.138.94:8080/server/Register")
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

                            onSignUpSuccess();
                        } else {
                            Toast.makeText(SignUpActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });

        }


    }

    //返回登录界面
    @OnClick(R.id.tv_link_sign_in)
    void OnClickLink() {
        final Intent intent = new Intent();
        intent.setClass(SignUpActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 检查注册的数据是否合适
     */
    private boolean checkForm() {

        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;



        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

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

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }
        return isPass;
    }

    @Override
    public int initLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onSignUpSuccess() {
        super.onSignUpSuccess();
        //注册成功
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }
        });

        final Intent intent = new Intent();
        intent.setClass(SignUpActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onSignUpFailed() {
        super.onSignUpFailed();
        //注册失败
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignUpActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
