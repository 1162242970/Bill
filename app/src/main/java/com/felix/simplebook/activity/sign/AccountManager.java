package com.felix.simplebook.activity.sign;

import com.felix.simplebook.callback.IUserChecker;
import com.felix.simplebook.utils.MyPreference;

/**
 * Created by android on 18-5-24.
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态,登陆后调用
    public static void setSignState(boolean state) {
        MyPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean isSignIn() {
        return MyPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    /**
     * 根据SignTag.SIGN_TAG枚举类判断是否已经登录,true为登录,false未登录
     * 用户点击登录按钮以后,调用setSignState()方法将SignTag.SIGN_TAG的值设为true
     *
     */
    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNoSignIn();
        }
    }
}

