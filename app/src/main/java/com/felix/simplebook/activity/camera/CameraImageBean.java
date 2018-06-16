package com.felix.simplebook.activity.camera;

import android.net.Uri;

/**
 * Created by android on 18-1-10.
 * 存储一些中间值
 */

public class CameraImageBean {

    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }

}
