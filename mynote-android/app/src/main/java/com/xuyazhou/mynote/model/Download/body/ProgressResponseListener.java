package com.xuyazhou.mynote.model.Download.body;

/**
 * 响应体进度回调接口，比如用于文件下载中
 */
public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
