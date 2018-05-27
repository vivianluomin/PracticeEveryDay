package com.example.asus1.servicebestpractice;

/**
 * Created by asus1 on 2017/4/23.
 */

public interface DownloadListenner {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
