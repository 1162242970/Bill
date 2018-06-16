package com.felix.simplebook.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;


import com.felix.simplebook.net.callback.IRequest;
import com.felix.simplebook.net.callback.ISuccess;
import com.felix.simplebook.utils.file.FileUtil;

import org.litepal.LitePalApplication;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by android on 17-12-4.
 * 创建一个AsyncTask完成文件的下载
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object[] params) {
        String dowlnloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (dowlnloadDir == null || dowlnloadDir.equals("")) {
            dowlnloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {
            //String.toUpperCase()将字符转换成大写
            return FileUtil.writeToDisk(is, dowlnloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, dowlnloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);

    }

    /**
     * 如果下载文件为apk,则默认安装
     *
     */
    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            LitePalApplication.getContext().startActivity(install);
        }
    }
}
