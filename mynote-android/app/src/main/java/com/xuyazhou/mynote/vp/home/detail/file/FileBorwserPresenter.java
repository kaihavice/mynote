package com.xuyazhou.mynote.vp.home.detail.file;

import android.app.Activity;
import android.util.Log;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.utils.FileUtils;
import com.xuyazhou.mynote.common.utils.ShowToast;
import com.xuyazhou.mynote.model.Download.DownloadFileService;
import com.xuyazhou.mynote.model.Download.ServiceGenerator;
import com.xuyazhou.mynote.model.Download.body.ProgressResponseListener;
import com.xuyazhou.mynote.vp.base.BasePresenter;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileBorwserPresenter extends BasePresenter<FileBorwserContract.View>
        implements FileBorwserContract.Presenter, ProgressResponseListener {

    @Inject
    Activity activity;

    @Inject
    public FileBorwserPresenter() {

    }

    public void saveFile(boolean isLocalFile, String path, String savePath) throws IOException {

        if (isLocalFile) {

            if (FileUtils.fileCopy(path, savePath + "/" + System.currentTimeMillis() / 1000 + "." + path.substring(path.lastIndexOf(".") + 1))) {
                ShowToast.Short(activity, "保存成功");
                activity.finish();
            } else {
                ShowToast.Short(activity, "保存失败");
            }

        } else {
            DownloadFileService downloadFileService = ServiceGenerator.createResponseService(DownloadFileService.class, this);

            Call<File> call = downloadFileService.download(path.substring(33), savePath + "/" +
                    System.currentTimeMillis() / 1000 + "." + path.substring(path.lastIndexOf(".") + 1));

            showDialogView(R.string.downing, true);

            call.enqueue(new Callback<File>() {
                @Override
                public void onResponse(Call<File> call, Response<File> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.e("onResponse", "file path:" + response.body().getPath());
                        ShowToast.Short(activity, "保存成功");
                    } else {
                        ShowToast.Short(context, "保存失败");
                    }
                    dismissDialog();
                    activity.finish();

                }

                @Override
                public void onFailure(Call<File> call, Throwable t) {
                    dismissDialog();
                    ShowToast.Short(context, "保存失败");
                }
            });
        }


    }

    @Override
    public void onResponseProgress(long bytesRead, long contentLength, boolean done) {

    }
}