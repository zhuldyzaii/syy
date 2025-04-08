package com.example.syy.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUploader {

    private static boolean isInitialized = false;

    public interface UploadResultCallback {
        void onSuccess(String url);
        void onError(Exception e);
    }

    public static void uploadFileToCloudinary(Context context, Uri fileUri, String folder, UploadResultCallback callback) {
        if (!isInitialized) {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "drackki7a");
            config.put("api_key", "198711584919164");
            config.put("api_secret", "PRu8Y0yrXWm2hFRhw_UX4uYfV-0");
            MediaManager.init(context, config);
            isInitialized = true;
        }

        String requestId = MediaManager.get().upload(fileUri)
                .option("folder", folder)
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {}

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {}

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String url = (String) resultData.get("secure_url");
                        new Handler(Looper.getMainLooper()).post(() -> {
                            callback.onSuccess(url);
                        });
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            callback.onError(new Exception(error.getDescription()));
                        });
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        onError(requestId, error);
                    }
                })
                .dispatch();
    }
}
