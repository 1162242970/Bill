package com.felix.simplebook.activity.sign;

/**
 * Created by android on 18-5-24.
 */

public interface ISignListener {
    void onSignInSuccess();

    void onSignInFailed();

    void onSignUpSuccess();

    void onSignUpFailed();
}
