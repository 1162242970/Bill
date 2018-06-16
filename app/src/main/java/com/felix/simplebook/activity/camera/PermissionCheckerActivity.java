package com.felix.simplebook.activity.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.felix.simplebook.R;
import com.felix.simplebook.base.BaseActivity;
import com.felix.simplebook.utils.callback.CallbackManager;
import com.felix.simplebook.utils.callback.CallbackType;
import com.felix.simplebook.utils.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by android on 17-11-30.
 */

@RuntimePermissions
public abstract class PermissionCheckerActivity extends AppCompatActivity {


    //不是直接调用方法
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        LatteCamera.start(this);
    }

    //这个是真正调用的方法
    public void startCameraWithCheck() {
        PermissionCheckerActivityPermissionsDispatcher.startCameraWithCheck(this);
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(this, "不允许拍照", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        Toast.makeText(this, "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }

    /**
     * 进行图片剪裁的操作
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //裁剪前后的路径
        final UCrop.Options OPTIONS = new UCrop.Options();
        //设置裁剪界面toolbar颜色
        OPTIONS.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //设置裁剪界面状态栏颜色
        OPTIONS.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //判断跳转成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //跳转相机
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();

                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .withOptions(OPTIONS)
                            .start(this);
                    break;
                    //跳转相册
                case RequestCodes.PICK_PHOTO:
                    //相册的原路径
                    final Uri pickPath = data.getData();
                    //从相册选择以后需要有个路径存放剪裁过的图片
                    final String pickCrop = LatteCamera.createCropFile().getPath();
                    if (pickPath != null) {
                        UCrop.of(pickPath, Uri.parse(pickCrop))
                                .withMaxResultSize(400, 400)
                                .withOptions(OPTIONS)
                                .start(this);
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到裁剪后的数据进行处理

                    final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback != null) {
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(this, "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }




}
