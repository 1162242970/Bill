package com.felix.simplebook.activity.camera;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.blankj.utilcode.util.FileUtils;
import com.felix.simplebook.R;
import com.felix.simplebook.activity.person.UserProfileActivity;
import com.felix.simplebook.utils.file.FileUtil;

import java.io.File;

/**
 * Created by android on 18-1-10.
 * 照片处理类
 */

public class CameraHandler implements View.OnClickListener {

    private final AlertDialog DIALOG;
    private final PermissionCheckerActivity ACTIVITY;

    public CameraHandler( PermissionCheckerActivity activity) {
        this.DIALOG = new AlertDialog.Builder(activity).create();
        this.ACTIVITY = activity;
    }

    final void beginCameraDialog() {
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            //设置动画
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            //FLAG_DIM_BEHIND:让该window后所有的东西都成暗淡
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //设置黑暗度,dimAmount在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
            params.dimAmount = 0.5f;
            window.setAttributes(params);

            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
        }
    }

    private String getPhotoName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    /**
     * 打开相机
     */
    private void takePhoto() {
        final String currentPhotoName = getPhotoName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //创建相机目录文件
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhotoName);

        //兼容7.0及以上的写法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            final Uri uri = ACTIVITY.getContentResolver().
                    insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            //需要讲Uri路径转化为实际路径,还需要在configure初始化FileUtils
            final File realFile =
                    FileUtils.getFileByPath(FileUtil.getRealFilePath(ACTIVITY, uri));
            final Uri realUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setPath(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            //Android 7.0以下的写法
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        ACTIVITY.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    private void pickPhoto() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        ACTIVITY.startActivityForResult(Intent.createChooser(intent, "选择获取图片的方式"),
                RequestCodes.PICK_PHOTO);

    }

    /**
     * Dialog中控件的监听事件
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.photodialog_btn_cancel) {
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_take) {
            takePhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_native) {
            pickPhoto();
            DIALOG.cancel();
        }
    }

}
