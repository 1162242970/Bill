package com.felix.simplebook.base;


import com.felix.simplebook.activity.sign.ISignListener;

/**
 * Created by android on 18-5-24.
 */

public abstract class SignBaseActivity extends BaseActivity implements ISignListener {

    @Override
    public void onSignInSuccess() {

    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignUpSuccess() {

    }

    @Override
    public void onSignUpFailed() {

    }
}
