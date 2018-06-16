package com.felix.simplebook.activity.camera;

import android.net.Uri;

import com.felix.simplebook.activity.person.UserProfileActivity;
import com.felix.simplebook.utils.file.FileUtil;


/**
 * Created by android on 18-1-10.
 * 照相机调用类
 */

public class LatteCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime(
                                "IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerActivity activity) {
        new CameraHandler(activity).beginCameraDialog();
    }
}
