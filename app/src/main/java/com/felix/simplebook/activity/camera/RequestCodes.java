package com.felix.simplebook.activity.camera;

import com.yalantis.ucrop.UCrop;

/**
 * Created by android on 18-1-10.
 * 请求码存储
 */

public class RequestCodes {
    //拍照
    public static final int TAKE_PHOTO = 4;
    //选择照片
    public static final int PICK_PHOTO = 5;
    public static final int CROP_PHOTO = UCrop.REQUEST_CROP;
    public static final int CROP_ERROR = UCrop.RESULT_ERROR;
    public static final int SCAN = 7;
}
