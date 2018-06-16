package com.felix.simplebook.activity.person;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.felix.simplebook.R;
import com.felix.simplebook.activity.camera.PermissionCheckerActivity;
import com.felix.simplebook.activity.person.list.ListAdapter;
import com.felix.simplebook.activity.person.list.ListBean;
import com.felix.simplebook.activity.person.list.ListItemType;
import com.felix.simplebook.activity.person.list.NameActivity;
import com.felix.simplebook.activity.sign.AccountManager;
import com.felix.simplebook.activity.sign.SignInActivity;
import com.felix.simplebook.database.UserProfile;
import com.felix.simplebook.utils.MyPreference;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 18-1-9.
 */


public class UserProfileActivity extends PermissionCheckerActivity {

    public RecyclerView mRecyclerView = null;
    public TextView mTextView = null;
    public final List<ListBean> data = new ArrayList<>();
    public ListAdapter adapter = null;
    public int phone = 0;
    public List<UserProfile> userProfiles = null;
    public UserProfile userProfile = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mRecyclerView = findViewById(R.id.rv_user_profile);
        mTextView = findViewById(R.id.tv_change_user);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        initView();

    }


    public void initView() {

        final ListBean image = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl(MyPreference.getCustomAppProfile("photos"))
                .build();


        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setActivity(new NameActivity())
                .setValue(MyPreference.getCustomAppProfile("username"))
                .build();

        final ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue("未知")
                .build();

        final ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("")
                .build();

        data.add(image);
        data.add(name);
        data.add(gender);
        data.add(birth);


        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }


    public void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否切换账号")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转登录界面
                        final Intent intent = new Intent();
                        intent.setClass(UserProfileActivity.this, SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        //设置登录失败
                        AccountManager.setSignState(false);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);


    }

}
