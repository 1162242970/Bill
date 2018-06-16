package com.felix.simplebook.activity.person.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.felix.simplebook.R;
import com.felix.simplebook.activity.camera.PermissionCheckerActivity;
import com.felix.simplebook.database.UserProfile;
import com.felix.simplebook.net.RestClient;
import com.felix.simplebook.net.callback.IFailure;
import com.felix.simplebook.net.callback.ISuccess;
import com.felix.simplebook.utils.MyPreference;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by android on 18-5-25.
 */

public class NameActivity extends PermissionCheckerActivity{

    private Unbinder unbinder;

    @BindView(R.id.edit_name)
    AppCompatEditText nameEditText;
    @BindView(R.id.btn_name_submit)
    AppCompatButton nameButton;


    @OnClick(R.id.btn_name_submit)
    void buttonNameClick(){


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delegate_name);
        unbinder = ButterKnife.bind(NameActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
