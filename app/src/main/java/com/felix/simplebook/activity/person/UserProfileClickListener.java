package com.felix.simplebook.activity.person;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.felix.simplebook.R;
import com.felix.simplebook.activity.camera.PermissionCheckerActivity;
import com.felix.simplebook.activity.person.list.ListBean;
import com.felix.simplebook.activity.person.list.NameActivity;
import com.felix.simplebook.net.RestClient;
import com.felix.simplebook.net.callback.IFailure;
import com.felix.simplebook.net.callback.ISuccess;
import com.felix.simplebook.utils.MyPreference;
import com.felix.simplebook.utils.callback.CallbackManager;
import com.felix.simplebook.utils.callback.CallbackType;
import com.felix.simplebook.utils.callback.IGlobalCallback;
import com.felix.simplebook.utils.data.DateDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePalApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by android on 18-1-9.
 * <p>
 * RecyclerView的Item点击事件
 */

public class UserProfileClickListener extends SimpleClickListener {

    private final PermissionCheckerActivity ACTIVITY;
    private final UserProfileActivity USERACTIVITY;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true);

    private String[] mGenders = new String[]{"男", "女", "保密"};

    private static final String API_HOST = "你的服务器域名";
    private static final String UPLOAD_IMG = API_HOST + "你的上传地址";

    public UserProfileClickListener(UserProfileActivity activity) {
        ACTIVITY = activity;
        USERACTIVITY = activity;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                //设置回调
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(@Nullable final Uri args) {
                                //为ImageView设置头像
                                final ImageView avatar = view.findViewById(R.id.img_arrow_avatar);

                                Glide.with(ACTIVITY)
                                        .load(args)
                                        .apply(OPTIONS)
                                        .into(avatar);

                                //上传服务器
                                if (args != null) {

                                    new Thread() {
                                        @Override
                                        public void run() {
                                            super.run();
                                            //update file name
                                            String name = args.toString();
                                            Log.i("Log name", name);

                                            String afterPath = name.substring(8, name.lastIndexOf("/"));
                                            String afterName = name.substring(name.lastIndexOf("/"), name.length());

                                            String lastName = MyPreference.getCustomAppProfile("username") + ".jpg";

                                            File afterFile = new File(afterPath, afterName);
                                            File lastFile = new File(afterPath, lastName);

                                            try {
                                                FileInputStream fis = new FileInputStream(afterFile);
                                                FileOutputStream fos = new FileOutputStream(lastFile);

                                                Log.i("Log after path", afterPath + afterName);
                                                byte[] buffer = new byte[1024];

                                                int i = 0;
                                                while ((i = fis.read(buffer)) != -1) {
                                                    fos.write(buffer, 0, i);
                                                }

                                                fis.close();
                                                fos.close();

                                                doPost(afterPath + "/" + lastName);
                                                MyPreference.addCustomAppProfile("photos", "http://120.78.138.94:8080/server/Restore?username=" + lastName);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            doPost(args.getPath());
                                        }
                                    }.start();
//                                    OkHttpClient client = new OkHttpClient.Builder().build();
//                                    RequestBody requestBody = new FormBody.Builder()
//                                            .add("photos", args.getPath())
//                                            .build();
//                                    Request request = new Request.Builder()
//                                            .url("http://120.78.138.94:8080/server/BackUp")
//                                            .post(requestBody)
//                                            .build();
//
//                                    client.newCall(request).enqueue(new Callback() {
//                                        @Override
//                                        public void onFailure(Call call, IOException e) {
//
//
//                                        }
//
//                                        @Override
//                                        public void onResponse(Call call, Response response) throws IOException {
//                                            try {
//                                                JSONObject jsonObject = new JSONObject(response.body().string());
//                                                String result = jsonObject.getString("result");
//                                                if (result.equals("successful")) {
//
//                                                } else {
//
//                                                }
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//
//
//                                        }
//                                    });
                                }
                            }
                        });
                //开启照相机或选择图片
                ACTIVITY.startCameraWithCheck();
                break;
            case 2:
                //进入更改姓名界面
//                final Intent intent = new Intent(LitePalApplication.getContext(), NameActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                LitePalApplication.getContext().startActivity(intent);

                break;
            case 3:
                //更改性别界面
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AppCompatTextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        MyPreference.addCustomAppProfile("gender", mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                        MyPreference.addCustomAppProfile("date", date);
                    }
                });
                dateDialogUtil.showDialog(ACTIVITY);
                break;
            default:
                break;

        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ACTIVITY);
        builder.setSingleChoiceItems(mGenders, 0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    private String doPost(String imagePath) {
        OkHttpClient mOkHttpClient = new OkHttpClient();

        String result = "error";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("image", imagePath,
                RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath)));
        RequestBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        Request request = reqBuilder
                .url("http://120.78.138.94:8080/server/BackUp")
                .post(requestBody)
                .build();

        Log.d(TAG, "请求地址 " + "http://120.78.138.94:8080/server/BackUp");
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            Log.d(TAG, "响应码 " + response.code());
            if (response.isSuccessful()) {
                String resultValue = response.body().string();
                Log.d(TAG, "响应体 " + resultValue);
                return resultValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
