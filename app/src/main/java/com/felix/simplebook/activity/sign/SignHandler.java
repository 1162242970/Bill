package com.felix.simplebook.activity.sign;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.felix.simplebook.database.UserProfile;
import com.felix.simplebook.utils.MyPreference;

import org.litepal.LitePal;


/**
 * Created by android on 17-12-8.
 */

public class SignHandler {

    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response);
        final String result = profileJson.getString("result");
        if (result.equals("successful")) {
            //让ExampleActivity回调onSignUpSuccess方法
            signListener.onSignUpSuccess();
        } else if (result.equals("fail")) {
            Toast.makeText((Context) signListener, "注册失败", Toast.LENGTH_SHORT);
        } else if (result.equals("error")) {
            Toast.makeText((Context) signListener, "用户名已经存在", Toast.LENGTH_SHORT);
        }
    }

    public static void onSignIn(String response, ISignListener signListener) {
//        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final JSONObject profileJson = JSON.parseObject(response);
        final String result = profileJson.getString("result");
        if (result.equals("successful")){

        } else {

        }


        //保存登录成功状态
        AccountManager.setSignState(true);

        //让ExampleActivity回调onSignInSuccess方法
        signListener.onSignInSuccess();
    }
}
